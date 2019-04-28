package cn.dezhi.welfare.partner.util;

import java.text.SimpleDateFormat;

/**
 * @author xjk
 * @date 2019/4/14 -  19:30
 * 处理时间的工具
 **/
public class DateKit {
    public static  String formatetime(long time,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }
}
