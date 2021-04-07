package com.asiainfo.datax.web.controller;

import com.asiainfo.datax.web.entity.DataStashUnit;
import com.asiainfo.datax.web.service.DataStashUnitService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.xml.ws.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"datax/stashunit/"})
public class DataStashUnitController
{
    private static final Logger log = LoggerFactory.getLogger(DataStashUnitController.class);
    private final DataStashUnitService dataStashUnitService;

    public DataStashUnitController(DataStashUnitService dataStashUnitService)
    {
        this.dataStashUnitService = dataStashUnitService;
    }

    @ApiOperation(value="查询配置列表", notes="获取", response=Response.class)
    @ApiImplicitParams({@io.swagger.annotations.ApiImplicitParam(name="orderBy", value="排序类型", dataType="String", paramType="query", defaultValue="start_time"), @io.swagger.annotations.ApiImplicitParam(name="desc", value="排序顺序：默认倒叙，1正序", dataType="String", paramType="query")})
    @ApiResponses({@io.swagger.annotations.ApiResponse(code=400, message="输入的参数无效"), @io.swagger.annotations.ApiResponse(code=404, message="找不到结果数据")})
    @GetMapping(path={"list/"})
    public List<DataStashUnit> getDataStashUnit(@Param("orderBy") String orderBy, @Param("desc") String desc)
    {
        return this.dataStashUnitService.getDataStashUnitList(orderBy, desc);
    }
}
