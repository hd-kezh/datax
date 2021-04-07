package com.asiainfo.datax.web.service;

import com.asiainfo.datax.web.common.Constant;
import com.asiainfo.datax.web.dao.DataxPocTaskDao;
import com.asiainfo.datax.web.entity.DataxPocTask;
import com.asiainfo.datax.web.vo.TaskAggResponse;
import com.asiainfo.datax.web.vo.TaskAggResponse.TaskAggResponseBuilder;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorService
{
    @Autowired
    private DataxPocTaskDao dataxPocTaskDao;

    public TaskAggResponse taskAgg()
    {
        List<Map<String, Object>> result = this.dataxPocTaskDao.groupByState();
        int successCount = 0;
        int completeCount = 0;
        int failCount = 0;
        int delayCount = 0;
        int retryCount = 0;
        int runningCount = 0;
        int initCount = 0;
        for (Map<String, Object> map : result)
        {
            Object status = map.get("state");
            if (Constant.TASK_SUCCESS_STATE.equals(status))
            {
                successCount = (int)(successCount + ((Long)map.get("count")).longValue());
                completeCount = (int)(completeCount + ((Long)map.get("count")).longValue());
                retryCount += ((BigDecimal)map.get("rerunCount")).intValue();
            }
            else if (Constant.TASK_FAIL_STATE.equals(status))
            {
                failCount = (int)(failCount + ((Long)map.get("count")).longValue());
                completeCount = (int)(completeCount + ((Long)map.get("count")).longValue());
                retryCount += ((BigDecimal)map.get("rerunCount")).intValue();
            }
            else if (Constant.TASK_RUNNING_STATE.equals(status))
            {
                runningCount = (int)(runningCount + ((Long)map.get("count")).longValue());
            }
            else if (Constant.TASK_INIT_STATE.equals(status))
            {
                initCount = (int)(initCount + ((Long)map.get("count")).longValue());
            }
        }
        return

                TaskAggResponse.builder().completeCount(Integer.valueOf(completeCount)).successCount(Integer.valueOf(successCount)).delayCount(Integer.valueOf(delayCount)).failCount(Integer.valueOf(failCount)).retryCount(Integer.valueOf(retryCount)).runningCount(Integer.valueOf(runningCount)).initCount(Integer.valueOf(initCount)).build();
    }

    public Map<String, Object> estimateCompleteTime(Long taskId)
    {
        DataxPocTask dataxPocTask = this.dataxPocTaskDao.selectById(taskId);
        Integer dealNum = Integer.valueOf(Integer.parseInt(dataxPocTask.getDealingNum()));
        int SUM = 1000000;
        int noDealNum = SUM - dealNum.intValue();
        int speed = Integer.parseInt(dataxPocTask.getDealRate());
        int timeCount = (int)Math.ceil(noDealNum / speed);

        return Collections.singletonMap("TIME", Integer.valueOf(timeCount));
    }
}
