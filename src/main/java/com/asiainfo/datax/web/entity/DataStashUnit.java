package com.asiainfo.datax.web.entity;

public class DataStashUnit
{
    private String unitCode;
    private String label;

    public String toString()
    {
        return "DataStashUnit(unitCode=" + getUnitCode() + ", label=" + getLabel() + ")";
    }

    public void setUnitCode(String unitCode)
    {
        this.unitCode = unitCode;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DataStashUnit)) {
            return false;
        }
        DataStashUnit other = (DataStashUnit)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$unitCode = getUnitCode();Object other$unitCode = other.getUnitCode();
        if (this$unitCode == null ? other$unitCode != null : !this$unitCode.equals(other$unitCode)) {
            return false;
        }
        Object this$label = getLabel();Object other$label = other.getLabel();return this$label == null ? other$label == null : this$label.equals(other$label);
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof DataStashUnit;
    }

    public int hashCode()
    {
        int PRIME = 59;int result = 1;Object $unitCode = getUnitCode();result = result * 59 + ($unitCode == null ? 43 : $unitCode.hashCode());Object $label = getLabel();result = result * 59 + ($label == null ? 43 : $label.hashCode());return result;
    }

    public String getUnitCode()
    {
        return this.unitCode;
    }

    public String getLabel()
    {
        return this.label;
    }
}
