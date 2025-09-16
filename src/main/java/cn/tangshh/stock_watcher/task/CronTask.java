package cn.tangshh.stock_watcher.task;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.tangshh.stock_watcher.constant.I18nKey;
import cn.tangshh.stock_watcher.util.LogUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 定时任务
 *
 * @author Tang
 * @version v1.0
 */
public class CronTask implements I18nKey {
    private final String UUID = RandomUtil.randomString(16);
    private Scheduler scheduler;

    /**
     * 启动定时任务
     */
    public void start(String corn, Runnable task) {
        JobDetail job = JobBuilder.newJob(RunnableJob.class)
                .withIdentity(StrUtil.format("Job-{}", UUID), UUID) // 任务标识（名称+组名）
                .build();
        job.getJobDataMap().put("task", task);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(StrUtil.format("Trigger-{}", UUID), UUID) // 触发器标识
                .withSchedule(CronScheduleBuilder.cronSchedule(corn))
                .build();
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (Exception e) {
            LogUtil.print("定时任务启动失败：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 停止定时任务
     */
    public void stop() {
        if (scheduler != null) {
            try {
                JobKey jobKey = JobKey.jobKey(StrUtil.format("Job-{}", UUID), UUID);
                scheduler.pauseJob(jobKey);
                scheduler.deleteJob(jobKey);
            } catch (Exception e) {
                LogUtil.print("定时任务关闭失败：{}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

}