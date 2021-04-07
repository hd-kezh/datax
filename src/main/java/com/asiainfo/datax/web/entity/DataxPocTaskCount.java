package com.asiainfo.datax.web.entity;

public class DataxPocTaskCount
{
    private Long taskId;
    protected Long count;

    public void setCount(Long count)
    {
        this.count = count;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $taskId = getTaskId();result = result * 59 + ($taskId == null ? 43 : $taskId.hashCode());Object $count = getCount();result = result * 59 + ($count == null ? 43 : $count.hashCode());return result;
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof DataxPocTaskCount;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DataxPocTaskCount)) {
            return false;
        }
        DataxPocTaskCount other = (DataxPocTaskCount)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$taskId = getTaskId();Object other$taskId = other.getTaskId();
        if (this$taskId == null ? other$taskId != null : !this$taskId.equals(other$taskId)) {
            return false;
        }
        Object this$count = getCount();Object other$count = other.getCount();return this$count == null ? other$count == null : this$count.equals(other$count);
    }

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public String toString()
    {
        return "DataxPocTaskCount(taskId=" + getTaskId() + ", count=" + getCount() + ")";
    }

    public Long getTaskId()
    {
        return this.taskId;
    }

    public Long getCount()
    {
        return this.count;
    }

    public DataxPocTaskCount(Long taskId, Long count)
    {
        this.taskId = taskId;
        this.count = count;
    }

    public DataxPocTaskCount() {}
}
