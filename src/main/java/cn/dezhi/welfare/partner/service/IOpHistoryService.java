package cn.dezhi.welfare.partner.service;

import cn.dezhi.welfare.partner.entity.dataObject.OpHistoryDo;
import cn.dezhi.welfare.partner.response.CommonReturnType;

/**
 * @author xjk
 * @date 2019/4/27 -  19:18
 **/
public interface IOpHistoryService {
    /**
     * 插入一条操作记录
     * @param opHistoryDo
     * @return
     */
    boolean insertOpHistory(OpHistoryDo opHistoryDo);

    /**
     * 获取对应商户的最近六条历史纪录
     * @param partnerId
     * @return
     */
    CommonReturnType getSixOpHistory(int partnerId);
}
