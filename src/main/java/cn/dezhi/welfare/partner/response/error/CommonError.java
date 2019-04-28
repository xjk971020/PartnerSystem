package cn.dezhi.welfare.partner.response.error;

/**
 * @author xjk
 * @date 2019/4/14 -  14:51
 **/
public interface CommonError {
    /**
     * 获取返回状态码
     * @return
     */
    int getErrCode();

    /**
     * 获取返回的信息
     * @return
     */
    String getErrMsg();

    /**
     * 设置错误信息
     * @param errorMsg
     * @return
     */
    CommonError setErrorMsg(String errorMsg);
}
