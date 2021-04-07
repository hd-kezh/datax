package com.asiainfo.datax.web.entity;

import java.util.Date;

public class DataxPocTaskLog
{
    private Long taskId;
    private String logName;
    private Date createTime;

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $taskId = getTaskId();result = result * 59 + ($taskId == null ? 43 : $taskId.hashCode());Object $logName = getLogName();result = result * 59 + ($logName == null ? 43 : $logName.hashCode());Object $createTime = getCreateTime();result = result * 59 + ($createTime == null ? 43 : $createTime.hashCode());return result;
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof DataxPocTaskLog;
    }

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public void setLogName(String logName)
    {
        this.logName = logName;
    }

    public String toString()
    {
        return "DataxPocTaskLog(taskId=" + getTaskId() + ", logName=" + getLogName() + ", createTime=" + getCreateTime() + ")";
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DataxPocTaskLog)) {
            return false;
        }
        DataxPocTaskLog other = (DataxPocTaskLog)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$taskId = getTaskId();Object other$taskId = other.getTaskId();
        if (this$taskId == null ? other$taskId != null : !this$taskId.equals(other$taskId)) {
            return false;
        }
        Object this$logName = getLogName();Object other$logName = other.getLogName();
        if (this$logName == null ? other$logName != null : !this$logName.equals(other$logName)) {
            return false;
        }
        Object this$createTime = getCreateTime();Object other$createTime = other.getCreateTime();return this$createTime == null ? other$createTime == null : this$createTime.equals(other$createTime);
    }

    public Long getTaskId()
    {
        return this.taskId;
    }

    public String getLogName()
    {
        return this.logName;
    }

    public Date getCreateTime()
    {
        return this.createTime;
    }
}
