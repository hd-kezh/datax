package com.asiainfo.datax.web.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class TimingManager
{
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private ConcurrentHashMap<Long, ScheduledFuture> runInfoMap = new ConcurrentHashMap();

    public TimingManager(ThreadPoolTaskScheduler threadPoolTaskScheduler)
    {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    public void addTask(Long id, Runnable task, long period)
    {
        synchronized (this.runInfoMap)
        {
            removeTask(id);
            ScheduledFuture future = this.threadPoolTaskScheduler.scheduleWithFixedDelay(task, period * 1000L);
            this.runInfoMap.put(id, future);
        }
    }

    public void removeTask(Long id)
    {
        synchronized (this.runInfoMap)
        {
            ScheduledFuture taskRunInfo = (ScheduledFuture)this.runInfoMap.remove(id);
            if (taskRunInfo != null) {
                taskRunInfo.cancel(true);
            }
        }
    }
}
