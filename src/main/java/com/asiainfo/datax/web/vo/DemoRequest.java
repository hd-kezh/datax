package com.asiainfo.datax.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class DemoRequest {

    @ApiModelProperty(name = "id", value = "id", required = true, example = "0")
    @JSONField(name = "id")
    @JsonProperty("id")
    private String id;

    @ApiModelProperty(name = "status", value = "状态", required = false, example = "1")
    @JSONField(name = "status")
    @JsonProperty("status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
