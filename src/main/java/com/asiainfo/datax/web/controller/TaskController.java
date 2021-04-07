package com.asiainfo.datax.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.asiainfo.datax.web.entity.DataxPocTask;
import com.asiainfo.datax.web.service.DataxPocTaskService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.xml.ws.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"datax/task/"})
public class TaskController
{
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);
    private final DataxPocTaskService dataxPocTaskService;

    public TaskController(DataxPocTaskService dataxPocTaskService)
    {
        this.dataxPocTaskService = dataxPocTaskService;
    }

    @ApiOperation(value="查询任务", notes="获取", response=Response.class)
    @ApiImplicitParams({@io.swagger.annotations.ApiImplicitParam(name="orderBy", value="排序类型", dataType="String", paramType="query", defaultValue="start_time"), @io.swagger.annotations.ApiImplicitParam(name="desc", value="排序顺序：默认倒叙，1正序", dataType="String", paramType="query"), @io.swagger.annotations.ApiImplicitParam(name="remark", value="任务标注", required=true, dataType="String", paramType="query", defaultValue="1"), @io.swagger.annotations.ApiImplicitParam(name="state", value="状态", required=true, dataType="int", paramType="query", defaultValue="-1"), @io.swagger.annotations.ApiImplicitParam(name="type", value="任务类型", required=true, dataType="int", paramType="query", defaultValue="-1"), @io.swagger.annotations.ApiImplicitParam(name="rerunCount", value="重跑次数", required=true, dataType="int", paramType="query", defaultValue="-1"), @io.swagger.annotations.ApiImplicitParam(name="id", value="任务号", dataType="long", paramType="query")})
    @ApiResponses({@io.swagger.annotations.ApiResponse(code=400, message="输入的参数无效"), @io.swagger.annotations.ApiResponse(code=404, message="找不到结果数据")})
    @GetMapping(path={"list/"})
    public String getTask(@Param("state") int state, @Param("type") int type, @Param("rerunCount") int rerunCount, @Param("id") Long id, @Param("orderBy") String orderBy, @Param("desc") String desc, @Param("remark") String remark)
    {
        List<DataxPocTask> list = this.dataxPocTaskService.getDataxPocTaskList(state, type, rerunCount, id, orderBy, desc, remark);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", list);
        return jsonObject.toJSONString();
    }

    @ApiOperation(value="新增任务", notes="获取", response=Response.class)
    @ApiResponses({@io.swagger.annotations.ApiResponse(code=400, message="输入的参数无效"), @io.swagger.annotations.ApiResponse(code=404, message="找不到结果数据")})
    @PostMapping(path={"insert/"})
    public String insertTask(@RequestBody DataxPocTask task)
    {
        log.info("新增任务入参=====task:" + task + "======");
        if ((null == task.getTaskName()) || (task.getTaskName().trim().isEmpty())) {
            return "任务名不能为空";
        }
        if (null == task.getUnitCode()) {
            return "配置不能为空";
        }
        if (null == task.getLabel()) {
            return "配置不能为空";
        }
        if (null == task.getStartType()) {
            return "启动方式不能为空";
        }
        return this.dataxPocTaskService.insertTask(task);
    }

    @ApiOperation(value="修改任务", notes="获取", response=Response.class)
    @ApiResponses({@io.swagger.annotations.ApiResponse(code=400, message="输入的参数无效"), @io.swagger.annotations.ApiResponse(code=404, message="找不到结果数据")})
    @PostMapping(path={"update/"})
    public String updateTask(@RequestBody DataxPocTask task)
    {
        if (null == task.getId()) {
            return "任务号不能为空";
        }
        return this.dataxPocTaskService.updateTask(task);
    }
}
