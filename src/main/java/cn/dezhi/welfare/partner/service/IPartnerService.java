package cn.dezhi.welfare.partner.service;

import cn.dezhi.welfare.partner.entity.dataObject.PartnerDo;
import cn.dezhi.welfare.partner.entity.viewObjcet.PartnerVo;

/**
 * @author xjk
 * @date 2019/4/14 -  15:41
 **/
public interface IPartnerService {
    /**
     * 添加一个合作伙伴
     * @Param partnerDo
     * @return
     */
    int insertPartner(PartnerDo partnerDo);

    /**
     * 通过邮箱获得合作伙伴信息
     * @param email
     * @return
     */
    PartnerDo selectPartnerByPartnerName(String email);

    /**
     * 根据id查找合作伙伴
     * @param partnerId
     * @return
     */
    PartnerDo selectByPrimaryKey(int partnerId);

    /**
     * 修改密码
     * @param partnerVo
     * @return
     */
    PartnerDo updatePassword(PartnerVo partnerVo, String newPassword);

    /**
     * 修改账户信息
     * @param partnerDo
     * @return
     */
    PartnerDo updatePartner(PartnerDo partnerDo);

    /**
     * 修改最近一次登录的时间
     * @param partnerDo
     * @return
     */
    PartnerDo updatePartnerWithTime(PartnerDo partnerDo);
}
