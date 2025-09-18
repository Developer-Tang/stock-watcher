package cn.tangshh.stock_watcher.ui.listener;

import cn.tangshh.stock_watcher.config.PluginConfig;
import cn.tangshh.stock_watcher.constant.I18nKey;
import cn.tangshh.stock_watcher.entity.StockData;
import cn.tangshh.stock_watcher.enums.PopupDetailView;
import cn.tangshh.stock_watcher.service.StockDataService;
import cn.tangshh.stock_watcher.service.StockDataServiceFactory;
import cn.tangshh.stock_watcher.ui.model.StockTableDataModel;
import cn.tangshh.stock_watcher.util.I18nUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

/**
 * 股票表鼠标监听器
 *
 * @author Tang
 * @version v1.0
 */

public class StockTableMouseListener implements MouseListener, I18nKey {
    private final static Logger LOG = Logger.getInstance(StockTableMouseListener.class);
    private final PluginConfig config = PluginConfig.getInstance();

    private final JBTable table;

    public StockTableMouseListener(JBTable table) {
        this.table = table;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0 || selectedRow >= table.getRowCount()) {
                return; // 错误参数
            }
            StockTableDataModel model = (StockTableDataModel) table.getModel();
            StockData data = model.getStockData().get(selectedRow);

            JBTabbedPane pane = new JBTabbedPane(SwingConstants.TOP);
            for (PopupDetailView view : PopupDetailView.values()) {
                pane.addTab(I18nUtil.message(view.getI18nKey()), buildTabsView(data.getCode(), view));
            }

            JBPopupFactory.getInstance()
                    .createComponentPopupBuilder(pane, null)
                    .setMovable(false)
                    .setRequestFocus(true)
                    .createPopup()
                    .show(RelativePoint.fromScreen(new Point(e.getXOnScreen(), e.getYOnScreen() - 400)));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public JComponent buildTabsView(String stockCode, PopupDetailView view) {
        StockDataService service = StockDataServiceFactory.get(config.getDataSource());

        JComponent component = new JBLabel();
        if (service != null) {
            if (view == PopupDetailView.TIME_SHARE || view == PopupDetailView.DAY_K ||
                    view == PopupDetailView.WEEK_K || view == PopupDetailView.MONTH_K) {
                ApplicationManager.getApplication().invokeLater(() -> {
                    try {
                        String url = service.kLineImg(stockCode, view);
                        ImageIcon imageIcon = new ImageIcon(URI.create(url).toURL());
                        ((JBLabel) component).setIcon(imageIcon);
                    } catch (Exception ex) {
                        LOG.error("K线图加载失败", ex);
                    }
                });
            } else if (view == PopupDetailView.DETAIL) {
                // 考虑展示股票的一些信息
            }
        }

        component.setPreferredSize(new Dimension(550, 350));
        component.setBackground(JBColor.LIGHT_GRAY);
        component.setOpaque(true);
        return component;
    }
}