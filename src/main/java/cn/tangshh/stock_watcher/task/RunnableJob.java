package cn.tangshh.stock_watcher.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * 可运行作业
 *
 * @author Tang
 * @version v1.0
 */
public class RunnableJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        Object job = context.getMergedJobDataMap().get("task");
        if (job instanceof Runnable task) {
            task.run();
        }
    }
}