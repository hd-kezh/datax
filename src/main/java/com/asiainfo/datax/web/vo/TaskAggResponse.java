//
// Decompiled by Procyon v0.5.36
//

package com.asiainfo.datax.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskAggResponse
{
    @JsonProperty("SUCCESS_COUNT")
    private Integer successCount;
    @JsonProperty("COMPLETE_COUNT")
    private Integer completeCount;
    @JsonProperty("FAIL_COUNT")
    private Integer failCount;
    @JsonProperty("DELAY_COUNT")
    private Integer delayCount;
    @JsonProperty("RETRY_COUNT")
    private Integer retryCount;
    @JsonProperty("RUNNING_COUNT")
    private Integer runningCount;
    @JsonProperty("INIT_COUNT")
    private Integer initCount;

    TaskAggResponse(final Integer successCount, final Integer completeCount, final Integer failCount, final Integer delayCount, final Integer retryCount, final Integer runningCount, final Integer initCount) {
        this.successCount = successCount;
        this.completeCount = completeCount;
        this.failCount = failCount;
        this.delayCount = delayCount;
        this.retryCount = retryCount;
        this.runningCount = runningCount;
        this.initCount = initCount;
    }

    public static TaskAggResponseBuilder builder() {
        return new TaskAggResponseBuilder();
    }

    private TaskAggResponse() {
    }

    public Integer getSuccessCount() {
        return this.successCount;
    }

    public Integer getCompleteCount() {
        return this.completeCount;
    }

    public Integer getFailCount() {
        return this.failCount;
    }

    public Integer getDelayCount() {
        return this.delayCount;
    }

    public Integer getRetryCount() {
        return this.retryCount;
    }

    public Integer getRunningCount() {
        return this.runningCount;
    }

    public Integer getInitCount() {
        return this.initCount;
    }

    public void setSuccessCount(final Integer successCount) {
        this.successCount = successCount;
    }

    public void setCompleteCount(final Integer completeCount) {
        this.completeCount = completeCount;
    }

    public void setFailCount(final Integer failCount) {
        this.failCount = failCount;
    }

    public void setDelayCount(final Integer delayCount) {
        this.delayCount = delayCount;
    }

    public void setRetryCount(final Integer retryCount) {
        this.retryCount = retryCount;
    }

    public void setRunningCount(final Integer runningCount) {
        this.runningCount = runningCount;
    }

    public void setInitCount(final Integer initCount) {
        this.initCount = initCount;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TaskAggResponse)) {
            return false;
        }
        final TaskAggResponse other = (TaskAggResponse)o;
        if (!other.canEqual((Object)this)) {
            return false;
        }
        final Object this$successCount = this.getSuccessCount();
        final Object other$successCount = other.getSuccessCount();
        Label_0065: {
            if (this$successCount == null) {
                if (other$successCount == null) {
                    break Label_0065;
                }
            }
            else if (this$successCount.equals(other$successCount)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$completeCount = this.getCompleteCount();
        final Object other$completeCount = other.getCompleteCount();
        Label_0102: {
            if (this$completeCount == null) {
                if (other$completeCount == null) {
                    break Label_0102;
                }
            }
            else if (this$completeCount.equals(other$completeCount)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$failCount = this.getFailCount();
        final Object other$failCount = other.getFailCount();
        Label_0139: {
            if (this$failCount == null) {
                if (other$failCount == null) {
                    break Label_0139;
                }
            }
            else if (this$failCount.equals(other$failCount)) {
                break Label_0139;
            }
            return false;
        }
        final Object this$delayCount = this.getDelayCount();
        final Object other$delayCount = other.getDelayCount();
        Label_0176: {
            if (this$delayCount == null) {
                if (other$delayCount == null) {
                    break Label_0176;
                }
            }
            else if (this$delayCount.equals(other$delayCount)) {
                break Label_0176;
            }
            return false;
        }
        final Object this$retryCount = this.getRetryCount();
        final Object other$retryCount = other.getRetryCount();
        Label_0213: {
            if (this$retryCount == null) {
                if (other$retryCount == null) {
                    break Label_0213;
                }
            }
            else if (this$retryCount.equals(other$retryCount)) {
                break Label_0213;
            }
            return false;
        }
        final Object this$runningCount = this.getRunningCount();
        final Object other$runningCount = other.getRunningCount();
        Label_0250: {
            if (this$runningCount == null) {
                if (other$runningCount == null) {
                    break Label_0250;
                }
            }
            else if (this$runningCount.equals(other$runningCount)) {
                break Label_0250;
            }
            return false;
        }
        final Object this$initCount = this.getInitCount();
        final Object other$initCount = other.getInitCount();
        if (this$initCount == null) {
            if (other$initCount == null) {
                return true;
            }
        }
        else if (this$initCount.equals(other$initCount)) {
            return true;
        }
        return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TaskAggResponse;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $successCount = this.getSuccessCount();
        result = result * 59 + (($successCount == null) ? 43 : $successCount.hashCode());
        final Object $completeCount = this.getCompleteCount();
        result = result * 59 + (($completeCount == null) ? 43 : $completeCount.hashCode());
        final Object $failCount = this.getFailCount();
        result = result * 59 + (($failCount == null) ? 43 : $failCount.hashCode());
        final Object $delayCount = this.getDelayCount();
        result = result * 59 + (($delayCount == null) ? 43 : $delayCount.hashCode());
        final Object $retryCount = this.getRetryCount();
        result = result * 59 + (($retryCount == null) ? 43 : $retryCount.hashCode());
        final Object $runningCount = this.getRunningCount();
        result = result * 59 + (($runningCount == null) ? 43 : $runningCount.hashCode());
        final Object $initCount = this.getInitCount();
        result = result * 59 + (($initCount == null) ? 43 : $initCount.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "TaskAggResponse(successCount=" + this.getSuccessCount() + ", completeCount=" + this.getCompleteCount() + ", failCount=" + this.getFailCount() + ", delayCount=" + this.getDelayCount() + ", retryCount=" + this.getRetryCount() + ", runningCount=" + this.getRunningCount() + ", initCount=" + this.getInitCount() + ")";
    }

    public static class TaskAggResponseBuilder
    {
        private Integer successCount;
        private Integer completeCount;
        private Integer failCount;
        private Integer delayCount;
        private Integer retryCount;
        private Integer runningCount;
        private Integer initCount;

        TaskAggResponseBuilder() {
        }

        public TaskAggResponseBuilder successCount(final Integer successCount) {
            this.successCount = successCount;
            return this;
        }

        public TaskAggResponseBuilder completeCount(final Integer completeCount) {
            this.completeCount = completeCount;
            return this;
        }

        public TaskAggResponseBuilder failCount(final Integer failCount) {
            this.failCount = failCount;
            return this;
        }

        public TaskAggResponseBuilder delayCount(final Integer delayCount) {
            this.delayCount = delayCount;
            return this;
        }

        public TaskAggResponseBuilder retryCount(final Integer retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public TaskAggResponseBuilder runningCount(final Integer runningCount) {
            this.runningCount = runningCount;
            return this;
        }

        public TaskAggResponseBuilder initCount(final Integer initCount) {
            this.initCount = initCount;
            return this;
        }

        public TaskAggResponse build() {
            return new TaskAggResponse(this.successCount, this.completeCount, this.failCount, this.delayCount, this.retryCount, this.runningCount, this.initCount);
        }

        @Override
        public String toString() {
            return "TaskAggResponse.TaskAggResponseBuilder(successCount=" + this.successCount + ", completeCount=" + this.completeCount + ", failCount=" + this.failCount + ", delayCount=" + this.delayCount + ", retryCount=" + this.retryCount + ", runningCount=" + this.runningCount + ", initCount=" + this.initCount + ")";
        }
    }
}
