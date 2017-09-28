package com.stu.quartz.chapter03;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * Created by dongweizhao on 17/9/26.
 */
public class MyDetailQuartzJobBean extends QuartzJobBean{

    private Object targetObject;
    private String targetMethod;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            Method m = null;
            try {
                m = targetObject.getClass().getMethod(targetMethod, new Class[] {});
                m.invoke(targetObject, new Object[] {});
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }
}
