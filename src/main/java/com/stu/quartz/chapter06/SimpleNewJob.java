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
 
package com.stu.quartz.chapter06;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * <p>
 * A dumb implementation of Job, for unit testing purposes.
 * </p>
 * 
 * @author James House
 */
public class SimpleNewJob implements Job {

  private static Logger       _log  = LoggerFactory.getLogger(SimpleNewJob.class);

  public SimpleNewJob() {
  }

  public void execute(JobExecutionContext context) throws JobExecutionException {

    JobKey jobKey = context.getJobDetail().getKey();

    _log.info("SimpleNewJob: " + jobKey + " done at " + new Date());

  }

}