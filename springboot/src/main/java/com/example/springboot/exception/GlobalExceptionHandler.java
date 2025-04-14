package com.example.springboot.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.springboot.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = "com.example.springboot.controller")
public class GlobalExceptionHandler {
    private static final Log log = LogFactory.get();

    //统一异常处理@Exception.class，主要用于Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody //返回json串
    public Result error(HttpServletRequest request, Exception e) {
        log.error("异常信息",e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result customException(HttpServletRequest request, CustomException e) {
        return Result.error(e.getMsg());
    }
}
