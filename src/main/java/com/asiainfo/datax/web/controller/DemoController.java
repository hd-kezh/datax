package com.asiainfo.datax.web.controller;

import com.asiainfo.datax.web.service.ResService;
import com.asiainfo.datax.web.util.IoUtil;
import com.asiainfo.datax.web.vo.DemoRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

/**
 * Created by dawson on 2018/10/19 14:29
 */

@Slf4j
@RestController
@RequestMapping("datax/demo/")
public class DemoController {

    @Autowired
    ResService resSer;

    Double total = 0.0;

    @ApiOperation(value = "获取资源", notes = "获取", response = Response.class)
    @ApiResponses(value = { @ApiResponse(code = 400, message = "输入的参数无效"),
            @ApiResponse(code = 404, message = "找不到结果数据") })
    @RequestMapping(method = RequestMethod.POST,path = "base/")
    public String baseInfo(@RequestBody DemoRequest req) {
        //参数检查
        IoUtil.checkField(req);
        return IoUtil.out("0000","success",resSer.index());
    }
}
