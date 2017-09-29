package com.stu.quartz.chapter06;

import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by dongweizhao on 17/9/29.
 * 动态任务添加、删除、修改测试
 */
public class JobTest {
    private static Logger _log = LoggerFactory.getLogger(JobTest.class);

    @org.junit.Test
    public void startA() throws SchedulerException, InterruptedException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        sched.clear();
        sched.start();
        _log.info("ClusterExampleRun A started");
        TimeUnit.SECONDS.sleep(50);
        sched.shutdown();
    }

    @org.junit.Test
    public void startB() throws SchedulerException, InterruptedException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        sched.start();
        _log.info("ClusterExampleRun B started");
        TimeUnit.SECONDS.sleep(1000);
        sched.shutdown();
    }


    @org.junit.Test
    public void addNewJob() throws SchedulerException, InterruptedException {
        QuartzManager.addJob("new", "newGroup", "0/5 * * * * ?", SimpleNewJob.class);
    }

    @org.junit.Test
    public void addSimpleRecoveryJob() throws SchedulerException {
        QuartzManager.addJob("simpleRecovery", "simpleRecoveryGroup", "0/2 * * * * ?", SimpleRecoveryJob.class);
    }

    @Test
    public void removeNewJob() throws SchedulerException {
        QuartzManager.removeJob("new", "newGroup");
    }

    @Test
    public void modifyNewJob() throws SchedulerException {
        QuartzManager.modifyJob("new", "newGroup", "0/10 * * * * ?");
    }



}
