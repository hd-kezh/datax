package com.asiainfo.datax.web.controller;

import com.asiainfo.datax.web.service.MonitorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/datax/monitor"})
public class MonitorController
{
    private static final Logger log = LoggerFactory.getLogger(MonitorController.class);
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MonitorService monitorService;

    @Scheduled(fixedRate=3000L)
    public void taskAgg()
            throws Exception
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = objectMapper.writeValueAsString(this.monitorService.taskAgg());
        this.messagingTemplate.convertAndSend("/monitor/taskAgg", jsonResult);
    }

    @RequestMapping(value={"/estimateCompleteTime"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public Map<String, Object> estimateCompleteTime(@RequestParam("TASK_ID") Long taskId)
    {
        return this.monitorService.estimateCompleteTime(taskId);
    }
}
