package com.stu.quartz.chapter03;

import org.quartz.SchedulerException;
import org.quartz.impl.StdScheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Created by dongweizhao on 17/9/25.
 * 基于spring的集群配置
 */
public class QuartsClusterDemo3 {
    public static void main(String[] args) throws InterruptedException, SchedulerException {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("03/spring-quartz.xml");
    }
}
