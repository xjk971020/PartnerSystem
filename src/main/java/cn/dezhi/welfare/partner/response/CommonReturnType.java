package cn.dezhi.welfare.partner.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xjk
 * @date 2019/4/14 -  14:39
 * 定义通用的返回数据
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonReturnType {

    /**
     * 返回数据状态: "success" "fail"
     */

    private String status;
    /**
     * 返回数据
     * 如果状态为success,则返回ui显示所需数据
     * 如果状态为fail，则返回失败信息
     */
    private Object data;

    public static CommonReturnType create(Object data) {
        return new CommonReturnType("success",data);
    }

    public static CommonReturnType create(Object data, String status) {
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setData(data);
        commonReturnType.setStatus(status);
        return commonReturnType;
    }
}
