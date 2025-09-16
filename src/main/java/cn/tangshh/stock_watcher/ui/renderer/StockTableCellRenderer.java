package cn.tangshh.stock_watcher.ui.renderer;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.tangshh.stock_watcher.config.PluginConfig;
import cn.tangshh.stock_watcher.constant.StockFieldName;
import cn.tangshh.stock_watcher.entity.StockField;
import cn.tangshh.stock_watcher.enums.DisplayStyle;
import com.intellij.ui.JBColor;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 *
 * @author Tang
 * @version v1.0
 */

public class StockTableCellRenderer extends DefaultTableCellRenderer {
    private final PluginConfig config = PluginConfig.getInstance();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        component.setForeground(JBColor.BLACK); // 不加后面变动容易影响

        int modelIndex = table.convertColumnIndexToModel(column);
        List<StockField> stockFields = config.getStockFields();
        StockField field = stockFields.get(modelIndex);

        if (!StrUtil.equalsAny(field.getName(), StockFieldName.PRICE_CHANGE,
                StockFieldName.PRICE_CHANGE_PERCENT, StockFieldName.PRICE_CHANGE_PROFIT,
                StockFieldName.HOLDING_PROFIT, StockFieldName.HOLDING_PROFIT_PERCENT)) {
            return component;
        }

        JBColor upColor = null, downColor = null;
        DisplayStyle style = config.getDisplayStyle();
        switch (style) {
            case RED_GREEN -> {
                upColor = JBColor.RED;
                downColor = JBColor.GREEN;
            }
            case BLACK_GRAY -> {
                upColor = JBColor.BLACK;
                downColor = JBColor.GRAY;
            }
        }
        BigDecimal val = Convert.toBigDecimal(value);

        if (BigDecimal.ZERO.compareTo(val) > 0) {
            component.setForeground(downColor);
        } else if (BigDecimal.ZERO.compareTo(val) < 0) {
            component.setForeground(upColor);
        }

        return component;
    }
}