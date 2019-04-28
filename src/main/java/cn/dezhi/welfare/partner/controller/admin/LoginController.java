package cn.dezhi.welfare.partner.controller.admin;

import cn.dezhi.welfare.partner.constant.WebConst;
import cn.dezhi.welfare.partner.controller.AbstractController;
import cn.dezhi.welfare.partner.entity.dataObject.PartnerDo;
import cn.dezhi.welfare.partner.entity.viewObjcet.PartnerVo;
import cn.dezhi.welfare.partner.response.CommonReturnType;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.response.error.EmBusinessError;
import cn.dezhi.welfare.partner.service.IGoodsService;
import cn.dezhi.welfare.partner.service.IPartnerService;
import cn.dezhi.welfare.partner.util.MyUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author xjk
 * @date 2019/4/14 -  13:03
 **/
@RestController
public class LoginController extends AbstractController {

    @Autowired
    private IPartnerService partnerService;

    @Autowired
    private IGoodsService goodsService;

    @ApiOperation(value = "合作伙伴登录")
    @PostMapping("/login")
    public CommonReturnType doLogin(@RequestParam("mail")String mail, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(mail) || StringUtils.isBlank(password)) {
            throw new BussinessException(EmBusinessError.PARMETER_VALIDATION_ERROR, "登录邮箱或者密码不能为空");
        }
        PartnerDo partnerDo = partnerService.selectPartnerByPartnerName(mail);
        if (partnerDo == null) {
            throw new BussinessException(EmBusinessError.USER_NOT_EXIST);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(mail,password);
        try {
            subject.login(token);
            HttpSession session = request.getSession();
            PartnerVo partnerVo = new PartnerVo();
            partnerService.updatePartnerWithTime(partnerDo);
            MyUtil.convertPartnerDoToPartnerVo(partnerDo,partnerVo);
            session.setAttribute(WebConst.LOGIN_SESSION_KEY,partnerVo);
            MyUtil.setCookie(request,response,mail);
        } catch (IncorrectCredentialsException e) {
            throw new BussinessException(EmBusinessError.USER_CREDENTIAL_ERROR);
        } catch (ExcessiveAttemptsException e) {
            throw new BussinessException(EmBusinessError.USER_LOGIN_ERROR_OVER);
        }
        return CommonReturnType.create(this.getPartner(request).getPartnerId());
    }

    @ApiOperation(value = "注销")
    @GetMapping("/logout")
    public CommonReturnType logout(HttpSession session,HttpServletResponse response) {
        session.removeAttribute(WebConst.LOGIN_SESSION_KEY);
        Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE,"");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return CommonReturnType.create("注销成功");
    }
}
