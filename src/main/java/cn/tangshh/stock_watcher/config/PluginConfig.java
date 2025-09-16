package cn.tangshh.stock_watcher.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.tangshh.stock_watcher.constant.I18nKey;
import cn.tangshh.stock_watcher.constant.StockFieldName;
import cn.tangshh.stock_watcher.entity.StockConfig;
import cn.tangshh.stock_watcher.entity.StockField;
import cn.tangshh.stock_watcher.enums.DataSource;
import cn.tangshh.stock_watcher.enums.DisplayStyle;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import kotlin.jvm.JvmField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 插件配置类，用于存储和管理用户的配置信息
 *
 * @author Tang
 */
@Data
@Service
@Accessors(chain = true)
@State(name = "cn.tangshh.stock_watcher.config.PluginConfig", storages = {@Storage("StockWatcherConfig.xml")})
public final class PluginConfig implements PersistentStateComponent<PluginConfig>, I18nKey {
    /** 表格列表收益展示样式 */
    private DisplayStyle displayStyle = DisplayStyle.BLACK_GRAY;

    /** 隐私模式开关（开启后，中文显示会转换为拼音） */
    private Boolean privacyModeEnabled = false;

    /** 数据刷新速率（cron格式） */
    private String refreshCron = "0/3 * * * * ?"; // 默认每3秒刷新一次

    /** 数据查询来源 */
    private DataSource dataSource = DataSource.SINA;

    /** 股票代码列表配置（格式：sh000001,3000,100） */
    private String stockConfigStr = "sh000001\nsz399001";

    /** 展示字段配置 */
    @JvmField
    private List<StockField> stockFields = CollUtil.newArrayList(
            new StockField()
                    .setName(StockFieldName.CODE)
                    .setI18nKey(FIELD_CODE)
                    .setCanCancel(false),
            new StockField()
                    .setName(StockFieldName.NAME)
                    .setI18nKey(FIELD_NAME),
            new StockField()
                    .setName(StockFieldName.PREV_CLOSE_PRICE)
                    .setI18nKey(FIELD_PREV_CLOSE_PRICE),
            new StockField()
                    .setName(StockFieldName.OPEN_PRICE)
                    .setI18nKey(FIELD_OPEN_PRICE),
            new StockField()
                    .setName(StockFieldName.LOW_PRICE)
                    .setI18nKey(FIELD_LOW_PRICE),
            new StockField()
                    .setName(StockFieldName.HIGH_PRICE)
                    .setI18nKey(FIELD_HIGH_PRICE),
            new StockField()
                    .setName(StockFieldName.CURRENT_PRICE)
                    .setI18nKey(FIELD_CURRENT_PRICE)
                    .setCanCancel(false),
            new StockField()
                    .setName(StockFieldName.PRICE_CHANGE)
                    .setI18nKey(FIELD_PRICE_CHANGE)
                    .setCanCancel(false),
            new StockField()
                    .setName(StockFieldName.PRICE_CHANGE_PERCENT)
                    .setI18nKey(FIELD_PRICE_CHANGE_PERCENT)
                    .setCanCancel(false),
            new StockField()
                    .setName(StockFieldName.PRICE_CHANGE_PROFIT)
                    .setI18nKey(FIELD_PRICE_CHANGE_PROFIT),
            new StockField()
                    .setName(StockFieldName.HOLDING_PROFIT)
                    .setI18nKey(FIELD_HOLDING_PROFIT),
            new StockField()
                    .setName(StockFieldName.HOLDING_PROFIT_PERCENT)
                    .setI18nKey(FIELD_HOLDING_PROFIT_PERCENT)
    );

    /**
     * 获取实例
     */
    public static PluginConfig getInstance() {
        return ApplicationManager.getApplication().getService(PluginConfig.class);
    }

    /**
     * 获取存储的股票配置列表
     */
    public List<StockConfig> getStockConfigs() {
        if (stockConfigStr == null || stockConfigStr.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return StrUtil.split(stockConfigStr, "\n").stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> {
                    List<String> data = StrUtil.split(line, StrUtil.COMMA);
                    String code = CollUtil.get(data, 0);
                    String costPrice = CollUtil.get(data, 1);
                    String holdQuantity = CollUtil.get(data, 2);

                    return new StockConfig()
                            .setCode(StrUtil.trim(code))
                            .setCostPrice(Convert.toBigDecimal(StrUtil.trim(costPrice)))
                            .setHoldQuantity(Convert.toInt(StrUtil.trim(holdQuantity)));
                })
                .filter(config -> StrUtil.isNotBlank(config.getCode()))
                .toList();
    }

    /**
     * 设置股票配置列表
     */
    public void updateStockList(List<StockConfig> configs) {
        if (CollUtil.isEmpty(configs)) {
            stockConfigStr = StrUtil.EMPTY;
            return;
        }

        stockConfigStr = configs.stream()
                .map(config -> config.getCode() + "," + config.getCostPrice() + "," + config.getHoldQuantity())
                .collect(Collectors.joining("\n"));
    }

    @Override
    public @NotNull PluginConfig getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PluginConfig state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}