package cn.dezhi.welfare.partner.util;

/**
 * @author xjk
 * @date 2019/4/20 -  11:22
 **/
public class Tools {
    /**
     * 判断字符串是否为数字或者有正确的值
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (null != str && 0 != str.trim().length() && str.matches("\\d*")) {
            return true;
        }
        return false;
    }
}
