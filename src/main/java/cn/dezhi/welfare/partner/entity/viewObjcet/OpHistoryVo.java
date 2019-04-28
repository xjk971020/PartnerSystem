package cn.dezhi.welfare.partner.entity.viewObjcet;

import cn.dezhi.welfare.partner.entity.dataObject.PartnerDo;
import lombok.Data;

/**
 * @author xjk
 * @date 2019/4/27 -  19:49
 **/
@Data
public class OpHistoryVo {
    private Integer historyId;

    private String historyType;

    private String historyName;

    private Integer opTime;

    private Integer operatorId;

    private String operatorType;

    private String opDetails;

    private PartnerDo partnerDo;
}
