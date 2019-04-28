package cn.dezhi.welfare.partner.validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xjk
 * @date 2019/4/14 -  22:26
 **/
@Data
public class ValidationResult {
    /**
     * 校验结果是否有错
     */
    private boolean hasErrors = false;

    private Map<String,String> errorMsgResult = new HashMap<>();

    /**
     * 实现通用的通过格式化字符串信息获取错误结果的msg方法
     */
    public String getErrMsg() {
        return StringUtils.join(errorMsgResult.values().toArray(),",");
    }

}
