package com.asiainfo.datax.web.dao;

import com.asiainfo.datax.web.entity.DataStashUnit;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public abstract interface DataStashUnitDao
{
    @Select({"<script>select unit_code as unitCode,label from dacp_datastash_unit where unit_type=2 order by ${orderType} <if test='desc!= null'> desc</if> </script>"})
    public abstract List<DataStashUnit> listDataStashUnit(@Param("orderType") String paramString1, @Param("desc") String paramString2);
}
