package cn.tangshh.stock_watcher.ui;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.tangshh.stock_watcher.config.PluginConfig;
import cn.tangshh.stock_watcher.constant.I18nKey;
import cn.tangshh.stock_watcher.entity.StockConfig;
import cn.tangshh.stock_watcher.entity.StockData;
import cn.tangshh.stock_watcher.enums.DataSource;
import cn.tangshh.stock_watcher.enums.IEnumListCellRenderer;
import cn.tangshh.stock_watcher.service.StockDataService;
import cn.tangshh.stock_watcher.service.StockDataServiceFactory;
import cn.tangshh.stock_watcher.task.CronTask;
import cn.tangshh.stock_watcher.ui.listener.StockTableMouseListener;
import cn.tangshh.stock_watcher.ui.model.StockTableColumnModel;
import cn.tangshh.stock_watcher.ui.model.StockTableDataModel;
import cn.tangshh.stock_watcher.util.I18nUtil;
import cn.tangshh.stock_watcher.util.NotifyUtil;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.table.JBTable;
import net.miginfocom.swing.MigLayout;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 股票工具窗口面板
 *
 * @author Tang
 * @version v1.0
 */
public class StockToolWindowPanel implements ToolWindowFactory, I18nKey {
    private final PluginConfig config = PluginConfig.getInstance();
    private final Map<String, JBTable> tables = new ConcurrentHashMap<>();
    private final Map<String, StockTableDataModel> dataModels = new ConcurrentHashMap<>();
    private final Map<String, StockTableColumnModel> columnModels = new ConcurrentHashMap<>();
    private final Map<String, CronTask> tasks = new ConcurrentHashMap<>();
    private final Map<String, Boolean> refreshState = new ConcurrentHashMap<>();

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        String name = project.getName();
        project.getMessageBus().connect().subscribe(ProjectManager.TOPIC, new ProjectManagerListener() {
            @Override
            public void projectClosed(@NotNull Project project) {
                tables.remove(name);
                dataModels.remove(name);
                columnModels.remove(name);
                CronTask task = tasks.remove(name);
                if (task != null) {
                    task.stop();
                }
                refreshState.remove(name);
            }
        });
        JPanel panel = new JPanel(new MigLayout("", "[grow]", ""));
        panel.add(createToolBar(name), "wrap");
        panel.add(createTableView(name), "grow");

        ContentManager manager = toolWindow.getContentManager();
        ContentFactory factory = manager.getFactory();
        Content content = factory.createContent(panel, "", false);
        manager.addContent(content);
    }

    private JBPanel<?> createToolBar(@NotNull String projectName) {
        JBPanel<?> panel = new JBPanel<>();
        panel.setLayout(new MigLayout("", "", ""));

        // 数据源
        panel.add(new JLabel(I18nUtil.message(CONFIG_TITLE_DATA_SOURCE)), "gapleft 20");
        ComboBox<DataSource> dataSourceCb = new ComboBox<>(DataSource.values());
        dataSourceCb.setRenderer(IEnumListCellRenderer.getInstance());
        dataSourceCb.setSelectedItem(config.getDataSource());
        dataSourceCb.addActionListener(event -> {
            Object item = dataSourceCb.getSelectedItem();
            if (!ObjUtil.equals(item, config.getDataSource()) && item instanceof DataSource source) {
                config.setDataSource(source);
            }
        });
        panel.add(dataSourceCb, "gapright 30");

        // 刷新状态按钮
        JButton refreshBtn = new JButton(I18nUtil.message(TOOL_WINDOW_REFRESH_BTN));
        panel.add(refreshBtn, "width 175, gapright 30");

        // 重置样式按钮
        JButton resetStyleBtn = new JButton(I18nUtil.message(TOOL_WINDOW_RESET_TABLE_STYLE_BTN));
        panel.add(resetStyleBtn, "width 175, gapright 30");

        // 刷新状态文本
        JBLabel stateText = new JBLabel();
        panel.add(stateText, "");

        // 最后处理按钮点击事件，确保组件都能拿到
        refreshBtn.addActionListener(event -> refreshBtnClick(projectName, refreshBtn, stateText));
        resetStyleBtn.addActionListener(event -> resetStyleClick(projectName));

        return panel;
    }

    private synchronized void refreshBtnClick(String projectName, JButton refreshBtn, JBLabel stateText) {
        Boolean inRefresh = refreshState.getOrDefault(projectName, Boolean.FALSE);
        CronTask cronTask = tasks.getOrDefault(projectName, new CronTask());

        inRefresh = !inRefresh;

        if (inRefresh) {
            resetStyleClick(projectName); // 重置下列样式
            try {
                cronTask.start(config.getRefreshCron(), () -> refreshDataTask(projectName, stateText));
                stateText.setText(I18nUtil.message(TOOL_WINDOW_REFRESH_WAIT_MSG));
                refreshBtn.setText(I18nUtil.message(TOOL_WINDOW_STOP_BTN));
            } catch (RuntimeException ex) {
                if (StrUtil.contains(ex.getMessage(), "CronExpression")) {
                    inRefresh = false;
                    NotifyUtil.balloon(I18nUtil.message(CONFIG_ERROR_REFRESH_CYCLE), NotificationType.ERROR);
                } else {
                    ex.printStackTrace();
                }
            }
        } else {
            cronTask.stop();
            stateText.setText(I18nUtil.message(TOOL_WINDOW_REFRESH_STOP_MSG));
            refreshBtn.setText(I18nUtil.message(TOOL_WINDOW_REFRESH_BTN));
        }
        refreshState.put(projectName, inRefresh);
        tasks.putIfAbsent(projectName, cronTask);
    }

    private void resetStyleClick(@NotNull String projectName) {
        if (columnModels.containsKey(projectName)) {
            columnModels.get(projectName).updateConfig(config);
        }
    }

    private void refreshDataTask(@NotNull String projectName, JBLabel stateText) {
        ApplicationManager.getApplication().invokeLater(() -> {
            StockDataService dataService = StockDataServiceFactory.get(config.getDataSource());
            List<StockData> data = dataService.batchGetStockData(CollUtil.map(config.getStockConfigs(), StockConfig::getCode, true));

            for (StockData d : data) {
                StockConfig conf = CollUtil.findOne(config.getStockConfigs(), e -> StrUtil.equalsIgnoreCase(e.getCode(), d.getCode()));
                if (conf != null) {
                    d.setHoldQuantity(conf.getHoldQuantity());
                    d.setCostPrice(conf.getCostPrice());
                }
            }

            if (dataModels.containsKey(projectName)) {
                dataModels.get(projectName).updateConfig(config);
            }
            if (dataModels.containsKey(projectName)) {
                dataModels.get(projectName).updateStockData(data);
            }
            if (dataModels.containsKey(projectName)) {
                dataModels.get(projectName).fireTableDataChanged();
            }
            if (tables.containsKey(projectName)) {
                tables.get(projectName).repaint();
            }
            stateText.setText(I18nUtil.message(TOOL_WINDOW_REFRESH_OK_MSG, DateUtil.formatTime(new Date())));
        });
    }

    private JScrollPane createTableView(@NotNull String projectName) {
        StockTableDataModel dataModel = new StockTableDataModel();
        StockTableColumnModel columnModel = new StockTableColumnModel();

        JBTable table = new JBTable(dataModel, columnModel);
        table.addMouseListener(new StockTableMouseListener(table));

        dataModels.putIfAbsent(projectName, dataModel);
        columnModels.putIfAbsent(projectName, columnModel);
        tables.putIfAbsent(projectName, table);
        return new JBScrollPane(table);
    }
}