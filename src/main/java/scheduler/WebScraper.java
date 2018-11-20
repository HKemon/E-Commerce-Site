package scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class WebScraper {
    public void WebScraperRun() {
        try {
            JobDetail job = JobBuilder.newJob(WebScraperScheduler.class)
                    .withIdentity("webScraperScheduler")
                    .build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(5).repeatForever())
                    .build();
            SchedulerFactory schFactory = new StdSchedulerFactory();
            Scheduler scheduler = schFactory.getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}