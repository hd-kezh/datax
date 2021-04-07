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
public class DataPocHiveService
{
    private static final Logger log = LoggerFactory.getLogger(DataPocHiveService.class);
    @Autowired
    DataStashService dataStashService;

    public void writerEsFile(String filename, String filePath, String RuserName, String Rpassword, String RjdbcUrl, String writerName, String tableName, String Wcolums, String WcolumsArray, String wdstype, String rdstype, String Rcolums, String Rtable, String Raddress, String RdbName)
            throws IOException
    {
        String fileName = filename + "Json.json";
        Path fpath = Paths.get(filePath + fileName, new String[0]);
        if (Files.exists(fpath, new LinkOption[0])) {
            Files.delete(fpath);
        }
        Files.createFile(fpath, new FileAttribute[0]);
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
        try
        {
            BufferedWriter bfw = Files.newBufferedWriter(fpath, new OpenOption[0]);
            bfw.write("{                                                             \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ");
            bfw.write("\t\"job\": {                                                                                                                                       \r\n");
            bfw.write("\t\t\"setting\": {                                                                                                                                 \r\n");
            bfw.write("\t\t\t\"speed\": {                                                                                                                                 \r\n");
            bfw.write("\t\t\t\t\"channel\": 20 ,                                                                                                                         \r\n");
            bfw.write("\t\t\t\t\"byte\": 1048576                                                                                                                        \r\n");
            bfw.write("\t\t\t}                                                                                                                                            \r\n");
            bfw.write("\t\t},                                                                                                                                             \r\n");
            bfw.write("\t\t\"content\": [                                                                                                                                 \r\n");
            bfw.write("\t\t\t{                                                                                                                                            \r\n");
            bfw.write("\t\t\t\t\"reader\":                                                                                                                               \r\n");

            bfw.write(this.dataStashService.genReader(valueSet, rdstype));
            bfw.write("\t\t\t\t,\"writer\": {                                                                                                                              \r\n");
            bfw.write("\t\t\t\t\t\"name\": \"" + writerName + "\",                                                                                                                \r\n");
            bfw.write("\t\t\t\t\t\"parameter\": {                                                                                                                         \r\n");
            bfw.write("\t\t\t\t\t\t\"defaultFS\": \"hdfs://10.125.128.107:8020\",                                                                                                      \r\n");
            bfw.write("\t\t\t\t\t\t\"fileType\": \"text\",                                                                                                          \r\n");
            bfw.write("                   \"path\": \"/\",                                                                                   \r\n");
            bfw.write("\t\t\t\t\t\t\"fileName\": \"drds2hdfs_test\",                                                                                                      \r\n");
            bfw.write("\t\t\"column\":                                                                                                                                  \r\n");
            bfw.write(WcolumsArray);
            bfw.write("\t\t\t\t\t\t,                                                                                                                                     \r\n");
            bfw.write("\t\t\t\t\t\t\"writeMode\": \"append\",                                                                                                          \r\n");
            bfw.write("                   \"fieldDelimiter\": \",\"                                                                                   \r\n");
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
