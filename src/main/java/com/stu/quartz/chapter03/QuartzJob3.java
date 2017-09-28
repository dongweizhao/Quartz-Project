package com.stu.quartz.chapter03;

import org.quartz.JobExecutionException;

import java.io.Serializable;

/**
 * Created by dongweizhao on 17/9/25.
 */
public class QuartzJob3 implements Serializable{

    public void runJob() throws JobExecutionException {
        System.out.println("开启定时任务3！！");
    }
}
