package cn.tangshh.stock_watcher.ui.listener;

import cn.tangshh.stock_watcher.config.PluginConfig;
import cn.tangshh.stock_watcher.constant.I18nKey;
import cn.tangshh.stock_watcher.entity.StockData;
import cn.tangshh.stock_watcher.enums.PopupDetailView;
import cn.tangshh.stock_watcher.service.StockDataService;
import cn.tangshh.stock_watcher.service.StockDataServiceFactory;
import cn.tangshh.stock_watcher.ui.model.StockTableDataModel;
import cn.tangshh.stock_watcher.util.I18nUtil;
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

        JBLabel label = new JBLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        if (service != null) {
            if (view == PopupDetailView.TIME_SHARE || view == PopupDetailView.DAY_K ||
                    view == PopupDetailView.WEEK_K || view == PopupDetailView.MONTH_K) {
                // 设置加载中的提示
                label.setText(I18nUtil.message(I18nKey.LOADING));

                // 使用SwingWorker异步加载图片
                new SwingWorker<ImageIcon, Void>() {
                    @Override
                    protected ImageIcon doInBackground() throws Exception {
                        try {
                            String url = service.kLineImg(stockCode, view);
                            // 使用ImageIO.read()更高效，且支持更多图片格式
                            java.awt.Image image = javax.imageio.ImageIO.read(URI.create(url).toURL());
                            return image != null ? new ImageIcon(image) : null;
                        } catch (Exception ex) {
                            label.setText(I18nUtil.message(I18nKey.LOAD_FAIL));
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            ImageIcon icon = get();
                            if (icon != null) {
                                label.setIcon(icon);
                                label.setText(null); // 清除加载提示
                            } else {
                                label.setText(I18nUtil.message(I18nKey.LOAD_FAIL));
                            }
                        } catch (Exception ex) {
                            label.setText(I18nUtil.message(I18nKey.LOAD_FAIL));
                        }
                    }
                }.execute();
            } else if (view == PopupDetailView.DETAIL) {
                label.setText(I18nUtil.message(I18nKey.POPUP_UI_DETAIL_PLACEHOLDER));
            }
        } else {
            label.setText(I18nUtil.message(I18nKey.SERVICE_FAIL));
        }

        label.setPreferredSize(new Dimension(550, 350));
        label.setBackground(JBColor.LIGHT_GRAY);
        label.setOpaque(true);
        return label;
    }
}