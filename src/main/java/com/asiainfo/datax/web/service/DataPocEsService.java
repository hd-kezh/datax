package com.asiainfo.datax.web.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPocEsService
{
    private static final Logger log = LoggerFactory.getLogger(DataPocEsService.class);
    @Autowired
    DataStashService dataStashService;

    public void writerEsFile(String filename, String filePath, String RuserName, String Rpassword, String RjdbcUrl, String writerName, String tableName, String Wcolums, String Ipurl, String wdstype, String rdstype, String Rcolums, String Rtable, String Raddress, String RdbName)
            throws IOException
    {
        String fileName = filename + "Json.json";

        Map<String, String> valueSet = new HashMap();
        valueSet.put("filename", filename);
        valueSet.put("filePath", filePath);
        valueSet.put("RuserName", RuserName);
        valueSet.put("Rpassword", Rpassword);
        valueSet.put("RjdbcUrl", RjdbcUrl);
        valueSet.put("writerName", writerName);
        valueSet.put("Wcolums", Wcolums);
        valueSet.put("wdstype", wdstype);
        valueSet.put("rdstype ", rdstype);
        valueSet.put("Rcolums ", Rcolums);
        valueSet.put("Rtable", Rtable);
        valueSet.put("Raddress", Raddress);
        valueSet.put("RdbName", RdbName);


        Path fpath = Paths.get(filePath + fileName, new String[0]);
        if (Files.exists(fpath, new LinkOption[0])) {
            Files.delete(fpath);
        }
        Files.createFile(fpath, new FileAttribute[0]);
        try
        {
            BufferedWriter bfw = Files.newBufferedWriter(fpath, new OpenOption[0]);
            bfw.write("{                                                             \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ");
            bfw.write("\t\"job\": {                                                                                                                                       \r\n");
            bfw.write("\t\t\"setting\": {                                                                                                                                 \r\n");
            bfw.write("\t\t\t\"speed\": {                                                                                                                                 \r\n");
            bfw.write("\t\t\t\t\"channel\": 20                                                                                                                          \r\n");


            bfw.write("\t\t\t}                                                                                                                                            \r\n");
            bfw.write("\t\t},                                                                                                                                             \r\n");
            bfw.write("\t\t\"content\": [                                                                                                                                 \r\n");
            bfw.write("\t\t\t{                                                                                                                                            \r\n");
            bfw.write("\t\t\t\t\"reader\":                                                                                                                               \r\n");

            bfw.write(this.dataStashService.genReader(valueSet, rdstype));
            bfw.write("\t\t\t\t,\"writer\": {                                                                                                                              \r\n");
            bfw.write("\t\t\t\t\t\"name\": \"" + writerName + "\",                                                                                                                \r\n");
            bfw.write("\t\t\t\t\t\"parameter\": {                                                                                                                         \r\n");
            bfw.write("\t\t\t\t\t\t\"esClusterName\": \"kf-es \",                                                                                                      \r\n");
            bfw.write("\t\t\t\t\t\t\"esClusterIP\": \"" + Ipurl.substring(0, Ipurl.indexOf(":")) + "\",                                                                                                          \r\n");
            bfw.write("                   \"esClusterPort\": \"" + Ipurl.substring(Ipurl.indexOf(":") + 1) + "\",                                                                                   \r\n");
            bfw.write("\t\t\t\t\t\t\"esIndex\": \"" + tableName + "\",                                                                                                      \r\n");
            bfw.write("\t\t\t\t\t\t\"esType\": \"type\",                                                                                                          \r\n");
            bfw.write("                   \"attributeNameString\": \"" + Wcolums + "\",                                                                                   \r\n");
            bfw.write("\t\t\t\t\t\t\"attributeNameSplit\": \"，\",                                                                                                      \r\n");
            bfw.write("\t\t\t\t\t\t\"className\": \"com.alibaba.datax.plugin.writer.elasticsearchwriter.Cust\",                                                                                                          \r\n");
            bfw.write("\t\t\t\t\t\t\"batchSize\":\"100000\"                                                                                                               \r\n");
            bfw.write("\t\t\t\t\t}                                                                                                                                        \r\n");
            bfw.write("\t\t\t\t}                                                                                                                                          \r\n");
            bfw.write("\t\t\t}                                                                                                                                            \r\n");
            bfw.write("\t\t]                                                                                                                                              \r\n");
            bfw.write("\t}                                                                                                                                                \r\n");
            bfw.write("}                                                                                                                                                 \r\n");


            bfw.flush();
            bfw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
