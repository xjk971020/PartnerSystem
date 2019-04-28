package cn.dezhi.welfare.partner.util.redis;

/**
 * @author xjk
 * @date 2019/4/20 -  11:26
 **/
public class RedisKeyUtil {
    public static String getKey(String tableName,String tableColumn,String id) {
        StringBuffer sb = new StringBuffer();
        sb.append(tableName + ":");
        sb.append(tableColumn + ":");
        sb.append(id);
        return sb.toString();
    }
}
