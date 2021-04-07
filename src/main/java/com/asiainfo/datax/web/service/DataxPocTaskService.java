//
// Decompiled by Procyon v0.5.36
//

package com.asiainfo.datax.web.service;

import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.function.Consumer;
import com.asiainfo.datax.web.entity.DataxPocTask;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import com.asiainfo.datax.web.dao.DataxPocTaskCountDao;
import com.asiainfo.datax.web.dao.DataxPocTaskDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DataxPocTaskService
{
    private static final Logger log;
    private final DataxPocTaskDao dataxPocTaskDao;
    private final DataStashService dataStashService;
    private final DataxPocTaskCountDao dataxPocTaskCountDao;
    private ExecutorService executor;

    public DataxPocTaskService(final DataxPocTaskDao dataxPocTaskDao, final DataStashService dataStashService, final DataxPocTaskCountDao dataxPocTaskCountDao) {
        this.executor = Executors.newCachedThreadPool();
        this.dataxPocTaskDao = dataxPocTaskDao;
        this.dataStashService = dataStashService;
        this.dataxPocTaskCountDao = dataxPocTaskCountDao;
    }

    public List<DataxPocTask> getDataxPocTaskList(final int state, final int type, final int rerunCount, final Long id, String orderType, String desc, final String remark) {
        if (null == orderType) {
            orderType = "start_time";
        }
        if ("1".equals(desc)) {
            desc = null;
        }
        else {
            desc = "DESC";
        }
        final List<DataxPocTask> dataxPocTasks = this.dataxPocTaskDao.listTask(state, type, rerunCount, id, orderType, desc, remark);
        dataxPocTasks.forEach(this::lambda$getDataxPocTaskList$0);
        return dataxPocTasks;
    }

    public String insertTask(final DataxPocTask dataxPocTask) {
        dataxPocTask.setCreateTime(new Date());
        dataxPocTask.setState(Integer.valueOf(0));
        dataxPocTask.setRerunCount(Integer.valueOf(0));
        dataxPocTask.setType(Integer.valueOf(1));
        final boolean insert = this.dataxPocTaskDao.insertTask(dataxPocTask);
        try {
            this.dataStashService.dealConfigData(dataxPocTask.getUnitCode(), dataxPocTask.getId());
        }
        catch (Exception e) {
            DataxPocTaskService.log.error(e.getMessage());
            throw new RuntimeException("\u6839\u636e\u914d\u7f6e\u751f\u4ea7datax json\u6587\u4ef6\u5f02\u5e38");
        }
        if (insert) {
            return "true";
        }
        return "false";
    }

    public String updateTask(final DataxPocTask dataxPocTask) {
        final boolean update = this.dataxPocTaskDao.updateTaskById(dataxPocTask);
        try {
            this.dataStashService.dealConfigData(dataxPocTask.getUnitCode(), dataxPocTask.getId());
        }
        catch (Exception e) {
            DataxPocTaskService.log.error(e.getMessage());
            throw new RuntimeException("\u6839\u636e\u914d\u7f6e\u751f\u4ea7datax json\u6587\u4ef6\u5f02\u5e38");
        }
        if (update) {
            return "true";
        }
        return "false";
    }

    private void createConfigFile(final String unitCode, final Long taskId) {
        this.executor.submit(this::lambda$createConfigFile$1);
    }

    static {
        DataxPocTaskService.log = LoggerFactory.getLogger((Class)DataxPocTaskService.class);
    }
}
