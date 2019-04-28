package cn.dezhi.welfare.partner.realm;

import cn.dezhi.welfare.partner.constant.WebConst;
import cn.dezhi.welfare.partner.dao.PartnerDoMapper;
import cn.dezhi.welfare.partner.entity.dataObject.PartnerDo;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.response.error.EmBusinessError;
import cn.dezhi.welfare.partner.util.shiro.ByteSourceUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * @author xjk
 * @date 2019/4/18 -  9:41
 **/
public class UserRealm  extends AuthorizingRealm {

    @Autowired
    @Lazy
    private PartnerDoMapper partnerDoMapper;

    /**
     * 权限验证相关
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 身份验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String email = String.valueOf(token.getPrincipal());
        PartnerDo partnerDo = partnerDoMapper.selectPartnerByPartnerName(email);
        Object principal = partnerDo.getContactEmail();
        Object credentials = partnerDo.getPartnerPwd();
        ByteSource credentialsSalt = ByteSourceUtils.bytes(WebConst.MD5_SALT);
        String realmName = getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);
        return info;
    }
}
