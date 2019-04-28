package cn.dezhi.welfare.partner.response.error;

/**
 * @author xjk
 * @date 2019/4/14 -  14:57
 **/
public class BussinessException extends RuntimeException implements CommonError{

    private CommonError commonError;

    /**
     * 接受EmBusinessError的参数
     * @param commonError
     */
    public BussinessException(CommonError commonError) {
        this.commonError = commonError;
    }

    /**
     * 传入自定义错误信息
     * @param commonError
     * @param errMsg
     */
    public BussinessException(CommonError commonError,String errMsg) {
        this.commonError = commonError;
        this.commonError.setErrorMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.commonError.setErrorMsg(errorMsg);
        return this;
    }
}
