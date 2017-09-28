/* 
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

package com.stu.quartz.chapter04;

import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by dongweizhao on 17/9/25.
 *半注入式集群测试
 * 1、启动ClusterExample4
 * 2、启动ClusterExampleRunA
 * 3、启动ClusterExampleRunB
 * 观察任务负载情况,并停掉一台服务器观察任务转移详情
 */

public class ClusterExample4 {

    private static Logger _log = LoggerFactory.getLogger(ClusterExample4.class);

    public void run() {
        try {
            boolean scheduleJobs = true;
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("04/spring-quartz.xml");

            StdScheduler scheduler = (StdScheduler) applicationContext.getBean("scheduler");

            if (scheduleJobs) {
                scheduler.clear();
                String schedId = scheduler.getSchedulerInstanceId();
                int count = 1;
                JobDetail job = newJob(QuartzJob4.class).withIdentity("job_" + count, schedId).requestRecovery().build();
                SimpleTrigger trigger = newTrigger().withIdentity("triger_" + count, schedId)
                        .startAt(futureDate(1, DateBuilder.IntervalUnit.SECOND))
                        .withSchedule(simpleSchedule().withRepeatCount(2000).withIntervalInSeconds(5)).build();

                _log.info(job.getKey() + " will run at: " + trigger.getNextFireTime() + " and repeat: "
                        + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");
                scheduler.scheduleJob(job, trigger);
                count++;
                job = newJob(QuartzJob4.class).withIdentity("job_" + count, schedId).requestRecovery().build();
                trigger = newTrigger().withIdentity("triger_" + count, schedId).startAt(futureDate(2, DateBuilder.IntervalUnit.SECOND))
                        .withSchedule(simpleSchedule().withRepeatCount(2000).withIntervalInSeconds(5)).build();

                _log.info(job.getKey() + " will run at: " + trigger.getNextFireTime() + " and repeat: "
                        + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");
                scheduler.scheduleJob(job, trigger);
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        ClusterExample4 example = new ClusterExample4();
        example.run();
    }
}
