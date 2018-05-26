package com.github.narcoticfresh;

import com.github.narcoticfresh.model.JobConfig;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

@DisallowConcurrentExecution
public class ExecJob implements Job {

  private static Logger LOG = LoggerFactory.getLogger(ExecJob.class);

  /**
   * <p>
   * Called by the <code>{@link org.quartz.Scheduler}</code> when a
   * <code>{@link org.quartz.Trigger}</code> fires that is associated with
   * the <code>Job</code>.
   * </p>
   *
   * @throws JobExecutionException if there is an exception while executing the job.
   */
  public void execute(JobExecutionContext context)
      throws JobExecutionException {

    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    String jobName = dataMap.getString("jobName");

    JobConfig jobConfig = JobConfiguration.getJobConfiguration(jobName);

    if (jobConfig == null) {
      throw new JobExecutionException("Could not find configuration for job '"+jobName+"'");
    }

    LOG.info("Starting job with command '{}'...", jobConfig.getCommand());

    try {
      new ProcessExecutor()
          .destroyOnExit()
          .command(jobConfig.getCommand())
          .redirectOutput(Slf4jStream.ofCaller().asInfo())
          .redirectError(Slf4jStream.ofCaller().asError())
          .execute();
    } catch (Exception e) {
      LOG.error("Error in job execution", e);
    }
  }

}