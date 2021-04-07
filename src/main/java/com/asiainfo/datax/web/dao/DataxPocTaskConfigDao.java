package com.asiainfo.datax.web.dao;

import com.asiainfo.datax.web.entity.DataxPocTaskConfig;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public abstract interface DataxPocTaskConfigDao
{
    @Select({"select task_id taskId, json_config_name jsonConfigName from dacp_datax_poc_task_config where task_id=#{taskId}"})
    public abstract List<DataxPocTaskConfig> selectByTaskId(@Param("taskId") Long paramLong);

    @Insert({"insert into dacp_datax_poc_task_config (task_id,unit_code, json_config_name,gen_cost)  VALUES(#{tc.taskId},#{tc.unitCode},#{tc.jsonConfigName},#{tc.genCost})"})
    public abstract int insertTaskConfig(@Param("tc") DataxPocTaskConfig paramDataxPocTaskConfig);

    @Delete({"delete from dacp_datax_poc_task_config  where task_id = #{taskId}"})
    public abstract int deleteTaskConfig(@Param("taskId") Long paramLong);
}
