package com.asiainfo.datax.web.service;

import com.asiainfo.datax.web.dao.ResDao;
import com.asiainfo.datax.web.vo.DemoRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ResService {

    @Autowired
    ResDao resDao;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private String dirtyfilename="";

    public HashMap index(){
        HashMap result = new HashMap();
        Map<String, String> map = System.getenv();
        result.put("username",map.get("USERNAME"));
        result.put("computername",map.get("COMPUTERNAME"));
        result.put("userdomain",map.get("USERDOMAIN"));
        DemoRequest req = new DemoRequest();
        req.setId("1");
        req.setStatus("1");
        resDao.insert(req);
        return result;
    }

    public void reportGenerate(String data,String filename){
        RandomAccessFile rf = null;
        try {
            rf = new RandomAccessFile(filename, "rw");
            rf.seek(rf.length());
            rf.writeBytes( new String(data.getBytes("utf-8"), "ISO8859_1").concat(" \r\n"));
            rf.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void generate(String data,String filename) {
        RandomAccessFile rf = null;
        try {
            rf = new RandomAccessFile(filename, "rw");
            rf.seek(rf.length());
            rf.writeBytes( new String(data.getBytes("utf-8"), "ISO8859_1").concat(" \r\n"));
            rf.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void reportErr(String data) {
        this.reportGenerate(data,"error.txt");
    }

    public void report(String data) {
        this.reportGenerate(data,"report.txt");
    }

    public void reportDirty(String data) {
        if (data.endsWith(".log")) {
            if(!dirtyfilename.equals(data)){
                dirtyfilename=data;
            }else{
                return;
            }
        }
        this.reportGenerate(data,"dirty.txt");
    }

    public boolean batchInsert(String mapper,List param){
       return this.batchInsert(mapper,param,10000);
    }

    public boolean batchInsert(String mapper, List params, int batchsize) {
            long start = System.currentTimeMillis();
            try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory()
                    .openSession(ExecutorType.BATCH, false)) {
                int len = params.size();
                Iterator it = params.iterator();
                int i =0 ;
                while (it.hasNext()){
                    i++;
                    sqlSession.insert(mapper,it.next());
                    if(i>0&&i%(batchsize-1)==0){
                        //按batchsize提交
                        System.err.println("inside commit");
                        sqlSession.commit();
                    }
                }
                System.err.println(len%batchsize);
                //未达到batchsize提交
                if(len%batchsize!=0)
                    sqlSession.commit();
                sqlSession.clearCache();
            } catch (Exception e) {
                e.printStackTrace();
                log.info("异常：{}", e.getMessage());
            }
            long end = System.currentTimeMillis();
            log.info("结束时间：{}", end - start);
            return true;
    }

    private boolean baseBatchInsert(SqlSession sqlSession,String mapper,Object param){
        sqlSession.insert(mapper,param);
        return true;
    }
}
