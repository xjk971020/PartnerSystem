package cn.dezhi.welfare.partner.entity.dataObject;

import lombok.Data;

@Data
public class OpHistoryDo {
    private Integer historyId;

    private Integer historyType;

    private String historyName;

    private Integer opTime;

    private Integer operatorId;

    private String operatorType;

    private String opDetails;

    private PartnerDo partnerDo;

}