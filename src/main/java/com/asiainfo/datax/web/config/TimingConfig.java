package com.asiainfo.datax.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class TimingConfig
{
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler()
    {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public TimingManager timingManager(ThreadPoolTaskScheduler threadPoolTaskScheduler)
    {
        return new TimingManager(threadPoolTaskScheduler);
    }
}
