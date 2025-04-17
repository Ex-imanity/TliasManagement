package org.example.tliaswebmanagement.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.tliaswebmanagement.pojo.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result handlerException(Exception e) {
        log.error("程序出错：{}", e.getMessage());
//        e.printStackTrace();
        return Result.error("程序出错，请联系管理员");
    }

    @ExceptionHandler
    public Result handlerDuplicateException(DuplicateKeyException e) {
        log.error("数据重复：{}", e.getMessage());
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        String errMessage = message.substring(i);
        String[] arr = errMessage.split(" ");
        return Result.error(arr[2] + "已存在");
    }

    @ExceptionHandler
    public Result handlerUnsafeDeletionException(UnsafeDeletionException e) {
        log.error("删除失败：{}", e.getMessage());
        return Result.error(e.getMessage());
    }
}
