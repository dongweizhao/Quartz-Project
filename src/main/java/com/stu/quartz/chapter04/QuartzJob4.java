package com.stu.quartz.chapter04;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by dongweizhao on 17/9/25.
 */
public class QuartzJob4 implements Job {

    private static Logger _log = LoggerFactory.getLogger(QuartzJob4.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("QuartzJob4: " + jobKey + " done at " + new Date());
    }
}
