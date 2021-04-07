package com.asiainfo.datax.web.dao;

import com.asiainfo.datax.web.entity.DataxPocTaskCount;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public abstract interface DataxPocTaskCountDao
{
    @Insert({"insert into dacp_datax_poc_task_count(task_id,count) values(#{taskCount.taskId},#{taskCount.count})"})
    public abstract int insert(@Param("taskCount") DataxPocTaskCount paramDataxPocTaskCount);

    @Delete({"delete from dacp_datax_poc_task_count where task_id = #{taskId}"})
    public abstract int deleteByTaskId(@Param("taskId") Long paramLong);

    @Select({"select sum(count) from dacp_datax_poc_task_count where task_id = #{taskId}"})
    public abstract Long sumCountByTaskId(@Param("taskId") Long paramLong);
}
