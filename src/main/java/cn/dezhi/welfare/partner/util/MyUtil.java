package cn.dezhi.welfare.partner.util;

import cn.dezhi.welfare.partner.constant.WebConst;
import cn.dezhi.welfare.partner.dao.BrandDoMapper;
import cn.dezhi.welfare.partner.dao.TypeDoMapper;
import cn.dezhi.welfare.partner.entity.dataObject.BrandDo;
import cn.dezhi.welfare.partner.entity.dataObject.GoodsDo;
import cn.dezhi.welfare.partner.entity.dataObject.PartnerDo;
import cn.dezhi.welfare.partner.entity.viewObjcet.GoodsVo;
import cn.dezhi.welfare.partner.entity.viewObjcet.PartnerVo;
import cn.dezhi.welfare.partner.service.IBrandService;
import cn.dezhi.welfare.partner.service.ITypeService;
import cn.dezhi.welfare.partner.util.shiro.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.*;

/**
 * @author xjk
 * @date 2019/4/15 -  21:23
 **/
public class MyUtil {

    private static Logger logger = LoggerFactory.getLogger(MyUtil.class);

    @Autowired
    private IBrandService brandService;

    @Autowired
    private ITypeService typeService;

    /**
     * 判断注册的角色
     *
     * @param registerRole
     * @return
     */
    public static Integer judgeRegisterRole(String registerRole) {
        if ("供货商".equals(registerRole)) {
            return 1;
        }
        if ("物流商".equals(registerRole)) {
            return 2;
        }
        if ("支付商".equals(registerRole)) {
            return 3;
        }
        if ("服务商".equals(registerRole)) {
            return 4;
        }
        return 0;
    }

    /**
     * 判断注册的类型
     *
     * @param registerType
     * @return
     */
    public static Integer judgeRegisterType(String registerType) {
        if ("企业".equals(registerType)) {
            return 0;
        }
        if ("个人".equals(registerType)) {
            return 1;
        }
        if ("组织团体".equals(registerType)) {
            return 2;
        }
        return -1;
    }

    /**
     * 获取存入sessio中的partnerVo
     *
     * @param request
     * @return
     */
    public static PartnerVo getPartnerDo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (null == session) {
            return null;
        }
        return (PartnerVo) session.getAttribute(WebConst.LOGIN_SESSION_KEY);
    }

    /**
     * 设置记住密码cookie,时长为12小时
     *
     * @param response
     * @param email
     */
    public static void setCookie(HttpServletRequest request,HttpServletResponse response, String email) {
        try {
            String val = PasswordUtil.enAes(email, WebConst.AES_SALT);
            Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, val);
            // 设置域名的cookie
//            if (null != request) {
//                String domainName = getDomainName(request);
//                if (!"localhost".equals(domainName)) {
//                    cookie.setDomain(domainName);
//                }
//            }
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 60 * 12);
            cookie.setSecure(false);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到cookie的域名
     */
    public static final String getDomainName(HttpServletRequest request) {
        String domainName = null;

        String serverName = request.getRequestURL().toString();
        if (serverName == null || serverName.equals("")) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            final int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > 3) {
                // www.xxx.com.cn
                domainName = domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // xxx.com or xxx.cn
                domainName = domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }

        if (domainName != null && domainName.indexOf(":") > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        return domainName;
    }

    /**
     * 转化商家伙伴模型
     *
     * @param partnerDo
     * @param partnerVo
     */
    public static void convertPartnerDoToPartnerVo(PartnerDo partnerDo, PartnerVo partnerVo) {
        BeanUtils.copyProperties(partnerDo, partnerVo);
        switch (partnerDo.getReisterRole()) {
            case 1:
                partnerVo.setReisterRole("供货商");
                break;
            case 2:
                partnerVo.setReisterRole("物流商");
                break;
            case 3:
                partnerVo.setReisterRole("支付商");
                break;
            case 4:
                partnerVo.setReisterRole("服务商");
                break;
            default:
                partnerVo.setReisterRole("无");
        }
        switch (partnerDo.getReigsterType()) {
            case 0:
                partnerVo.setReisterRole("企业");
                break;
            case 1:
                partnerVo.setReisterRole("个人");
                break;
            case 2:
                partnerVo.setReisterRole("组织团体");
                break;
            default:
                partnerVo.setReisterRole("无");
        }
        switch (partnerDo.getStatus()) {
            case 0:
                partnerVo.setStatus("审核中");
                break;
            case 1:
                partnerVo.setStatus("正常");
                break;
            case 2:
                partnerVo.setStatus("审核不通过");
                break;
            case 3:
                partnerVo.setStatus("锁定");
                break;
        }
    }

}