package com.asiainfo.datax.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Response {

    @ApiModelProperty(name = "status", value = "状态", required = true, example = "0:success")
    @JSONField(name = "status")
    @JsonProperty("status")
    private String status;

    @ApiModelProperty(name = "msg", value = "信息", required = true, example = "success")
    @JSONField(name = "msg")
    @JsonProperty("msg")
    private String msg;

    @ApiModelProperty(name = "data", value = "业务数据", required = true, example = "{'age':'11'}")
    @JSONField(name = "data")
    @JsonProperty("data")
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
