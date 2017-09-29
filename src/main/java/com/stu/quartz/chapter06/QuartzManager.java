package com.stu.quartz.chapter06;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by dongweizhao on 17/9/29.
 * 任务管理类
 */
public class QuartzManager {
    public static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private static Logger _log = LoggerFactory.getLogger(QuartzManager.class);
    private static String triggerPrefix = "trigger_";
    private static String jobPrefix = "job_";

    /**
     * 添加定时任务
     *
     * @param jobName
     * @param time
     * @throws SchedulerException
     */
    public static void addJob(String jobName, String groupName, String time, Class jobClass) throws SchedulerException {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            JobDetail job = newJob(jobClass).withIdentity(jobPrefix + jobName, groupName).requestRecovery().build();
            CronTrigger trigger = newTrigger().withIdentity(triggerPrefix + jobName, groupName).withSchedule(cronSchedule(time))
                    .build();
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            _log.error("添加任务失败,{}", jobName);
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改任务
     *
     * @param jobName
     * @param time
     */
    public static void modifyJob(String jobName,String groupName,String time) {

        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerPrefix + jobName, groupName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                _log.error("不存在此任务,{}", jobName);
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (oldTime != time) {
                trigger = newTrigger().withIdentity(triggerPrefix + jobName, groupName).withSchedule(cronSchedule(time))
                        .build();
                sched.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            _log.error("修改任务失败,{}", jobName);
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个定时任务
     *
     * @param jobName
     * @param groupName
     */
    public static void removeJob(String jobName,String groupName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            boolean isdel = sched.deleteJob(JobKey.jobKey(jobPrefix + jobName, groupName));// 删除任务
            if (isdel) {
                _log.info("删除任务{},成功", jobName);
            } else {
                _log.warn("删除任务{},失败", jobName);
            }
        } catch (SchedulerException e) {
            _log.error("移除任务失败,{}", jobName);
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public static void shutdown(boolean waitForJobsToComplete) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.shutdown(waitForJobsToComplete);
        } catch (SchedulerException e) {
            _log.error("关闭所有定时任务失败");
            throw new RuntimeException(e);
        }
    }

}
