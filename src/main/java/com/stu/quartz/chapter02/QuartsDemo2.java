package com.stu.quartz.chapter02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * 基于spring配置方式启动
 */
public class QuartsDemo2 {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("02/spring-quartz.xml");
    }
}
