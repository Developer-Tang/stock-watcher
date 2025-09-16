package cn.tangshh.stock_watcher.ui.model;

import cn.tangshh.stock_watcher.config.PluginConfig;
import cn.tangshh.stock_watcher.entity.StockField;
import cn.tangshh.stock_watcher.ui.renderer.StockTableCellRenderer;
import cn.tangshh.stock_watcher.util.I18nUtil;
import cn.tangshh.stock_watcher.util.PinyinUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import java.util.List;

/**
 *
 *
 * @author Tang
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StockTableColumnModel extends DefaultTableColumnModel {
    private StockTableCellRenderer renderer = new StockTableCellRenderer();

    public void updateConfig(PluginConfig config) {
        updateConfig(config, false);
    }

    public void updateConfig(PluginConfig config, boolean ignoreColumn) {
        if (!ignoreColumn) {
            List<StockField> stockFields = config.getStockFields();
            tableColumns.clear();
            for (int i = 0; i < stockFields.size(); i++) {
                StockField field = stockFields.get(i);
                TableColumn column = new TableColumn(i);
                column.setModelIndex(i);
                column.setIdentifier(field.getName());
                column.setHeaderValue(I18nUtil.message(field.getI18nKey()));
                if (I18nUtil.isChinese() && config.getPrivacyModeEnabled()) {
                    column.setHeaderValue(PinyinUtil.getPinyin(column.getHeaderValue().toString()));
                }
                if (!field.getSelected()) {
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
                column.setResizable(false);
                column.setCellRenderer(renderer);

                addColumn(column);
            }
        }
    }
}