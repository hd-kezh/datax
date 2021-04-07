package com.asiainfo.datax.web.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public abstract interface DataStashDao
{
    @Select({"SELECT   t.step_idx,   t.step_inst ,   t.step_cfg_val  FROM   dacp_datastash_unit_step t WHERE   t.unit_code = #{unitCode}"})
    public abstract List<Map<String, String>> selectStepInfo(String paramString);

    @Select({"SELECT   ds_inst_loc ,   ds_auth ,   ds_acct ,   ds_category ,   ds_conf ,   ds_type  FROM   dacp_meta_datasource t WHERE   t.ds_name =  #{dsName}"})
    public abstract List<Map<String, String>> selectDsConfig(String paramString);

    @Select({"SELECT   t.column_name,    t.column_type,    t.ds_type  FROM   dacp_column_type_define t "})
    public abstract List<Map<String, String>> selectallcolumn();

    @Select({"SELECT   t.reader_dbtype,    t.writer_dbtype,   t.channel  FROM   dacp_job_config t "})
    public abstract List<Map<String, String>> selectJobConfig(String paramString1, String paramString2);
}
