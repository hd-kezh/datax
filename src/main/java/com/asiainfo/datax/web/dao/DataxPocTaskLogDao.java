package com.asiainfo.datax.web.dao;

import com.asiainfo.datax.web.entity.DataxPocTaskLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public abstract interface DataxPocTaskLogDao
{
    @Insert({"insert into dacp_datax_poc_task_log(task_id,log_name,create_time) values(#{dataxPocTaskLog.taskId},#{dataxPocTaskLog.logName},#{dataxPocTaskLog.createTime})"})
    public abstract int insert(@Param("dataxPocTaskLog") DataxPocTaskLog paramDataxPocTaskLog);
}
