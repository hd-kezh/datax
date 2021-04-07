package com.asiainfo.datax.web.config;

import org.jboss.logging.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleConfig {
    private Logger log = Logger.getLogger(this.getClass());

    @Scheduled(cron = "0 0/1 * * * ?")
    public void jobdemo(){
        log.info("----------------AUTO JOB START----------------------");
    }
}
