package cn.tangshh.stock_watcher.task;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.Scheduler;
import cn.hutool.cron.task.Task;
import cn.tangshh.stock_watcher.constant.I18nKey;
import cn.tangshh.stock_watcher.util.I18nUtil;
import cn.tangshh.stock_watcher.util.NotifyUtil;
import com.intellij.notification.NotificationType;

/**
 * 定时任务
 *
 * @author Tang
 * @version v1.0
 */
public class CronTask implements I18nKey {
    private final String UUID = RandomUtil.randomString(16);

    /**
     * 启动定时任务
     */
    public void start(String corn, Task task) {
        if (StrUtil.isBlank(corn)) {
            NotifyUtil.balloon(I18nUtil.message(CONFIG_ERROR_REFRESH_CYCLE), NotificationType.ERROR);
            return;
        }
        CronUtil.setMatchSecond(true);
        CronUtil.schedule(UUID, corn, task);
        CronUtil.start();
    }

    /**
     * 停止定时任务
     */
    public void stop() {
        Scheduler scheduler = CronUtil.getScheduler();
        if (scheduler.isStarted()) {
            CronUtil.stop();
        }
        CronUtil.remove(UUID);
    }
}