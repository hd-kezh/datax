package com.asiainfo.datax.web.entity;

public class DataxPocTaskConfig
{
    private Long taskId;
    private String unitCode;
    private String jsonConfigName;
    private String genCost;

    public void setUnitCode(String unitCode)
    {
        this.unitCode = unitCode;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $taskId = getTaskId();result = result * 59 + ($taskId == null ? 43 : $taskId.hashCode());Object $unitCode = getUnitCode();result = result * 59 + ($unitCode == null ? 43 : $unitCode.hashCode());Object $jsonConfigName = getJsonConfigName();result = result * 59 + ($jsonConfigName == null ? 43 : $jsonConfigName.hashCode());Object $genCost = getGenCost();result = result * 59 + ($genCost == null ? 43 : $genCost.hashCode());return result;
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof DataxPocTaskConfig;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DataxPocTaskConfig)) {
            return false;
        }
        DataxPocTaskConfig other = (DataxPocTaskConfig)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$taskId = getTaskId();Object other$taskId = other.getTaskId();
        if (this$taskId == null ? other$taskId != null : !this$taskId.equals(other$taskId)) {
            return false;
        }
        Object this$unitCode = getUnitCode();Object other$unitCode = other.getUnitCode();
        if (this$unitCode == null ? other$unitCode != null : !this$unitCode.equals(other$unitCode)) {
            return false;
        }
        Object this$jsonConfigName = getJsonConfigName();Object other$jsonConfigName = other.getJsonConfigName();
        if (this$jsonConfigName == null ? other$jsonConfigName != null : !this$jsonConfigName.equals(other$jsonConfigName)) {
            return false;
        }
        Object this$genCost = getGenCost();Object other$genCost = other.getGenCost();return this$genCost == null ? other$genCost == null : this$genCost.equals(other$genCost);
    }

    public void setGenCost(String genCost)
    {
        this.genCost = genCost;
    }

    public void setJsonConfigName(String jsonConfigName)
    {
        this.jsonConfigName = jsonConfigName;
    }

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public String toString()
    {
        return "DataxPocTaskConfig(taskId=" + getTaskId() + ", unitCode=" + getUnitCode() + ", jsonConfigName=" + getJsonConfigName() + ", genCost=" + getGenCost() + ")";
    }

    public Long getTaskId()
    {
        return this.taskId;
    }

    public String getUnitCode()
    {
        return this.unitCode;
    }

    public String getJsonConfigName()
    {
        return this.jsonConfigName;
    }

    public String getGenCost()
    {
        return this.genCost;
    }

    public DataxPocTaskConfig(Long taskId, String unitCode, String jsonConfigName, String genCost)
    {
        this.taskId = taskId;
        this.unitCode = unitCode;
        this.jsonConfigName = jsonConfigName;
        this.genCost = genCost;
    }

    private DataxPocTaskConfig() {}
}
