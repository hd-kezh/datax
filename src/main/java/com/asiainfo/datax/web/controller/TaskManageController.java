package com.asiainfo.datax.web.controller;

import com.asiainfo.datax.web.entity.DataxPocTask;
import com.asiainfo.datax.web.entity.DataxPocTaskCount;
import com.asiainfo.datax.web.service.TaskManageService;
import io.swagger.annotations.ApiImplicitParams;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/datax/manage"})
public class TaskManageController
{
    private static final Logger log = LoggerFactory.getLogger(TaskManageController.class);
    @Autowired
    private TaskManageService taskManageService;

    @RequestMapping(value={"/schedule"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public Map<String, Object> schedule(@RequestParam("TASK_ID") Long taskId)
    {
        return this.taskManageService.schedule(taskId);
    }

    @RequestMapping(value={"/stop"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public Map<String, Object> stop(@RequestParam("TASK_ID") Long taskId)
    {
        return this.taskManageService.stop(taskId);
    }

    @RequestMapping(value={"/getTaskField"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String getTaskField(@RequestParam("TASK_ID") Long taskId)
    {
        return this.taskManageService.getTaskField(taskId);
    }

    @RequestMapping({"/updateTaskField"})
    @ApiImplicitParams({@io.swagger.annotations.ApiImplicitParam(name="ERROR_INFO", paramType="form", required=false)})
    public String updateTaskField(@RequestParam(value="RSV_STR1", required=false) String rsvStr1, @RequestParam(value="RSV_STR2", required=false) String rsvStr2, @RequestParam(value="STATE", required=false) Integer state, @RequestParam(value="DEAL_NUM", required=false) Integer dealNum, @RequestParam(value="ERROR_INFO", required=false) String errorInfo, @RequestParam("TASK_ID") Long taskId, @RequestParam(value="END_TIME", required=false) String endTime, @RequestParam(value="DEALING_NUM", required=false) String dealingNum, @RequestParam(value="DEAL_RATE", required=false) String dealRate)
    {
        DataxPocTask dataxPocTask = new DataxPocTask();
        dataxPocTask.setId(taskId);
        dataxPocTask.setRsvStr1(rsvStr1);
        dataxPocTask.setRsvStr2(rsvStr2);
        dataxPocTask.setErrorInfo(errorInfo);
        dataxPocTask.setState(state);
        dataxPocTask.setDealNum(dealNum);
        dataxPocTask.setDealingNum(dealingNum);
        dataxPocTask.setDealRate(dealRate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try
        {
            dataxPocTask.setEndTime(endTime != null ? sdf.parse(endTime) : null);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        log.info(dataxPocTask.toString());
        return this.taskManageService.updateTaskField(dataxPocTask);
    }

    @RequestMapping(value={"/getStartTimeCurr"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String getStartTimeCurr(@RequestParam("TASK_ID") Long taskId)
    {
        return String.valueOf(this.taskManageService.getStartTimeCurr(taskId));
    }

    @RequestMapping(value={"/addTaskCount"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String addTaskCount(@RequestParam("TASK_ID") Long taskId, @RequestParam("COUNT") Long count)
    {
        return this.taskManageService.addTaskCount(new DataxPocTaskCount(taskId, count));
    }
}
