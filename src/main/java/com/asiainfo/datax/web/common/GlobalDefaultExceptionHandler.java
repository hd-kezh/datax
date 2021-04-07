package com.asiainfo.datax.web.common;

import com.asiainfo.datax.web.util.IoUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.InputMismatchException;

//全局异常捕获
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    /*捕获缺少参数*/
    @ExceptionHandler(value = InputMismatchException.class)
    public void defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, InputMismatchException inputExcep) {
        String data = IoUtil.out("1", "缺少参数", null);
        try {
            OutputStream outputStream = resp.getOutputStream();//获取OutputStream输出流
            resp.setHeader("content-type", "application/json;charset=UTF-8");//通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
            byte[] dataByteArr = data.getBytes("UTF-8");//将字符转换成字节数组，指定以UTF-8编码进行转换
            outputStream.write(dataByteArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
