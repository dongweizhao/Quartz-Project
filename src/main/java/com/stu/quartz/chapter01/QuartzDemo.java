package com.stu.quartz.chapter01;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 编程式启动
 */
public class QuartzDemo {
    public void quartzTest() throws InterruptedException {
        //获取调度器
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.clear();
            scheduler.start();
            //创建任务器：定义任务细节
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();
            ScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
            //定义触发器
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "simpleTriggerGroup")
                    .withSchedule(scheduleBuilder).startNow().build();

            //将任务和触发器注册到调度器中
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        Thread.sleep(1000 * 30);
    }

    public static void main(String[] args) throws InterruptedException {
        QuartzDemo quartzDemo = new QuartzDemo();
        quartzDemo.quartzTest();
    }
}
