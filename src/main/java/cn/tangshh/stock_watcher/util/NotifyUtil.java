package cn.tangshh.stock_watcher.util;

import cn.tangshh.stock_watcher.constant.I18nKey;
import com.esotericsoftware.kryo.kryo5.minlog.Log;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ex.ProjectManagerEx;

/**
 * 通知工具类
 *
 * @author Tang
 * @version v1.0
 */
public class NotifyUtil implements I18nKey {
    private static final Logger LOG = Logger.getInstance(NotifyUtil.class);

    private static final String NOTIFICATION_GROUP_NONE = "cn.tangshh.StockWatcher.None";
    private static final String NOTIFICATION_GROUP_BALLOON = "cn.tangshh.StockWatcher.Balloon";
    private static final String NOTIFICATION_GROUP_STICKY_BALLOON = "cn.tangshh.StockWatcher.StickyBalloon";
    private static final String NOTIFICATION_GROUP_TOOL_WINDOW = "cn.tangshh.StockWatcher.ToolWindow";

    private NotifyUtil() {
        // 私有构造函数，防止实例化
    }

    /**
     * 显示弹窗通知
     *
     * @param message 消息内容
     * @param type    通知等级
     * @param project 项目实例，默认使用默认项目
     */
    public static void popup(String message, NotificationType type, Project project) {
        try {
            Project targetProject = project != null ? project : ProjectManagerEx.getInstanceEx().getDefaultProject();
            NotificationGroupManager.getInstance()
                    .getNotificationGroup(NOTIFICATION_GROUP_NONE)
                    .createNotification(
                            I18nUtil.message(CONFIG_TITLE),
                            message,
                            type
                    )
                    .notify(targetProject);
        } catch (Exception e) {
            LOG.error("通知工具调用失败", e);
        }
    }

    /**
     * 显示弹窗通知（使用默认项目）
     *
     * @param message 消息内容
     * @param type    通知等级
     */
    public static void popup(String message, NotificationType type) {
        popup(message, type, null);
    }

    /**
     * 显示气球样式的通知
     *
     * @param message 消息内容
     * @param type    通知等级
     * @param project 项目实例，默认使用默认项目
     */
    public static void balloon(String message, NotificationType type, Project project) {
        try {
            Project targetProject = project != null ? project : ProjectManagerEx.getInstanceEx().getDefaultProject();
            NotificationGroupManager.getInstance()
                    .getNotificationGroup(NOTIFICATION_GROUP_BALLOON)
                    .createNotification(
                            I18nUtil.message(CONFIG_TITLE),
                            message,
                            type
                    )
                    .notify(targetProject);
        } catch (Exception e) {
            Log.error("通知工具调用失败", e);
        }
    }

    /**
     * 显示气球样式的通知（使用默认项目）
     *
     * @param message 消息内容
     * @param type    通知等级
     */
    public static void balloon(String message, NotificationType type) {
        balloon(message, type, null);
    }

    /**
     * 显示粘性气球样式的通知
     *
     * @param message 消息内容
     * @param type    通知等级
     * @param project 项目实例，默认使用默认项目
     */
    public static void stickyBalloon(String message, NotificationType type, Project project) {
        try {
            Project targetProject = project != null ? project : ProjectManagerEx.getInstanceEx().getDefaultProject();
            NotificationGroupManager.getInstance()
                    .getNotificationGroup(NOTIFICATION_GROUP_STICKY_BALLOON)
                    .createNotification(
                            I18nUtil.message(CONFIG_TITLE),
                            message,
                            type
                    )
                    .notify(targetProject);
        } catch (Exception e) {
            Log.error("通知工具调用失败", e);
        }
    }

    /**
     * 显示粘性气球样式的通知（使用默认项目）
     *
     * @param message 消息内容
     * @param type    通知等级
     */
    public static void stickyBalloon(String message, NotificationType type) {
        stickyBalloon(message, type, null);
    }

    /**
     * 显示工具窗口通知
     *
     * @param message 消息内容
     * @param type    通知等级
     * @param project 项目实例，默认使用默认项目
     */
    public static void toolWindow(String message, NotificationType type, Project project) {
        try {
            Project targetProject = project != null ? project : ProjectManagerEx.getInstanceEx().getDefaultProject();
            NotificationGroupManager.getInstance()
                    .getNotificationGroup(NOTIFICATION_GROUP_TOOL_WINDOW)
                    .createNotification(
                            I18nUtil.message(CONFIG_TITLE),
                            message,
                            type
                    )
                    .notify(targetProject);
        } catch (Exception e) {
            Log.error("通知工具调用失败", e);
        }
    }

    /**
     * 显示工具窗口通知（使用默认项目）
     *
     * @param message 消息内容
     * @param type    通知等级
     */
    public static void toolWindow(String message, NotificationType type) {
        toolWindow(message, type, null);
    }
}