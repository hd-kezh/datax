//
// Decompiled by Procyon v0.5.36
//

package com.asiainfo.datax.web.service;

import org.slf4j.LoggerFactory;
import com.asiainfo.datax.web.entity.DataxPocTaskCount;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.asiainfo.datax.web.common.Constant;
import java.util.Date;
import com.asiainfo.datax.web.entity.DataxPocTaskLog;
import com.asiainfo.datax.web.util.IdUtil;
import java.util.ArrayList;
import java.util.Iterator;
import com.asiainfo.datax.web.entity.DataxPocTask;
import com.asiainfo.datax.web.entity.DataxPocTaskConfig;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.asiainfo.datax.web.util.ShellUtil;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.asiainfo.datax.web.config.TimingManager;
import com.asiainfo.datax.web.dao.DataxPocTaskCountDao;
import com.asiainfo.datax.web.dao.DataxPocTaskDao;
import com.asiainfo.datax.web.dao.DataxPocTaskLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import com.asiainfo.datax.web.dao.DataxPocTaskConfigDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TaskManageService
{
    private static final Logger log;
    @Autowired
    private DataxPocTaskConfigDao dataxPocTaskConfigDao;
    @Autowired
    private DataxPocTaskLogDao dataxPocTaskLogDao;
    @Autowired
    private DataxPocTaskDao dataxPocTaskDao;
    @Autowired
    private DataxPocTaskCountDao dataxPocTaskCountDao;
    @Autowired
    private TimingManager timingManager;

    public Map<String, Object> schedule(final Long taskId) {
        try {
            final List<DataxPocTaskConfig> dataxPocTaskConfigs = (List<DataxPocTaskConfig>)this.dataxPocTaskConfigDao.selectByTaskId(taskId);
            final DataxPocTask dataxPocTask = this.dataxPocTaskDao.selectById(taskId);
            final String type = dataxPocTask.getStartType();
            if ("1".equals(type)) {
                final List<String> params = (List<String>)this.getStartParam(taskId, dataxPocTaskConfigs);
                if (params == null || params.size() == 0) {
                    return (Map<String, Object>)Collections.singletonMap("MESSAGE", "TASK_ID:" + taskId + " json\u914d\u7f6e\u6587\u4ef6\u4e0d\u5b58\u5728");
                }
                this.updateTask(dataxPocTask);
                if (this.execTask(params, dataxPocTask.getRemark(), taskId) != 0) {
                    return (Map<String, Object>)Collections.singletonMap("MESSAGE", this.startFailMessage(taskId));
                }
            }
            else if ("2".equals(type)) {
                final List<String> params = (List<String>)this.getStartParam(taskId, dataxPocTaskConfigs);
                if (params == null || params.size() == 0) {
                    return (Map<String, Object>)Collections.singletonMap("MESSAGE", "TASK_ID:" + taskId + " json\u914d\u7f6e\u6587\u4ef6\u4e0d\u5b58\u5728");
                }
                this.updateTask(dataxPocTask);
                for (final String s : params) {
                    final String result = ShellUtil.execShell("/home/2ipoc1/dataxETL/shell/task_entry.sh", s, "2", String.valueOf(taskId));
                    if (result.indexOf("success") == -1) {
                        return (Map<String, Object>)Collections.singletonMap("MESSAGE", this.startFailMessage(taskId));
                    }
                }
            }
            else if ("3".equals(type)) {
                if (dataxPocTask.getCycle() == null) {
                    return (Map<String, Object>)Collections.singletonMap("MESSAGE", "TASK_ID:" + taskId + " \u5468\u671f\u914d\u7f6e\u5f02\u5e38");
                }
                this.timingManager.addTask(taskId, this::lambda$schedule$0, (long)dataxPocTask.getCycle());
            }
            return (Map<String, Object>)Collections.singletonMap("MESSAGE", "SUCCESS");
        }
        catch (Exception e) {
            e.printStackTrace();
            final StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            TaskManageService.log.error(sw.toString());
            return (Map<String, Object>)Collections.singletonMap("MESSAGE", "FAIL");
        }
    }

    public List<String> getStartParam(final Long taskId, final List<DataxPocTaskConfig> dataxPocTaskConfigs) {
        final List<String> params = new ArrayList<String>();
        for (final DataxPocTaskConfig dataxPocTaskConfig : dataxPocTaskConfigs) {
            final String configJsonName = dataxPocTaskConfig.getJsonConfigName();
            if (configJsonName == null || "".equals(configJsonName)) {
                return null;
            }
            final String logName = "/home/2ipoc1/dataxETL/logs/" + IdUtil.generate() + ".log";
            final DataxPocTaskLog dataxPocTaskLog = new DataxPocTaskLog();
            dataxPocTaskLog.setCreateTime(new Date());
            dataxPocTaskLog.setTaskId(taskId);
            dataxPocTaskLog.setLogName(logName);
            this.dataxPocTaskLogDao.insert(dataxPocTaskLog);
            params.add(taskId + " " + logName + " " + configJsonName);
        }
        return params;
    }

    public void updateTask(final DataxPocTask dataxPocTask) {
        final DataxPocTask newDataxPocTask = new DataxPocTask();
        if (Constant.TASK_INIT_STATE != dataxPocTask.getState()) {
            newDataxPocTask.setRerunCount(Integer.valueOf(dataxPocTask.getRerunCount() + 1));
        }
        newDataxPocTask.setId(dataxPocTask.getId());
        newDataxPocTask.setState(Constant.TASK_RUNNING_STATE);
        if (!dataxPocTask.getStartType().equals("2")) {
            newDataxPocTask.setStartTime(new Date());
        }
        newDataxPocTask.setEndTime((Date)null);
        this.dataxPocTaskCountDao.deleteByTaskId(dataxPocTask.getId());
        this.dataxPocTaskDao.updateTaskAndEndTimeErrorInfoById(newDataxPocTask);
    }

    public String startFailMessage(final Long taskId) throws Exception {
        return new String(Files.readAllBytes(Paths.get("/home/2ipoc1/dataxETC/logs/start/" + taskId + ".log", new String[0])));
    }

    public int execTask(final List<String> params, final String remark, final Long taskId) throws Exception {
        final List<String> params2 = new ArrayList<String>();
        for (final String s : params) {
            params2.add("sh /home/2ipoc1/dataxETL/shell/task_start.sh " + s);
        }
        String result = ShellUtil.execShell("/home/2ipoc1/dataxETL/shell/task_entry.sh", String.join(";", params2), "1", String.valueOf(taskId));
        if (result.indexOf("success") == -1) {
            return 1;
        }
        if ("2".equals(remark)) {
            for (final String s2 : params) {
                final String[] ss = s2.split(" ");
                result = ShellUtil.execShell("/home/2ipoc1/dataxETL/shell/task_speed.sh", ss[0], ss[1], ss[2]);
            }
        }
        return 0;
    }

    public Map<String, Object> stop(final Long taskId) {
        try {
            final List<DataxPocTaskConfig> dataxPocTaskConfigs = (List<DataxPocTaskConfig>)this.dataxPocTaskConfigDao.selectByTaskId(taskId);
            for (final DataxPocTaskConfig dataxPocTaskConfig : dataxPocTaskConfigs) {
                final String configJsonName = dataxPocTaskConfig.getJsonConfigName();
                if (configJsonName == null || "".equals(configJsonName)) {
                    return (Map<String, Object>)Collections.singletonMap("MESSAGE", "TASK_ID:" + taskId + "json\u914d\u7f6e\u6587\u4ef6\u4e0d\u5b58\u5728");
                }
                ShellUtil.execShell("/home/2ipoc1/dataxETL/shell/task_stop.sh", configJsonName);
                this.timingManager.removeTask(taskId);
            }
            final DataxPocTask newDataxPocTask = new DataxPocTask();
            newDataxPocTask.setId(taskId);
            newDataxPocTask.setState(Constant.TASK_MANUAL_STATE);
            this.dataxPocTaskDao.updateTaskById(newDataxPocTask);
            return (Map<String, Object>)Collections.singletonMap("MESSAGE", "SUCCESS");
        }
        catch (Exception e) {
            e.printStackTrace();
            final StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            TaskManageService.log.error(sw.toString());
            return (Map<String, Object>)Collections.singletonMap("MESSAGE", "FAIL");
        }
    }

    public String getTaskField(final Long taskId) {
        final DataxPocTask dataxPocTask = this.dataxPocTaskDao.selectById(taskId);
        return this.toFieldString(dataxPocTask.getRsvStr1()) + "^" + this.toFieldString(dataxPocTask.getState()) + "^" + this.toFieldString(dataxPocTask.getDealNum()) + "^" + this.toFieldString(dataxPocTask.getErrorInfo()) + "^" + this.toFieldString(dataxPocTask.getRsvStr2());
    }

    public String toFieldString(final Object s) {
        return (s == null) ? "" : s.toString();
    }

    public String updateTaskField(final DataxPocTask dataxPocTask) {
        this.dataxPocTaskDao.updateTaskById(dataxPocTask);
        return "success";
    }

    public Long getStartTimeCurr(final Long taskId) {
        return this.dataxPocTaskDao.selectStartTimeCurr(taskId);
    }

    public String addTaskCount(final DataxPocTaskCount dataxPocTaskCount) {
        this.dataxPocTaskCountDao.insert(dataxPocTaskCount);
        return "success";
    }

    static {
        TaskManageService.log = LoggerFactory.getLogger((Class)TaskManageService.class);
    }
}
