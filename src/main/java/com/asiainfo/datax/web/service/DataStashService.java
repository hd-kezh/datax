//
// Decompiled by Procyon v0.5.36
//

package com.asiainfo.datax.web.service;

import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.Iterator;
import com.asiainfo.datax.web.entity.DataxPocTaskConfig;
import com.alibaba.fastjson.JSONArray;
import java.util.function.Function;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import com.asiainfo.datax.web.util.AesCipher;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import com.asiainfo.datax.web.dao.DataxPocTaskConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import com.asiainfo.datax.web.dao.DataStashDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DataStashService
{
    private static final Logger log;
    @Autowired
    DataStashDao dataStashDao;
    @Autowired
    DataxPocTaskConfigDao dataxPocTaskConfigDao;
    @Value("${gen.jsonfilename}")
    private String filePath;
    @Value("${gen.columnFamily}")
    private String columnFamily;
    private List<Map<String, String>> columnTypes;
    private Map<String, String> columnTypeMap;
    private Map<String, String> hivecolumnTypeMap;
    private List<Map<String, String>> jobConfigs;
    private String jobchannel;

    public void dealConfigData(final String unitCode, final Long taskId) throws Exception {
        final long startTime = new Date().getTime();
        final List<Map<String, String>> datastashInfo = (List<Map<String, String>>)this.dataStashDao.selectStepInfo(unitCode);
        this.dataxPocTaskConfigDao.deleteTaskConfig(taskId);
        DataStashService.log.info("datastashInfo--->" + datastashInfo.toString());
        String fetchSql = "";
        String tabFields = "";
        String Wcolums = "";
        String WcolumsString = "";
        String Rsql = "";
        String RuserName = "";
        String Rpassword = "";
        String RjdbcUrl = "";
        String writerName = "";
        String WuserName = "";
        String Wpassword = "";
        String WjdbcUrl = "";
        String WtableName = "";
        String tableId = "";
        String writeAddress = "";
        String writedbName = "";
        int readerCount = 0;
        for (int i = 0; i < datastashInfo.size(); ++i) {
            final Map<String, String> dataStash4Read = datastashInfo.get(i);
            final String step_idx = dataStash4Read.get("step_idx");
            final String step_inst = dataStash4Read.get("step_inst");
            final String step_cfg_val = dataStash4Read.get("step_cfg_val");
            String drdsReadColumn = "\"";
            String mongoquery = "";
            String defaultFS = "";
            String fileType = "";
            String path = "";
            String fileNameForHive = "";
            String writeMode = "";
            String fieldDelimiter = "";
            final Map<String, String> map = new HashMap<String, String>();
            map.put("EPARCHY_CODE", "STRING");
            map.put("NET_TYPE_CODE", "STRING");
            map.put("SERIAL_NUMBER", "STRING");
            map.put("BILL_ID", "BIGINT");
            map.put("ACCT_ID", "BIGINT");
            map.put("USER_ID", "BIGINT");
            map.put("PARTITION_ID", "INT");
            map.put("CYCLE_ID", "INT");
            map.put("INTEGRATE_ITEM_CODE", "BIGINT");
            map.put("FEE", "BIGINT");
            map.put("BALANCE", "BIGINT");
            map.put("PRINT_FEE", "BIGINT");
            map.put("B_DISCNT", "BIGINT");
            map.put("A_DISCNT", "BIGINT");
            map.put("ADJUST_BEFORE", "BIGINT");
            map.put("ADJUST_AFTER", "BIGINT");
            map.put("LATE_FEE", "BIGINT");
            map.put("LATE_BALANCE", "BIGINT");
            map.put("LATECAL_DATE", "date");
            map.put("CANPAY_TAG", "STRING");
            map.put("PAY_TAG", "STRING");
            map.put("BILL_PAY_TAG", "STRING");
            map.put("VERSION_NO", "INT");
            map.put("UPDATE_TIME", "date");
            map.put("UPDATE_DEPART_ID", "STRING");
            map.put("UPDATE_STAFF_ID", "STRING");
            map.put("CHARGE_ID", "BIGINT");
            map.put("WRITEOFF_FEE1", "BIGINT");
            map.put("WRITEOFF_FEE2", "BIGINT");
            map.put("WRITEOFF_FEE3", "BIGINT");
            map.put("RSRV_FEE1", "BIGINT");
            map.put("RSRV_FEE2", "BIGINT");
            map.put("RSRV_FEE3", "BIGINT");
            map.put("RSRV_INFO1", "STRING");
            map.put("RSRV_INFO2", "STRING");
            map.put("DESC_OWE_TAG", "STRING");
            map.put("BACKUP_INFO", "STRING");
            map.put("ROLL_BACK_INFO", "STRING");
            map.put("CITY_CODE", "STRING");
            map.put("PROVINCE_CODE", "STRING");
            map.put("ARCHIVE_TIME", "date");
            if (this.isReader(step_inst)) {
                ++readerCount;
                final JSONObject cfgvalJson = JSONObject.parseObject(step_cfg_val);
                fetchSql = cfgvalJson.getString("fetchSql");
                DataStashService.log.debug("fetchSql" + fetchSql);
                tableId = cfgvalJson.getString("tabId");
                final Map<String, String> readerdsConfig = this.dataStashDao.selectDsConfig(tableId).get(0);
                final String readerDsType = readerdsConfig.get("ds_type");
                DataStashService.log.debug("dsConfig.get(\"ds_auth\")" + readerdsConfig.get("ds_auth"));
                final JSONObject readerDsconfDtl = JSONObject.parseObject((String)readerdsConfig.get("ds_conf"));
                Rpassword = AesCipher.decrypt((String)readerdsConfig.get("ds_auth"));
                RuserName = readerdsConfig.get("ds_acct");
                String Raddress = readerDsconfDtl.getString("serverAddress");
                if (StringUtils.isNotEmpty((CharSequence)Raddress)) {
                    Raddress = Raddress.replaceAll(",", "\",\"");
                }
                final String RdbName = readerDsconfDtl.getString("physicalDbName");
                RjdbcUrl = this.genJdbcUrl(readerdsConfig.get("ds_inst_loc"), readerDsType, readerDsconfDtl.getString("physicalDbName"));
                if (this.columnTypes == null) {
                    this.columnTypes = this.dataStashDao.selectallcolumn();
                }
                for (int n = 0; n < datastashInfo.size(); ++n) {
                    final Map<String, String> datastash4write = datastashInfo.get(n);
                    final String stepInst = datastash4write.get("step_inst");
                    final String stepCfgVal4Write = datastash4write.get("step_cfg_val");
                    if (this.isWriter(stepInst)) {
                        tabFields = "";
                        Wcolums = "";
                        String WcolumsType2 = "\"";
                        drdsReadColumn = "\"";
                        final JSONObject writerstepconf = JSONObject.parseObject(stepCfgVal4Write);
                        final JSONArray jsonArray = writerstepconf.getJSONArray("insertRelFields");
                        WtableName = writerstepconf.getString("objName");
                        if (writerstepconf.containsKey((Object)"condition") && StringUtils.isNotEmpty((CharSequence)writerstepconf.getString("condition"))) {
                            fetchSql = cfgvalJson.getString("fetchSql") + " where " + writerstepconf.getString("condition");
                            mongoquery = writerstepconf.getString("condition");
                        }
                        DataStashService.log.debug("jsonArray--->" + jsonArray);
                        final Map<String, String> writerdsConfig = this.dataStashDao.selectDsConfig(writerstepconf.getString("tabId")).get(0);
                        final String writerDsType = writerdsConfig.get("ds_type");
                        writerName = this.decodeWriter(writerdsConfig.get("ds_type"));
                        final JSONObject writerDtlconf = JSONObject.parseObject((String)writerdsConfig.get("ds_conf"));
                        WjdbcUrl = this.genJdbcUrl(writerdsConfig.get("ds_inst_loc"), writerdsConfig.get("ds_type"), writerDtlconf.getString("physicalDbName"));
                        writedbName = writerDtlconf.getString("physicalDbName");
                        final String filename = readerDsType + "2" + writerdsConfig.get("ds_type") + this.getSeq();
                        final String nodeIps = writerDtlconf.getString("node");
                        String nodeIpsWithoutPort = "";
                        String portsWithoutIP = "";
                        if (StringUtils.isNotEmpty((CharSequence)nodeIps)) {
                            nodeIpsWithoutPort = Arrays.stream(nodeIps.split(",")).map((Function<? super String, ? extends String>)DataStashService::lambda$dealConfigData$0).reduce(DataStashService::lambda$dealConfigData$1).get();
                            portsWithoutIP = Arrays.stream(nodeIps.split(",")).map((Function<? super String, ? extends String>)DataStashService::lambda$dealConfigData$2).distinct().reduce(DataStashService::lambda$dealConfigData$3).get();
                        }
                        Wpassword = AesCipher.decrypt((String)writerdsConfig.get("ds_auth"));
                        WuserName = writerdsConfig.get("ds_acct");
                        final JSONArray wclomsAarray = new JSONArray();
                        final JSONArray rclomsAarray = new JSONArray();
                        for (int j = 0; j < jsonArray.size(); ++j) {
                            final JSONObject relField = jsonArray.getJSONObject(j);
                            final String name = relField.getString("name");
                            final String mappingRule = this.get(relField.getString("mappingRule"), name);
                            final String mappingType = this.get(relField.getString("mappingType"), name);
                            if (j != jsonArray.size() - 1) {
                                tabFields = tabFields + mappingRule + " AS " + name + ",";
                                Wcolums = Wcolums + name + ",";
                                WcolumsType2 = WcolumsType2 + name + "\",\"";
                                drdsReadColumn = drdsReadColumn + mappingRule + "\",\"";
                            }
                            else {
                                tabFields = tabFields + mappingRule + " AS " + name;
                                Wcolums += name;
                                WcolumsType2 = WcolumsType2 + name + "\"";
                                drdsReadColumn = drdsReadColumn + mappingRule + "\"";
                            }
                            if ("mongoDB".equals(readerDsType)) {
                                final JSONObject jsonObject = new JSONObject();
                                if (mappingRule.indexOf("FIXED(") != -1) {
                                    jsonObject.put("name", (Object)name);
                                    jsonObject.put("type", this.getColumnType(name));
                                    jsonObject.put("fixedValue", (Object)mappingRule.substring(mappingRule.indexOf("FIXED(") + 6, mappingRule.indexOf(")")));
                                }
                                if (mappingRule.indexOf("DEFAULT(") != -1) {
                                    jsonObject.put("name", (Object)name);
                                    jsonObject.put("type", this.getColumnType(name));
                                    jsonObject.put("defaultValue", (Object)mappingRule.substring(mappingRule.indexOf("DEFAULT(") + 8, mappingRule.indexOf(")")));
                                }
                                else {
                                    jsonObject.put("name", (Object)name);
                                    jsonObject.put("type", this.getColumnType(name));
                                }
                                rclomsAarray.add((Object)jsonObject);
                            }
                            if ("hbase".equals(writerdsConfig.get("ds_type"))) {
                                final JSONObject jsonObject = new JSONObject();
                                jsonObject.put("index", (Object)j);
                                jsonObject.put("name", (Object)(this.columnFamily + ":" + name));
                                jsonObject.put("type", (Object)"string");
                                wclomsAarray.add((Object)jsonObject);
                            }
                            else if ("hive".equals(writerdsConfig.get("ds_type"))) {
                                defaultFS = writerDtlconf.getString("defaultFS");
                                fileType = writerDtlconf.getString("fileType");
                                path = writerDtlconf.getString("path") + WtableName;
                                fileNameForHive = WtableName + writerDtlconf.getString("fileName");
                                writeMode = writerDtlconf.getString("writeMode");
                                fieldDelimiter = writerDtlconf.getString("fieldDelimiter");
                                final JSONObject jsonObject = new JSONObject();
                                if ("oracle".equals(readerDsType)) {
                                    jsonObject.put("name", (Object)name);
                                    if (name.toUpperCase().equals("RECV_TIME") || name.toUpperCase().equals("ARCHIVE_TIME") || name.toUpperCase().equals("UPDATE_TIME") || name.toUpperCase().equals("LATECAL_DATE") || name.toUpperCase().equals("CANCEL_TIME")) {
                                        jsonObject.put("type", (Object)((StringUtils.upperCase(mappingRule).indexOf("TO_CHAR") == -1) ? "date" : "STRING"));
                                    }
                                    else {
                                        jsonObject.put("type", this.getHiveColumnType(name.toUpperCase()));
                                    }
                                }
                                else if ("drds".equals(readerDsType)) {
                                    jsonObject.put("name", (Object)name);
                                    if (name.toUpperCase().equals("RECV_TIME") || name.toUpperCase().equals("ARCHIVE_TIME") || name.toUpperCase().equals("UPDATE_TIME") || name.toUpperCase().equals("LATECAL_DATE") || name.toUpperCase().equals("CANCEL_TIME")) {
                                        jsonObject.put("type", (Object)((StringUtils.upperCase(mappingRule).indexOf("TO_CHAR") == -1) ? "date" : "STRING"));
                                    }
                                    else {
                                        jsonObject.put("type", this.getHiveColumnType(name.toUpperCase()));
                                    }
                                }
                                else {
                                    jsonObject.put("name", (Object)name);
                                    jsonObject.put("type", this.getHiveColumnType(name.toUpperCase()));
                                }
                                wclomsAarray.add((Object)jsonObject);
                            }
                            else if ("mongoDB".equals(writerdsConfig.get("ds_type"))) {
                                final JSONObject jsonObject = new JSONObject();
                                jsonObject.put("name", (Object)name.toUpperCase());
                                jsonObject.put("type", this.getColumnType(name.toUpperCase()));
                                wclomsAarray.add((Object)jsonObject);
                                writeAddress = writerdsConfig.get("ds_inst_loc");
                            }
                        }
                        WcolumsString = wclomsAarray.toJSONString();
                        Rsql = "SELECT " + tabFields + " FROM (" + fetchSql + ") ";
                        DataStashService.log.debug("fetchSql at last is ----------------->" + Rsql);
                        String preSql = "delete from " + WtableName;
                        if (readerCount > 1) {
                            preSql = "";
                        }
                        final String Rtable = this.genTableName(fetchSql, readerDsType, cfgvalJson.getString("fetchSql"));
                        final String Rcolums = WcolumsString;
                        final Map<String, String> valueSet = new HashMap<String, String>();
                        valueSet.put("filename", filename);
                        valueSet.put("filePath", this.filePath);
                        valueSet.put("Rsql", Rsql);
                        valueSet.put("RuserName", RuserName);
                        valueSet.put("Rpassword", Rpassword);
                        valueSet.put("RjdbcUrl", RjdbcUrl);
                        valueSet.put("writerName", writerName);
                        valueSet.put("WuserName", WuserName);
                        valueSet.put("Wpassword", Wpassword);
                        valueSet.put("WjdbcUrl", WjdbcUrl);
                        valueSet.put("WtableName", WtableName);
                        valueSet.put("Wcolums", Wcolums);
                        valueSet.put("preSql", preSql);
                        valueSet.put("wdstype", writerdsConfig.get("ds_type"));
                        valueSet.put("rdstype", readerDsType);
                        valueSet.put("Rcolums", Rcolums);
                        valueSet.put("Rtable", Rtable);
                        valueSet.put("Raddress", Raddress);
                        valueSet.put("RdbName", RdbName);
                        valueSet.put("WcolumsArray", WcolumsString);
                        valueSet.put("WcolumsType2", WcolumsType2);
                        valueSet.put("writeAddress", writeAddress);
                        valueSet.put("writedbName", writedbName);
                        valueSet.put("drdsColumn", drdsReadColumn);
                        valueSet.put("portsWithoutIP", portsWithoutIP);
                        valueSet.put("nodeIpsWithoutPort", nodeIpsWithoutPort);
                        valueSet.put("rclomsAarray", rclomsAarray.toJSONString());
                        valueSet.put("mongoquery", mongoquery);
                        valueSet.put("defaultFS", defaultFS);
                        valueSet.put("fileType", fileType);
                        valueSet.put("path", path);
                        valueSet.put("fileName", fileNameForHive);
                        valueSet.put("writeMode", writeMode);
                        valueSet.put("fieldDelimiter", fieldDelimiter);
                        this.jobchannel = this.getJobchannel(readerDsType, writerDsType);
                        this.writeNomalFile(filename, this.filePath, readerDsType, writerdsConfig.get("ds_type"), valueSet);
                        final String genCost = String.valueOf(new Date().getTime() - startTime);
                        this.dataxPocTaskConfigDao.insertTaskConfig(new DataxPocTaskConfig(taskId, unitCode, this.filePath + filename + ".json", genCost));
                    }
                }
            }
        }
    }

    private String getJobchannel(final String readerDsType, final String writerDsType) {
        String channel = "20";
        if (this.jobConfigs == null || this.jobConfigs.size() == 0) {
            this.jobConfigs = this.dataStashDao.selectJobConfig(readerDsType, writerDsType);
        }
        for (final Map<String, String> configMap : this.jobConfigs) {
            if (readerDsType.equals(configMap.get("reader_dbtype")) && writerDsType.equals(configMap.get("writer_dbtype"))) {
                channel = configMap.get("channel");
            }
        }
        return channel;
    }

    private Object getColumnType(final String name) {
        if (this.columnTypes != null && this.columnTypes.size() > 0 && this.columnTypeMap == null) {
            this.columnTypeMap = new HashMap();
            for (final Map<String, String> map : this.columnTypes) {
                this.columnTypeMap.put(map.get("column_name"), map.get("column_type"));
            }
        }
        if (this.columnTypeMap != null && StringUtils.isNotEmpty((CharSequence)this.columnTypeMap.get(name))) {
            return this.columnTypeMap.get(name);
        }
        return "string";
    }

    private Object getHiveColumnType(final String name) {
        if (this.columnTypes != null && this.columnTypes.size() > 0 && this.hivecolumnTypeMap == null) {
            this.hivecolumnTypeMap = new HashMap();
            for (final Map<String, String> map : this.columnTypes) {
                if ("hive".equals(map.get("ds_type"))) {
                    this.hivecolumnTypeMap.put(map.get("column_name"), map.get("column_type"));
                }
            }
        }
        if (this.hivecolumnTypeMap != null && StringUtils.isNotEmpty((CharSequence)this.hivecolumnTypeMap.get(name))) {
            return this.hivecolumnTypeMap.get(name);
        }
        return "STRING";
    }

    private String genTableName(final String fetchSql, final String ds_type, final String Rtable) {
        switch (ds_type) {
            case "mongoDB": {
                return Rtable;
            }
            default: {
                if (fetchSql.indexOf(" where ") > 0) {
                    return fetchSql.substring(fetchSql.indexOf(" from ") + 6, fetchSql.indexOf(" where ")).trim();
                }
                return fetchSql.substring(fetchSql.indexOf(" from ") + 6).trim();
            }
        }
    }

    private String decodeWriter(final String ds_type) throws Exception {
        switch (ds_type) {
            case "hbase": {
                return "hbase11xwriter";
            }
            case "mongoDB": {
                return "mongodbwriter";
            }
            case "hive": {
                return "hdfswriter";
            }
            case "es": {
                return "elasticsearchwriter";
            }
            case "oracle": {
                return "oraclewriter";
            }
            case "rds": {
                return "mysqlwriter";
            }
            case "drds": {
                return "drdswriter";
            }
            default: {
                throw new Exception(" \u7c7b\u578b\u4e0d\u652f\u6301\u8f6c\u6362" + ds_type + "\u7684Writer");
            }
        }
    }

    private String genJdbcUrl(final String ds_inst_loc, final String ds_type, final String dsdbName) throws Exception {
        switch (ds_type) {
            case "hbase": {
                return ds_inst_loc;
            }
            case "mongoDB": {
                return ds_inst_loc;
            }
            case "hive": {
                return ds_inst_loc;
            }
            case "es": {
                return ds_inst_loc;
            }
            case "oracle": {
                return "jdbc:oracle:thin:@" + ds_inst_loc + ":" + dsdbName;
            }
            case "rds": {
                return "jdbc:mysql://" + ds_inst_loc + "/" + dsdbName + "?useUnicode=true&characterEncoding=utf8";
            }
            case "drds": {
                return "jdbc:mysql://" + ds_inst_loc + "/" + dsdbName + "?useUnicode=true&characterEncoding=utf8";
            }
            default: {
                throw new Exception(" \u7c7b\u578b\u4e0d\u652f\u6301\u8f6c\u6362" + ds_type + "\u7684JDBC url");
            }
        }
    }

    private String get(final String string, final String defaultValue) {
        return StringUtils.defaultString(string, defaultValue);
    }

    private String decodeReader(final String ds_type) throws Exception {
        switch (ds_type) {
            case "hbase": {
                return "hbase11xreader";
            }
            case "hive": {
                return "hdfsreader";
            }
            case "oracle": {
                return "oraclereader";
            }
            case "rds": {
                return "mysqlreader";
            }
            case "drds": {
                return "drdsreader";
            }
            case "mongoDB": {
                return "mongoDBreader";
            }
            default: {
                throw new Exception(" \u7c7b\u578b\u4e0d\u652f\u6301\u8f6c\u6362" + ds_type + "\u7684Reader");
            }
        }
    }

    private boolean isReader(final String step_inst) {
        return step_inst.indexOf("reader") > 0;
    }

    private String getSeq() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private boolean isWriter(final String step_inst) {
        return step_inst.indexOf("writer") > 0;
    }

    private void writeNomalFile(final String filename, final String filePath, final String readerdstype, final String writedstype, final Map<String, String> valueSet) throws IOException {
        final String fileName = filename + ".json";
        final Path fpath = Paths.get(filePath + fileName, new String[0]);
        DataStashService.log.info("-------------writeNomalFile---\u5199\u6587\u4ef6---" + fpath.toString());
        if (Files.exists(fpath, new LinkOption[0])) {
            Files.delete(fpath);
        }
        Files.createFile(fpath, (FileAttribute<?>[])new FileAttribute[0]);
        try {
            final BufferedWriter bfw = Files.newBufferedWriter(fpath, new OpenOption[0]);
            bfw.write(this.genJob(this.genReader(valueSet, readerdstype), this.genWriter(valueSet, writedstype)));
            bfw.flush();
            bfw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToHbase(final String filename, final String filePath, final String RuserName, final String Rpassword, final String writerName, final String WjdbcUrl, final String WtableName, final String Wcolums, final String WcolumsArray, final String wdstype, final String rdstype, final String Rcolums, final String Rtable, final String Raddress, final String RdbName) throws IOException {
        final Map<String, String> valueSet = new HashMap<String, String>();
        valueSet.put("filename", filename);
        valueSet.put("filePath", filePath);
        valueSet.put("RuserName", RuserName);
        valueSet.put("Rpassword", Rpassword);
        valueSet.put("writerName", writerName);
        valueSet.put("WjdbcUrl", WjdbcUrl);
        valueSet.put("WtableName", WtableName);
        valueSet.put("Wcolums", Wcolums);
        valueSet.put("WcolumsArray", WcolumsArray);
        valueSet.put("wdstype", wdstype);
        valueSet.put("rdstype ", rdstype);
        valueSet.put("Rcolums ", Rcolums);
        valueSet.put("Rtable", Rtable);
        valueSet.put("Raddress", Raddress);
        valueSet.put("RdbName", RdbName);
        final String fileName = filename + "Json.json";
        final Path fpath = Paths.get(filePath + fileName, new String[0]);
        if (Files.exists(fpath, new LinkOption[0])) {
            Files.delete(fpath);
        }
        Files.createFile(fpath, (FileAttribute<?>[])new FileAttribute[0]);
        try {
            final BufferedWriter bfw = Files.newBufferedWriter(fpath, new OpenOption[0]);
            bfw.write("{                                                             \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ");
            bfw.write("\t\"job\": {                                                                                                                                       \r\n");
            bfw.write("\t\"setting\": {                                                                                                                                       \r\n");
            bfw.write("\t\"speed\": {                                                                                                                                       \r\n");
            bfw.write("\t\t\t\t\"record\": 50000                                                                                                                           \r\n");
            bfw.write("\t\t\t}                                                                                                                                            \r\n");
            bfw.write("\t\t},                                                                                                                                             \r\n");
            bfw.write("\t\t\"content\": [                                                                                                                                 \r\n");
            bfw.write("\t\t\t{                                                                                                                                            \r\n");
            bfw.write("\t\t\t\t\"reader\":                                                                                                                               \r\n");
            bfw.write(this.genReader(valueSet, rdstype));
            bfw.write("                     ,                                                                                                                        \r\n");
            bfw.write("\t\t\t\t\"writer\": {                                                                                                                              \r\n");
            bfw.write("\t\t\t\t\t\"name\": \"" + writerName + "\",                                                                                                                \r\n");
            bfw.write("\t\t\t\t\t\"parameter\": {                                                                                                                         \r\n");
            if ("|rds|drds".indexOf(wdstype) > 0) {
                bfw.write("\t\t\t\t\t\"hbaseConfig\": {                                                                                                                         \r\n");
            }
            bfw.write("\t\t\t\t\t\"hbase.zookeeper.quorum\": \"master\",                                                                                                                \r\n");
            bfw.write("\t\t\t\t\t\"hbase.zookeeper.property.clientPort\": \"2181\"                                                                                                               \r\n");
            bfw.write("\t},                                                                                                                                                \r\n");
            bfw.write("\t\t\t\t\t\"table\": \"" + WtableName + "\",                                                                                                                \r\n");
            bfw.write("\t\t\t\t\t\"mode\": \"normal\",                                                                                                                \r\n");
            bfw.write("\t\t\t\t\t\t\"rowkeyColumn\": [                                                                                                                          \r\n");
            bfw.write("{                                                             \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ");
            bfw.write("\t\t\t\t\t\"index\": \"0\",                                                                                                                \r\n");
            bfw.write("\t\t\t\t\t\"type\": \"string\"                                                                                                                \r\n");
            bfw.write("\t}                                                                                                                                                \r\n");
            bfw.write("                        ],                                                                                                                        \r\n");
            bfw.write("\t\t\t\t\t\t\"column\":                                                                                                                           \r\n");
            bfw.write("\t\t\t\t\t\t\t" + WcolumsArray + "                                                                                                                      \r\n");
            bfw.write("\t\t\t\t\t\t,                                                                                                                                     \r\n");
            bfw.write("\t\t\t\t\"versionColumn\": {                                                                                                                              \r\n");
            bfw.write("\t\t\t\t\t\"index\": \"-1\",                                                                                                                \r\n");
            bfw.write("\t\t\t\t\t\"value\": \"123456789\"                                                                                                               \r\n");
            bfw.write("\t                      },                                                                                                                                                \r\n");
            bfw.write("\t\t\t\t\t\"encoding\": \"utf-8\"                                                                                                                \r\n");
            bfw.write("\t                 }                                                                                                                                              \r\n");
            bfw.write("\t             }                                                                                                                                              \r\n");
            bfw.write("\t         ]                                                                                                                                              \r\n");
            bfw.write("\t    }                                                                                                                                              \r\n");
            bfw.write("    }                                                                                                                                              \r\n");
            bfw.flush();
            bfw.close();
        }
        catch (Exception ex) {}
    }

    private void writeMongodb(final String filename, final String filePath, final String Rsql, final String RuserName, final String Rpassword, final String RjdbcUrl, final String writerName, final String WuserName, final String Wpassword, final String WtableName, final String WcolumsArray, final String address, final String dbName, final String wdstype, final String rdstype, final String Rcolums, final String Rtable, final String Raddress, final String RdbName) throws IOException {
        final Map<String, String> valueSet = new HashMap<String, String>();
        valueSet.put("filename", filename);
        valueSet.put("filePath", filePath);
        valueSet.put("Rsql", Rsql);
        valueSet.put("RuserName", RuserName);
        valueSet.put("Rpassword", Rpassword);
        valueSet.put("RjdbcUrl", RjdbcUrl);
        valueSet.put("writerName", writerName);
        valueSet.put("WuserName", WuserName);
        valueSet.put("Wpassword", Wpassword);
        valueSet.put("WtableName", WtableName);
        valueSet.put("WcolumsArray", WcolumsArray);
        valueSet.put("address", address);
        valueSet.put("dbName", dbName);
        valueSet.put("wdstype", wdstype);
        valueSet.put("rdstype ", rdstype);
        valueSet.put("Rcolums ", Rcolums);
        valueSet.put("Rtable", Rtable);
        valueSet.put("Raddress", Raddress);
        valueSet.put("RdbName", RdbName);
        final String fileName = filename + "Json.json";
        final Path fpath = Paths.get(filePath + fileName, new String[0]);
        if (Files.exists(fpath, new LinkOption[0])) {
            Files.delete(fpath);
        }
        Files.createFile(fpath, (FileAttribute<?>[])new FileAttribute[0]);
        try {
            final BufferedWriter bfw = Files.newBufferedWriter(fpath, new OpenOption[0]);
            bfw.write("{                                                                                                                                            \r\n");
            bfw.write("\t\"job\": {                                                                                                                                       \r\n");
            bfw.write("\t\"setting\": {                                                                                                                                       \r\n");
            bfw.write("\t\"speed\": {                                                                                                                                       \r\n");
            bfw.write("\t\t\t\t\"record\": 50000                                                                                                                           \r\n");
            bfw.write("\t\t\t}                                                                                                                                            \r\n");
            bfw.write("\t\t},                                                                                                                                             \r\n");
            bfw.write("\t\t\"content\": [                                                                                                                                 \r\n");
            bfw.write("\t\t\t{                                                                                                                                            \r\n");
            bfw.write("\t\t\t\t\"reader\":                                                                                                                               \r\n");
            bfw.write(this.genReader(valueSet, rdstype));
            bfw.write("\t                      ,                                                                                                                                                \r\n");
            bfw.write("\t\t\t\t\"writer\": {                                                                                                                              \r\n");
            bfw.write("\t\t\t\t\t\"name\": \"" + writerName + "\",                                                                                                                \r\n");
            bfw.write("\t\t\t\t\t\"parameter\": {                                                                                                                         \r\n");
            if ("|rds|drds".indexOf(wdstype) > 0) {
                bfw.write("\t\t\t\t\t\t\"address\": \"[" + address + "]\",                                                                                                      \r\n");
            }
            bfw.write("\t\t\t\t\t\t\"username\": \"" + WuserName + "\",                                                                                                          \r\n");
            bfw.write("                        \"password\": \"" + Wpassword + "\",                                                                                   \r\n");
            bfw.write("                        \"dbName\": \" " + dbName + "\",                                                                                   \r\n");
            bfw.write("                        \"collectionName\": \" " + WtableName + "\",                                                                                   \r\n");
            bfw.write("\t\t\t\t\t\t\"column\":                                                                                                                           \r\n");
            bfw.write("\t\t\t\t\t\t\t" + WcolumsArray + "                                                                                                                      \r\n");
            bfw.write("\t\t\t\t\t\t,                                                                                                                                     \r\n");
            bfw.write("\t\t\t\t\"writeMode\": {                                                                                                                              \r\n");
            bfw.write("                        \"isReplace\": \" true \",                                                                                   \r\n");
            bfw.write("                        \"replaceKey\": \" _id \"                                                                                \r\n");
            bfw.write("\t\t\t\t\t    }                                                                                                                                        \r\n");
            bfw.write("\t\t\t\t\t}                                                                                                                                        \r\n");
            bfw.write("\t\t\t\t}                                                                                                                                        \r\n");
            bfw.write("\t\t\t }                                                                                                                                        \r\n");
            bfw.write("\t\t]                                                                                                                                        \r\n");
            bfw.write("\t}                                                                                                                                        \r\n");
            bfw.write("}                                                                                                                                        \r\n");
            bfw.flush();
            bfw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String genReader(final Map<String, String> valueSet, final String dsType) throws IOException {
        String srcString = this.getReaderString(dsType);
        DataStashService.log.info("Reader \u66ff\u6362\u524d-----------" + srcString);
        for (final String key : valueSet.keySet()) {
            if (valueSet.get(key) != null) {
                srcString = srcString.replace("$" + key + "$", valueSet.get(key));
            }
        }
        DataStashService.log.info("Reader \u66ff\u6362\u540e-----------" + srcString);
        return srcString;
    }

    private String genWriter(final Map<String, String> valueSet, final String dsType) throws IOException {
        String srcString = this.getWriterString(dsType);
        DataStashService.log.debug("Writer \u66ff\u6362\u524d-----------" + srcString);
        for (final String key : valueSet.keySet()) {
            if (valueSet.get(key) != null) {
                srcString = srcString.replace("$" + key + "$", valueSet.get(key));
            }
        }
        DataStashService.log.debug("Writer \u66ff\u6362\u540e-----------" + srcString);
        return srcString;
    }

    private String genJob(final String reader, final String writer) throws IOException {
        final Map<String, String> rwcontent = new HashMap<String, String>();
        rwcontent.put("readercontent", reader);
        rwcontent.put("writercontent", writer);
        rwcontent.put("jobchannel", this.jobchannel);
        String srcString = this.getJobString();
        DataStashService.log.info("JOB \u66ff\u6362\u524d-----------" + srcString);
        for (final String key : rwcontent.keySet()) {
            srcString = srcString.replace("$" + key + "$", rwcontent.get(key));
        }
        DataStashService.log.info("JOB \u66ff\u6362\u540e-----------" + srcString);
        return srcString;
    }

    private String getJobString() throws IOException {
        final InputStream inputStream = this.getClass().getResourceAsStream("/rwtemplete/job.json");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        final StringBuilder sb = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            sb.append(s + "\r\n");
        }
        reader.close();
        return sb.toString();
    }

    private String getWriterString(final String dsType) throws IOException {
        final InputStream inputStream = this.getClass().getResourceAsStream("/rwtemplete/writer/" + dsType + "writer.json");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        final StringBuilder sb = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            sb.append(s + "\r\n");
        }
        reader.close();
        return sb.toString();
    }

    private String getReaderString(final String dsType) throws IOException {
        final InputStream inputStream = this.getClass().getResourceAsStream("/rwtemplete/reader/" + dsType + "reader.json");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        final StringBuilder sb = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            sb.append(s + "\r\n");
        }
        reader.close();
        return sb.toString();
    }

    static {
        DataStashService.log = LoggerFactory.getLogger((Class)DataStashService.class);
    }
}
