package com.asiainfo.datax.web.util;

import com.alibaba.fastjson.JSONObject;

import com.asiainfo.datax.web.vo.Response;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.InputMismatchException;


public class IoUtil {

    private static Logger log = LoggerFactory.getLogger("IoUtil");

    public static String out(String status, String msg, Object data) {
        Response rsp = new Response();
        rsp.setMsg(msg);
        rsp.setStatus(status);
        rsp.setData(data);
        return JSONObject.toJSONString(rsp);
    }

    /**
     *  java反射机制判断对象所有属性是否全部为空
     * @param obj
     * @return 返回属性名称
     */
    public static boolean checkField(Object obj){
        try {
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                Boolean isRequired =false;
                //取出注解内信息
                try {
                    ApiModelProperty af = f.getAnnotation(ApiModelProperty.class);
                    isRequired = af.required();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                if (isRequired&&(f.get(obj) == null || f.get(obj) == "")) {
                    throw new InputMismatchException();
                }
            }
        }catch (Exception e){
            throw new InputMismatchException();
        }
        return true;
    }
}
