package cn.tangshh.stock_watcher.ui.model;

import cn.tangshh.stock_watcher.config.PluginConfig;
import cn.tangshh.stock_watcher.constant.StockFieldName;
import cn.tangshh.stock_watcher.entity.StockData;
import cn.tangshh.stock_watcher.entity.StockField;
import cn.tangshh.stock_watcher.util.I18nUtil;
import cn.tangshh.stock_watcher.util.PinyinUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Tang
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StockTableDataModel extends DefaultTableModel {
    private final List<StockField> fields = new ArrayList<>();
    private final List<StockData> stockData = new ArrayList<>();
    private boolean privacyModeEnabled = false;

    @Override
    public int getRowCount() {
        return stockData == null ? 0 : stockData.size();
    }

    @Override
    public int getColumnCount() {
        return fields.size();
    }

    @Override
    public String getColumnName(int column) {
        return I18nUtil.message(fields.get(column).getI18nKey());
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int column) {
        StockField field = fields.get(column);
        StockData data = stockData.get(row);

        return switch (field.getName()) {
            case StockFieldName.CODE -> data.getCode();
            case StockFieldName.NAME -> privacyModeEnabled ? PinyinUtil.getPinyin(data.getName()) : data.getName();
            case StockFieldName.PREV_CLOSE_PRICE -> data.getPrevClosePrice();
            case StockFieldName.OPEN_PRICE -> data.getOpenPrice();
            case StockFieldName.LOW_PRICE -> data.getLowPrice();
            case StockFieldName.HIGH_PRICE -> data.getHighPrice();
            case StockFieldName.CURRENT_PRICE -> data.getCurrentPrice();
            case StockFieldName.PRICE_CHANGE -> data.getPriceChange();
            case StockFieldName.PRICE_CHANGE_PERCENT -> String.format("%s %%", data.getPriceChangePercent());
            case StockFieldName.PRICE_CHANGE_PROFIT -> data.getPriceChangeProfit();
            case StockFieldName.HOLDING_PROFIT -> data.getHoldingProfit();
            case StockFieldName.HOLDING_PROFIT_PERCENT -> String.format("%s %%", data.getHoldingProfitPercent());
            default -> "";
        };
    }

    public void updateConfig(PluginConfig config) {
        this.fields.clear();
        this.fields.addAll(config.getStockFields());
        this.privacyModeEnabled = Boolean.TRUE.equals(config.getPrivacyModeEnabled());
    }

    public void updateStockData(List<StockData> stockData) {
        this.stockData.clear();
        this.stockData.addAll(stockData);
    }
}