package com.gain.handler;

import com.gain.constant.MessageConstant;
import com.gain.exception.BaseException;
import com.gain.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(0,ex.getMessage());
    }

    @ExceptionHandler
    public Result execptionHandler(SQLIntegrityConstraintViolationException e){
        String message = e.getMessage();
        if(message.contains("Duplicate entry")){
            String[] s = message.split(" ");
            return Result.error(0,s[2]+ MessageConstant.ALREADY_EXISTS);
        }else{
            return Result.error(0,MessageConstant.UNKNOWN_ERROR);
        }
    }
    @ExceptionHandler
    public Result databaseExceptionHandler(SQLException e) {
        log.error("数据库异常：{}", e.getMessage(), e);

        String message = e.getMessage();

        if (message.contains("Connection refused") ||
                message.contains("Communications link failure") ||
                message.contains("Access denied")) {
            return Result.error(0, MessageConstant.DATABASE_CONNECTION_FAILED);
        }

        return Result.error(0, "数据库操作失败：" + e.getMessage());
    }
    @ExceptionHandler
    public Result myBatisExceptionHandler(MyBatisSystemException e) {
        log.error("MyBatis 系统异常：{}", e.getMessage(), e);

        Throwable cause = e.getCause();
        if (cause != null && cause.getMessage() != null) {
            String message = cause.getMessage();
            if (message.contains("Connection refused") ||
                    message.contains("Communications link failure")) {
                return Result.error(0, MessageConstant.DATABASE_CONNECTION_FAILED);
            }
        }

        return Result.error(0, "系统繁忙，请稍后再试");
    }

}
