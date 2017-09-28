package com.stu.quartz.chapter05;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by dongweizhao on 17/9/25.
 * 编程式集群测试
 * 1、启动ClusterExample5
 * 2、启动ClusterExampleRunA
 * 3、启动ClusterExampleRunB
 * 观察任务负载情况,并停掉一台服务器观察任务转移详情
 */

public class ClusterExample5 {

    private static Logger _log = LoggerFactory.getLogger(ClusterExample5.class);

    public void run(boolean inClearJobs, boolean inScheduleJobs) throws Exception {

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        if (inClearJobs) {
            _log.warn("***** Deleting existing jobs/triggers *****");
            sched.clear();
        }

        _log.info("------- Initialization Complete -----------");

        if (inScheduleJobs) {

            _log.info("------- Scheduling Jobs ------------------");

            String schedId = sched.getSchedulerInstanceId();

            int count = 1;
            JobDetail job = newJob(SimpleRecoveryJob.class).withIdentity("job_" + count, schedId).requestRecovery().build();
            SimpleTrigger trigger = newTrigger().withIdentity("triger_" + count, schedId)
                    .startAt(futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(simpleSchedule().withRepeatCount(2000).withIntervalInSeconds(5)).build();

            _log.info(job.getKey() + " will run at: " + trigger.getNextFireTime() + " and repeat: "
                    + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");
            sched.scheduleJob(job, trigger);
            count++;


            job = newJob(SimpleRecoveryJob.class).withIdentity("job_" + count, schedId).requestRecovery().build();
            trigger = newTrigger().withIdentity("triger_" + count, schedId).startAt(futureDate(2, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(simpleSchedule().withRepeatCount(2000).withIntervalInSeconds(5)).build();

            _log.info(job.getKey() + " will run at: " + trigger.getNextFireTime() + " and repeat: "
                    + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");
            sched.scheduleJob(job, trigger);


        }

        // jobs don't start firing until start() has been called...
        _log.info("------- Starting Scheduler ---------------");
        //sched.start();
        _log.info("------- Started Scheduler ----------------");

        _log.info("------- Waiting for one hour... ----------");
        try {
            Thread.sleep(3600L * 1000L);
        } catch (Exception e) {
            //
        }

        _log.info("------- Shutting Down --------------------");
        sched.shutdown();
        _log.info("------- Shutdown Complete ----------------");
    }


    public static void main(String[] args) throws Exception {
        boolean clearJobs = true;
        boolean scheduleJobs = true;

        ClusterExample5 example = new ClusterExample5();
        example.run(clearJobs, scheduleJobs);
    }
}
