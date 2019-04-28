package cn.dezhi.welfare.partner.controller.exception;

import cn.dezhi.welfare.partner.response.CommonReturnType;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.response.error.EmBusinessError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xjk
 * @date 2019/4/14 -  15:07
 * 定义全局异常处理
 **/
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BussinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonReturnType bussinessExceptionHandler(BussinessException ex) {
        logger.error("find bussiness exception: {}",ex.getErrMsg());
        Map<String,Object> responseData = new HashMap<>();
        responseData.put("errorCode",ex.getErrCode());
        responseData.put("errorMessage",ex.getErrMsg());
        return CommonReturnType.create(responseData,"fail");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonReturnType exceptionhandler(Exception ex) {
        ex.printStackTrace();
        logger.error("find bussiness exception: {}",ex.getMessage());
        Map<String,Object> responseData = new HashMap<>();
        responseData.put("errorCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
        responseData.put("errorMessage",EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        return CommonReturnType.create(responseData,"fail");
    }
}
