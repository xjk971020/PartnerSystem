package cn.dezhi.welfare.partner.controller;

import cn.dezhi.welfare.partner.entity.dataObject.PartnerDo;
import cn.dezhi.welfare.partner.entity.viewObjcet.PartnerVo;
import cn.dezhi.welfare.partner.util.CodeMapCache;
import cn.dezhi.welfare.partner.util.MyUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xjk
 * @date 2019/4/18 -  8:36
 **/
public abstract class AbstractController {

    /**
     *缓存
     */
    protected CodeMapCache cache = CodeMapCache.single();

    /**
     * 获取当前登陆session中的用户
     * @param request
     * @return
     */
    public PartnerVo getPartner(HttpServletRequest request) {
        return MyUtil.getPartnerDo(request);
    }

}
