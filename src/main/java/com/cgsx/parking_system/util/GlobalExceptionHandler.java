package com.cgsx.parking_system.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     *
     */
    @ExceptionHandler(value = DefinitionException.class)
    @ResponseBody
    public Result bizExceptionHandler(DefinitionException e) {
        log.error("处理自定义异常");
        return Result.defineError(e);
    }

    /**
     * 处理NULL指针异常
     *
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Result eexceptionHandler( Exception e) {
        log.error("处理空指针异常");
        return Result.otherError(ErrorEnum.PARAM_IS_BLANK);
    }

    /**
     * 处理其他异常
     *
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandler( Exception e) {
        log.error("处理其他异常");
        return Result.otherError(ErrorEnum.INTERNAL_SERVER_ERROR);
    }
}