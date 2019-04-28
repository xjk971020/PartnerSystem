package cn.dezhi.welfare.partner.response.error;

import lombok.AllArgsConstructor;

/**
 * @author xjk
 * @date 2019/4/14 -  14:53
 **/
@AllArgsConstructor
public enum EmBusinessError implements CommonError {
    PARMETER_VALIDATION_ERROR(10001,"入参不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),

    USER_NOT_EXIST(20001,"用户不存在"),
    USER_CREDENTIAL_ERROR(20002,"密码错误"),
    USER_LOGIN_ERROR_OVER(20003,"登录失败次数超限,请五分钟后重新尝试"),

    GOODS_ERROR(30001,"商品验证失败"),

    FILE_ERROR(40001,"文件不合法"),

    OPEEATION_HISTORY_ERROR(50001,"操作纪录存在问题");



    private int  errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errMsg = errorMsg;
        return this;
    }
}
