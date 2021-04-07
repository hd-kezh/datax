package com.asiainfo.datax.web.dao;

import com.asiainfo.datax.web.entity.DataxPocTask;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component
public abstract interface DataxPocTaskDao
{
    @Select({"<script> select id as id,task_name as taskName,state,start_time as startTime,end_time as endTime,error_info as errorInfo,create_time as createTime,deal_num as dealNum,create_time as createTime,rerun_count as rerunCount,type,unit_code as unitCode,label,remark,dealing_num as dealingNum,deal_rate as dealRate,start_type as startType,delay_time as delayTime,cycle,rsv_str1,rsv_str2,rsv_str3,rsv_str4,rsv_str5 from dacp_datax_poc_task where remark = #{remark} <if test='state!=-1'> and state = #{state}</if> <if test='type!=-1'> and type = #{type}</if> <if test='rerunCount!=-1 and rerunCount == 0'> and rerun_count = #{rerunCount}</if> <if test='rerunCount!=-1 and rerunCount > 0'> and rerun_count > 0 </if> <if test='id!=-1 and id !=null'> and id = #{id}</if> order by ${orderType} <if test='desc!= null'> desc</if> </script>"})
    public abstract List<DataxPocTask> listTask(@Param("state") int paramInt1, @Param("type") int paramInt2, @Param("rerunCount") int paramInt3, @Param("id") Long paramLong, @Param("orderType") String paramString1, @Param("desc") String paramString2, @Param("remark") String paramString3);

    @Insert({"insert into dacp_datax_poc_task (task_name,state,start_time,end_time,error_info,deal_num,create_time,rerun_count,type,unit_code,label,remark,dealing_num,deal_rate,start_type,delay_time,cycle,rsv_str1,rsv_str2,rsv_str3,rsv_str4,rsv_str5) values (#{task.taskName},#{task.state},#{task.startTime},#{task.endTime},#{task.errorInfo},#{task.dealNum},#{task.createTime},#{task.rerunCount},#{task.type},#{task.unitCode},#{task.label},#{task.remark},#{task.dealingNum},#{task.dealRate},#{task.startType},#{task.delayTime},#{task.cycle},'1',#{task.rsvStr2},#{task.rsvStr3},#{task.rsvStr4},#{task.rsvStr5})"})
    @Options(useGeneratedKeys=true, keyProperty="task.id", keyColumn="id")
    public abstract boolean insertTask(@Param("task") DataxPocTask paramDataxPocTask);

    @Update({"<script>update dacp_datax_poc_task set <trim  suffixOverrides=','><if test='task.taskName!=null'>task_name = #{task.taskName},</if> <if test='task.state!=null'>state = #{task.state},</if> <if test='task.startTime!=null'>start_time = #{task.startTime},</if><if test='task.endTime!=null'>end_time = #{task.endTime},</if><if test='task.errorInfo!=null'>error_info = #{task.errorInfo},</if><if test='task.dealNum!=null'>deal_num = #{task.dealNum},</if><if test='task.rerunCount!=null'>rerun_count = #{task.rerunCount},</if><if test='task.type!=null'>type= #{task.type},</if><if test='task.unitCode!=null'>unit_code= #{task.unitCode},</if><if test='task.label!=null'>label= #{task.label},</if><if test='task.remark!=null'>remark=#{task.remark},</if><if test='task.dealingNum!=null'>dealing_num=#{task.dealingNum},</if><if test='task.dealRate!=null'>deal_rate=#{task.dealRate},</if><if test='task.startType!=null'>start_type=#{task.startType},</if><if test='task.delayTime!=null'>delay_time=#{task.delayTime},</if><if test='task.cycle!=null'>cycle=#{task.cycle},</if><if test='task.rsvStr1!=null'>rsv_str1=#{task.rsvStr1},</if><if test='task.rsvStr2!=null'>rsv_str2=#{task.rsvStr2},</if><if test='task.rsvStr3!=null'>rsv_str3=#{task.rsvStr3},</if><if test='task.rsvStr4!=null'>rsv_str4=#{task.rsvStr4},</if><if test='task.rsvStr5!=null'>rsv_str5=#{task.rsvStr5},</if></trim>where id = #{task.id}</script>"})
    public abstract boolean updateTaskById(@Param("task") DataxPocTask paramDataxPocTask);

    @Update({"<script>update dacp_datax_poc_task set <trim  suffixOverrides=','><if test='task.taskName!=null'>task_name = #{task.taskName},</if> <if test='task.state!=null'>state = #{task.state},</if> <if test='task.startTime!=null'>start_time = #{task.startTime},</if>end_time = #{task.endTime},error_info = #{task.errorInfo},<if test='task.dealNum!=null'>deal_num = #{task.dealNum},</if><if test='task.rerunCount!=null'>rerun_count = #{task.rerunCount},</if><if test='task.type!=null'>type= #{task.type},</if><if test='task.unitCode!=null'>unit_code= #{task.unitCode},</if><if test='task.label!=null'>label= #{task.label},</if><if test='task.remark!=null'>remark=#{task.remark},</if><if test='task.dealingNum!=null'>dealing_num=#{task.dealingNum},</if><if test='task.dealRate!=null'>deal_rate=#{task.dealRate},</if><if test='task.startType!=null'>start_type=#{task.startType},</if><if test='task.delayTime!=null'>delay_time=#{task.delayTime},</if><if test='task.cycle!=null'>cycle=#{task.cycle},</if><if test='task.rsvStr1!=null'>rsv_str1=#{task.rsvStr1},</if><if test='task.rsvStr2!=null'>rsv_str2=#{task.rsvStr2},</if><if test='task.rsvStr3!=null'>rsv_str3=#{task.rsvStr3},</if><if test='task.rsvStr4!=null'>rsv_str4=#{task.rsvStr4},</if><if test='task.rsvStr5!=null'>rsv_str5=#{task.rsvStr5},</if></trim>where id = #{task.id}</script>"})
    public abstract boolean updateTaskAndEndTimeErrorInfoById(@Param("task") DataxPocTask paramDataxPocTask);

    @Update({"update dacp_datax_poc_task set state = #{state},start_time = #{startTime} where id = #{id}"})
    public abstract int updateStateAndStartTimeById(@Param("id") long paramLong, @Param("state") int paramInt, @Param("startTime") Date paramDate);

    @Select({"select state,count(1) count,sum(rerun_count) rerunCount from dacp_datax_poc_task group by state"})
    public abstract List<Map<String, Object>> groupByState();

    @Select({"select id as id,task_name as taskName,state,start_time as startTime,end_time as endTime,error_info as errorInfo,create_time as createTime,deal_num as dealNum,create_time as createTime,rerun_count as rerunCount,type,unit_code as unitCode,label,remark,dealing_num as dealingNum,deal_rate as dealRate,start_type as startType,delay_time as delayTime,cycle,rsv_str1 rsvStr1,rsv_str2 rsvStr2,rsv_str3 rsvStr3,rsv_str4 rsvStr4,rsv_str5 rsvStr5 from dacp_datax_poc_task where id = #{id}"})
    public abstract DataxPocTask selectById(@Param("id") Long paramLong);

    @Select({"select timestampdiff(SECOND,now(),start_time) from dacp_datax_poc_task where id = #{id}"})
    public abstract Long selectStartTimeCurr(@Param("id") long paramLong);
}
