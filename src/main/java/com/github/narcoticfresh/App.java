package com.github.narcoticfresh;

import com.github.narcoticfresh.model.JobConfig;
import java.util.Map.Entry;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    private final static Logger LOG = LoggerFactory
        .getLogger(App.class);

    public static void main( String[] args )
    {
        try {
            new JobConfiguration("/home/dn/git/quartz-cron/configuration.yml");

            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            for (Entry<String, JobConfig> item : JobConfiguration.getConfiguration().getJobs().entrySet()) {
                JobDetail jobDetail = JobBuilder
                    .newJob(ExecJob.class)
                    .withIdentity(item.getKey())
                    .usingJobData("jobName", item.getKey())
                    .build();

                // trigger
                Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(item.getKey() + "-schedule")
                    .withSchedule(CronScheduleBuilder.cronSchedule(item.getValue().getSchedule()))
                    //.startNow()
                    .forJob(item.getKey())
                    .build();

                scheduler.scheduleJob(jobDetail, trigger);
            }

            scheduler.start();

            LOG.info("Scheduler started...");

        } catch (Exception se) {
            se.printStackTrace();
            System.exit(-1);
        }
    }
}
