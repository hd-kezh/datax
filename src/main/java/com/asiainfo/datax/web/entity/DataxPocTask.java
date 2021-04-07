package com.asiainfo.datax.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;

public class DataxPocTask
        implements Serializable
{
    private static final long serialVersionUID = 4540553283479824228L;
    private Long id;
    @Column(name="task_name")
    private String taskName;
    @Column(name="state")
    private Integer state;
    @Column(name="start_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;
    @Column(name="end_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;
    @Column(name="error_info")
    private String errorInfo;
    @Column(name="deal_num")
    private Integer dealNum;
    @Column(name="create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;
    @Column(name="rerun_count")
    private Integer rerunCount;
    @Column(name="type")
    private Integer type;
    @Column(name="unit_code")
    private String unitCode;
    @Column(name="label")
    private String label;
    @Column(name="remark")
    private String remark;
    @Column(name="dealing_num")
    private String dealingNum;
    @Column(name="deal_rate")
    private String dealRate;
    @Column(name="start_type")
    private String startType;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Column(name="delay_time")
    private Date delayTime;
    @Column(name="cycle")
    private Long cycle;
    @Column(name="rsv_str1")
    private String rsvStr1;
    @Column(name="rsv_str2")
    private String rsvStr2;
    @Column(name="rsv_str3")
    private String rsvStr3;
    @Column(name="rsv_str4")
    private String rsvStr4;
    @Column(name="rsv_str5")
    private String rsvStr5;

    public void setState(Integer state)
    {
        this.state = state;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $id = getId();result = result * 59 + ($id == null ? 43 : $id.hashCode());Object $taskName = getTaskName();result = result * 59 + ($taskName == null ? 43 : $taskName.hashCode());Object $state = getState();result = result * 59 + ($state == null ? 43 : $state.hashCode());Object $startTime = getStartTime();result = result * 59 + ($startTime == null ? 43 : $startTime.hashCode());Object $endTime = getEndTime();result = result * 59 + ($endTime == null ? 43 : $endTime.hashCode());Object $errorInfo = getErrorInfo();result = result * 59 + ($errorInfo == null ? 43 : $errorInfo.hashCode());Object $dealNum = getDealNum();result = result * 59 + ($dealNum == null ? 43 : $dealNum.hashCode());Object $createTime = getCreateTime();result = result * 59 + ($createTime == null ? 43 : $createTime.hashCode());Object $rerunCount = getRerunCount();result = result * 59 + ($rerunCount == null ? 43 : $rerunCount.hashCode());Object $type = getType();result = result * 59 + ($type == null ? 43 : $type.hashCode());Object $unitCode = getUnitCode();result = result * 59 + ($unitCode == null ? 43 : $unitCode.hashCode());Object $label = getLabel();result = result * 59 + ($label == null ? 43 : $label.hashCode());Object $remark = getRemark();result = result * 59 + ($remark == null ? 43 : $remark.hashCode());Object $dealingNum = getDealingNum();result = result * 59 + ($dealingNum == null ? 43 : $dealingNum.hashCode());Object $dealRate = getDealRate();result = result * 59 + ($dealRate == null ? 43 : $dealRate.hashCode());Object $startType = getStartType();result = result * 59 + ($startType == null ? 43 : $startType.hashCode());Object $delayTime = getDelayTime();result = result * 59 + ($delayTime == null ? 43 : $delayTime.hashCode());Object $cycle = getCycle();result = result * 59 + ($cycle == null ? 43 : $cycle.hashCode());Object $rsvStr1 = getRsvStr1();result = result * 59 + ($rsvStr1 == null ? 43 : $rsvStr1.hashCode());Object $rsvStr2 = getRsvStr2();result = result * 59 + ($rsvStr2 == null ? 43 : $rsvStr2.hashCode());Object $rsvStr3 = getRsvStr3();result = result * 59 + ($rsvStr3 == null ? 43 : $rsvStr3.hashCode());Object $rsvStr4 = getRsvStr4();result = result * 59 + ($rsvStr4 == null ? 43 : $rsvStr4.hashCode());Object $rsvStr5 = getRsvStr5();result = result * 59 + ($rsvStr5 == null ? 43 : $rsvStr5.hashCode());return result;
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof DataxPocTask;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DataxPocTask)) {
            return false;
        }
        DataxPocTask other = (DataxPocTask)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$id = getId();Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        Object this$taskName = getTaskName();Object other$taskName = other.getTaskName();
        if (this$taskName == null ? other$taskName != null : !this$taskName.equals(other$taskName)) {
            return false;
        }
        Object this$state = getState();Object other$state = other.getState();
        if (this$state == null ? other$state != null : !this$state.equals(other$state)) {
            return false;
        }
        Object this$startTime = getStartTime();Object other$startTime = other.getStartTime();
        if (this$startTime == null ? other$startTime != null : !this$startTime.equals(other$startTime)) {
            return false;
        }
        Object this$endTime = getEndTime();Object other$endTime = other.getEndTime();
        if (this$endTime == null ? other$endTime != null : !this$endTime.equals(other$endTime)) {
            return false;
        }
        Object this$errorInfo = getErrorInfo();Object other$errorInfo = other.getErrorInfo();
        if (this$errorInfo == null ? other$errorInfo != null : !this$errorInfo.equals(other$errorInfo)) {
            return false;
        }
        Object this$dealNum = getDealNum();Object other$dealNum = other.getDealNum();
        if (this$dealNum == null ? other$dealNum != null : !this$dealNum.equals(other$dealNum)) {
            return false;
        }
        Object this$createTime = getCreateTime();Object other$createTime = other.getCreateTime();
        if (this$createTime == null ? other$createTime != null : !this$createTime.equals(other$createTime)) {
            return false;
        }
        Object this$rerunCount = getRerunCount();Object other$rerunCount = other.getRerunCount();
        if (this$rerunCount == null ? other$rerunCount != null : !this$rerunCount.equals(other$rerunCount)) {
            return false;
        }
        Object this$type = getType();Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        Object this$unitCode = getUnitCode();Object other$unitCode = other.getUnitCode();
        if (this$unitCode == null ? other$unitCode != null : !this$unitCode.equals(other$unitCode)) {
            return false;
        }
        Object this$label = getLabel();Object other$label = other.getLabel();
        if (this$label == null ? other$label != null : !this$label.equals(other$label)) {
            return false;
        }
        Object this$remark = getRemark();Object other$remark = other.getRemark();
        if (this$remark == null ? other$remark != null : !this$remark.equals(other$remark)) {
            return false;
        }
        Object this$dealingNum = getDealingNum();Object other$dealingNum = other.getDealingNum();
        if (this$dealingNum == null ? other$dealingNum != null : !this$dealingNum.equals(other$dealingNum)) {
            return false;
        }
        Object this$dealRate = getDealRate();Object other$dealRate = other.getDealRate();
        if (this$dealRate == null ? other$dealRate != null : !this$dealRate.equals(other$dealRate)) {
            return false;
        }
        Object this$startType = getStartType();Object other$startType = other.getStartType();
        if (this$startType == null ? other$startType != null : !this$startType.equals(other$startType)) {
            return false;
        }
        Object this$delayTime = getDelayTime();Object other$delayTime = other.getDelayTime();
        if (this$delayTime == null ? other$delayTime != null : !this$delayTime.equals(other$delayTime)) {
            return false;
        }
        Object this$cycle = getCycle();Object other$cycle = other.getCycle();
        if (this$cycle == null ? other$cycle != null : !this$cycle.equals(other$cycle)) {
            return false;
        }
        Object this$rsvStr1 = getRsvStr1();Object other$rsvStr1 = other.getRsvStr1();
        if (this$rsvStr1 == null ? other$rsvStr1 != null : !this$rsvStr1.equals(other$rsvStr1)) {
            return false;
        }
        Object this$rsvStr2 = getRsvStr2();Object other$rsvStr2 = other.getRsvStr2();
        if (this$rsvStr2 == null ? other$rsvStr2 != null : !this$rsvStr2.equals(other$rsvStr2)) {
            return false;
        }
        Object this$rsvStr3 = getRsvStr3();Object other$rsvStr3 = other.getRsvStr3();
        if (this$rsvStr3 == null ? other$rsvStr3 != null : !this$rsvStr3.equals(other$rsvStr3)) {
            return false;
        }
        Object this$rsvStr4 = getRsvStr4();Object other$rsvStr4 = other.getRsvStr4();
        if (this$rsvStr4 == null ? other$rsvStr4 != null : !this$rsvStr4.equals(other$rsvStr4)) {
            return false;
        }
        Object this$rsvStr5 = getRsvStr5();Object other$rsvStr5 = other.getRsvStr5();return this$rsvStr5 == null ? other$rsvStr5 == null : this$rsvStr5.equals(other$rsvStr5);
    }

    public void setRsvStr5(String rsvStr5)
    {
        this.rsvStr5 = rsvStr5;
    }

    public void setRsvStr4(String rsvStr4)
    {
        this.rsvStr4 = rsvStr4;
    }

    public void setRsvStr3(String rsvStr3)
    {
        this.rsvStr3 = rsvStr3;
    }

    public void setRsvStr2(String rsvStr2)
    {
        this.rsvStr2 = rsvStr2;
    }

    public void setRsvStr1(String rsvStr1)
    {
        this.rsvStr1 = rsvStr1;
    }

    public void setCycle(Long cycle)
    {
        this.cycle = cycle;
    }

    public void setDelayTime(Date delayTime)
    {
        this.delayTime = delayTime;
    }

    public void setStartType(String startType)
    {
        this.startType = startType;
    }

    public void setDealRate(String dealRate)
    {
        this.dealRate = dealRate;
    }

    public void setDealingNum(String dealingNum)
    {
        this.dealingNum = dealingNum;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public void setUnitCode(String unitCode)
    {
        this.unitCode = unitCode;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public void setRerunCount(Integer rerunCount)
    {
        this.rerunCount = rerunCount;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public void setDealNum(Integer dealNum)
    {
        this.dealNum = dealNum;
    }

    public void setErrorInfo(String errorInfo)
    {
        this.errorInfo = errorInfo;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String toString()
    {
        return "DataxPocTask(id=" + getId() + ", taskName=" + getTaskName() + ", state=" + getState() + ", startTime=" + getStartTime() + ", endTime=" + getEndTime() + ", errorInfo=" + getErrorInfo() + ", dealNum=" + getDealNum() + ", createTime=" + getCreateTime() + ", rerunCount=" + getRerunCount() + ", type=" + getType() + ", unitCode=" + getUnitCode() + ", label=" + getLabel() + ", remark=" + getRemark() + ", dealingNum=" + getDealingNum() + ", dealRate=" + getDealRate() + ", startType=" + getStartType() + ", delayTime=" + getDelayTime() + ", cycle=" + getCycle() + ", rsvStr1=" + getRsvStr1() + ", rsvStr2=" + getRsvStr2() + ", rsvStr3=" + getRsvStr3() + ", rsvStr4=" + getRsvStr4() + ", rsvStr5=" + getRsvStr5() + ")";
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Long getId()
    {
        return this.id;
    }

    public String getTaskName()
    {
        return this.taskName;
    }

    public Integer getState()
    {
        return this.state;
    }

    public Date getStartTime()
    {
        return this.startTime;
    }

    public Date getEndTime()
    {
        return this.endTime;
    }

    public String getErrorInfo()
    {
        return this.errorInfo;
    }

    public Integer getDealNum()
    {
        return this.dealNum;
    }

    public Date getCreateTime()
    {
        return this.createTime;
    }

    public Integer getRerunCount()
    {
        return this.rerunCount;
    }

    public Integer getType()
    {
        return this.type;
    }

    public String getUnitCode()
    {
        return this.unitCode;
    }

    public String getLabel()
    {
        return this.label;
    }

    public String getRemark()
    {
        return this.remark;
    }

    public String getDealingNum()
    {
        return this.dealingNum;
    }

    public String getDealRate()
    {
        return this.dealRate;
    }

    public String getStartType()
    {
        return this.startType;
    }

    public Date getDelayTime()
    {
        return this.delayTime;
    }

    public Long getCycle()
    {
        return this.cycle;
    }

    public String getRsvStr1()
    {
        return this.rsvStr1;
    }

    public String getRsvStr2()
    {
        return this.rsvStr2;
    }

    public String getRsvStr3()
    {
        return this.rsvStr3;
    }

    public String getRsvStr4()
    {
        return this.rsvStr4;
    }

    public String getRsvStr5()
    {
        return this.rsvStr5;
    }
}
