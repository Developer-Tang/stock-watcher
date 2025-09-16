package cn.tangshh.stock_watcher.ui;

import cn.hutool.core.util.ObjUtil;
import cn.tangshh.stock_watcher.config.PluginConfig;
import cn.tangshh.stock_watcher.constant.I18nKey;
import cn.tangshh.stock_watcher.entity.StockField;
import cn.tangshh.stock_watcher.enums.DataSource;
import cn.tangshh.stock_watcher.enums.DisplayStyle;
import cn.tangshh.stock_watcher.enums.IEnumListCellRenderer;
import cn.tangshh.stock_watcher.util.I18nUtil;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.util.ui.JBUI;
import net.miginfocom.swing.MigLayout;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 股票配置面板
 *
 * @author Tang
 * @version v1.0
 */
public class StockConfigPanel implements Configurable, I18nKey {
    private final PluginConfig config = PluginConfig.getInstance();
    private final DefaultListCellRenderer renderer = IEnumListCellRenderer.getInstance();

    private JComboBox<DisplayStyle> displayStyleCb;
    private JTextField refreshCronField;
    private JComboBox<DataSource> dataSourceCb;
    private JTextArea stockConfigArea;
    private JCheckBox privacyModeCb;
    private final List<JCheckBox> stockFieldCbs = new ArrayList<>();

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return I18nUtil.message(CONFIG_TITLE);
    }

    @Override
    public @Nullable JComponent createComponent() {
        // 布局约束：2列（标签列和内容列），列宽自动适应，组件间距5px
        JPanel mainPanel = new JBPanel<>(new MigLayout("fillx", "[left]10[]"));
        /* MigLayout：
            wrap 表示换行
            grow 表示填充空间
            split 表示同一单元格放多个组件
        */

        // 1. 展示样式行
        mainPanel.add(new JLabel(I18nUtil.message(CONFIG_TITLE_REVENUE_DISPLAY_MODEL)), "top, span 1 4");
        displayStyleCb = new ComboBox<>(DisplayStyle.values());
        displayStyleCb.setRenderer(renderer);
        displayStyleCb.setSelectedItem(config.getDisplayStyle());
        mainPanel.add(displayStyleCb, "width 400::, wrap");
        mainPanel.add(buildComment(I18nUtil.message(CONFIG_COMMENT_REVENUE_DISPLAY_MODEL)), "width 400, wrap");

        // 初始化隐私模式复选框
        privacyModeCb = new JCheckBox(I18nUtil.message(CONFIG_TITLE_PRIVACY_MODE));
        privacyModeCb.setSelected(config.getPrivacyModeEnabled());
        mainPanel.add(privacyModeCb, "wrap");
        mainPanel.add(buildComment(I18nUtil.message(CONFIG_COMMENT_PRIVACY_MODE)), "width 400::, wrap");

        // 2. 刷新速率行
        mainPanel.add(new JLabel(I18nUtil.message(CONFIG_TITLE_REFRESH_CYCLE)), "top, span 1 2");
        refreshCronField = new JTextField(config.getRefreshCron());
        mainPanel.add(refreshCronField, "width 400::, wrap");
        mainPanel.add(buildComment(I18nUtil.message(CONFIG_COMMENT_REFRESH_CYCLE)), "width 400::, wrap");

        // 3. 数据查询来源行
        mainPanel.add(new JLabel(I18nUtil.message(CONFIG_TITLE_DATA_SOURCE)), "top");
        dataSourceCb = new ComboBox<>(DataSource.values());
        dataSourceCb.setRenderer(renderer);
        dataSourceCb.setSelectedItem(config.getDataSource());
        mainPanel.add(dataSourceCb, "width 400::, wrap");

        // 4. 股票代码列表行
        mainPanel.add(new JLabel(I18nUtil.message(CONFIG_TITLE_STOCK_LIST)), "top, span 1 2");
        stockConfigArea = new JTextArea(config.getStockConfigStr());
        stockConfigArea.setRows(10);
        stockConfigArea.setBorder(JBUI.Borders.empty(5));
        stockConfigArea.setFont(refreshCronField.getFont());
        JBScrollPane scrollPane = new JBScrollPane(stockConfigArea);
        scrollPane.setFocusable(false);
        mainPanel.add(scrollPane, "width 400::, wrap");
        mainPanel.add(buildComment(I18nUtil.message(CONFIG_COMMENT_STOCK_LIST)), "width 400::, wrap");

        // 5. 展示字段区域
        mainPanel.add(new JLabel(I18nUtil.message(CONFIG_TITLE_STOCK_FIELDS)), "top, span 1 2");
        JPanel fieldPanel = new JBPanel<>(new MigLayout("flowx, wrap", "[][][]", ""));
        fieldPanel.setBorder(JBUI.Borders.empty());
        for (StockField field : config.getStockFields()) {
            JCheckBox cb = new JCheckBox(I18nUtil.message(field.getI18nKey()));
            cb.setSelected(Boolean.TRUE.equals(field.getSelected()));
            cb.setEnabled(Boolean.TRUE.equals(field.getCanCancel()));
            fieldPanel.add(cb);
            stockFieldCbs.add(cb);
        }
        mainPanel.add(fieldPanel, "width 400::,wrap");
        mainPanel.add(buildComment(I18nUtil.message(CONFIG_COMMENT_STOCK_FIELDS)), "width 400::, wrap");
        return mainPanel;
    }

    /**
     * 校验变更
     *
     * @return boolean
     * @since v1.0
     */
    @Override
    public boolean isModified() {
        return !ObjUtil.equals(displayStyleCb.getSelectedItem(), config.getDisplayStyle()) ||
                !ObjUtil.equals(privacyModeCb.isSelected(), config.getPrivacyModeEnabled()) ||
                !ObjUtil.equals(refreshCronField.getText(), config.getRefreshCron()) ||
                !ObjUtil.equals(dataSourceCb.getSelectedItem(), config.getDataSource()) ||
                !ObjUtil.equals(stockConfigArea.getText(), config.getStockConfigStr()) ||
                fieldsChanged();
    }

    /**
     * 重置
     *
     * @since v1.0
     */
    @Override
    public void reset() {
        displayStyleCb.setSelectedItem(config.getDisplayStyle());
        privacyModeCb.setSelected(config.getPrivacyModeEnabled());
        refreshCronField.setText(config.getRefreshCron());
        dataSourceCb.setSelectedItem(config.getDataSource());
        stockConfigArea.setText(config.getStockConfigStr());

        List<StockField> stockField = config.getStockFields();
        if (stockFieldCbs.size() == stockField.size()) {
            for (int i = 0; i < stockFieldCbs.size(); i++) {
                JCheckBox box = stockFieldCbs.get(i);
                StockField field = stockField.get(i);
                box.setSelected(field.getSelected());
            }
        }
    }

    /**
     * 应用
     *
     * @since v1.0
     */
    @Override
    public void apply() {
        config.setRefreshCron(refreshCronField.getText())
                .setDisplayStyle((DisplayStyle) displayStyleCb.getSelectedItem())
                .setDataSource((DataSource) dataSourceCb.getSelectedItem())
                .setStockConfigStr(stockConfigArea.getText())
                .setPrivacyModeEnabled(privacyModeCb.isSelected());

        List<StockField> stockField = config.getStockFields();
        if (stockFieldCbs.size() == stockField.size()) {
            for (int i = 0; i < stockFieldCbs.size(); i++) {
                JCheckBox box = stockFieldCbs.get(i);
                StockField field = stockField.get(i);
                field.setSelected(box.isSelected());
            }
        }
    }

    /**
     * 字段已更改
     *
     * @return boolean
     * @since v1.0
     */
    public boolean fieldsChanged() {
        List<StockField> stockField = config.getStockFields();
        if (stockFieldCbs.size() == stockField.size()) {
            for (int i = 0; i < stockFieldCbs.size(); i++) {
                JCheckBox box = stockFieldCbs.get(i);
                StockField field = stockField.get(i);
                if (!ObjUtil.equals(box.isSelected(), field.getSelected())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 构建注释
     *
     * @param text 发短信
     * @return {@link JBTextArea }
     * @since v1.0
     */
    private JBTextArea buildComment(String text) {
        return buildComment(text, 10);
    }

    /**
     * 构建注释
     *
     * @param text 文本
     * @param left 左间距
     * @return {@link JBTextArea }
     * @since v1.0
     */
    private JBTextArea buildComment(String text, int left) {
        JBTextArea area = new JBTextArea(text);
        area.setEnabled(false);
        area.setLineWrap(true);
        area.setBorder(JBUI.Borders.emptyLeft(left));
        return area;
    }
}