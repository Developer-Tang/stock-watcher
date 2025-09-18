package cn.tangshh.stock_watcher.task;

import cn.tangshh.stock_watcher.constant.I18nKey;
import cn.tangshh.stock_watcher.util.RandomUtil;
import com.intellij.openapi.diagnostic.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 定时任务
 *
 * @author Tang
 * @version v1.0
 */
public class CronTask implements I18nKey {
    private final static Logger LOG = Logger.getInstance(CronTask.class);
    private final String UUID = RandomUtil.randomStr(16);
    private Scheduler scheduler;

    /**
     * 启动定时任务
     */
    public void start(String corn, Runnable task) {
        JobDetail job = JobBuilder.newJob(RunnableJob.class)
                .withIdentity(String.format("Job-%s", UUID), UUID) // 任务标识（名称+组名）
                .build();
        job.getJobDataMap().put("task", task);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(String.format("Trigger-%s", UUID), UUID) // 触发器标识
                .withSchedule(CronScheduleBuilder.cronSchedule(corn))
                .build();
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (Exception e) {
            LOG.error("定时任务启动失败", e);
        }
    }

    /**
     * 停止定时任务
     */
    public void stop() {
        if (scheduler != null) {
            try {
                JobKey jobKey = JobKey.jobKey(String.format("Job-%s", UUID), UUID);
                scheduler.pauseJob(jobKey);
                scheduler.deleteJob(jobKey);
            } catch (Exception e) {
                LOG.error("定时任务关闭失败", e);
            }
        }
    }

}