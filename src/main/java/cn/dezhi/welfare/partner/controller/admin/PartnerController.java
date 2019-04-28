package cn.dezhi.welfare.partner.controller.admin;

import cn.dezhi.welfare.partner.constant.WebConst;
import cn.dezhi.welfare.partner.controller.AbstractController;
import cn.dezhi.welfare.partner.entity.dataObject.PartnerDo;
import cn.dezhi.welfare.partner.entity.viewObjcet.PartnerVo;
import cn.dezhi.welfare.partner.response.CommonReturnType;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.response.error.EmBusinessError;
import cn.dezhi.welfare.partner.service.IMailService;
import cn.dezhi.welfare.partner.service.IPartnerService;
import cn.dezhi.welfare.partner.util.*;
import cn.dezhi.welfare.partner.util.shiro.PasswordUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author xjk
 * @date 2019/4/14 -  14:33
 **/
@RestController
@RequestMapping("/partner")
public class PartnerController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(PartnerController.class);

    @Autowired
    IMailService mailService;

    @Autowired
    private IPartnerService partnerService;

    @ApiOperation(value = "商家伙伴注册",notes = "传递参数进行商家伙伴注册")
    @PostMapping("/register")
    @Transactional(rollbackFor = BussinessException.class)
    public CommonReturnType register(@RequestParam(value = "partnerName")String partnerName,
                                        @RequestParam(value = "partnerPwd") String partnerPwd,
                                        @RequestParam(value = "registerRole")String reisterRole,
                                        @RequestParam(value = "registerType")String reigsterType,
                                        @RequestParam(value = "enterpriseName")String enterpriseName,
                                        @RequestParam(value = "businessLicenseNum")String businessLicenseNum,
                                        @RequestParam(value = "legalName")String legalName,
                                        @RequestParam(value = "legalIdentityNo")String legalIdentityNo,
                                        @RequestParam(value = "enterpriseResidence")String enterpriseResidence,
                                        @RequestParam(value = "contact")String contact,
                                        @RequestParam(value = "contactPhone")String contactPhone,
                                        @RequestParam(value = "contactEmail")String contactEmail,
                                        @RequestParam(value = "verifyCode") String verifyCode,
                                        @RequestParam(value = "depositBank")String depositBank,
                                        @RequestParam(value = "depositBankAccount")String depositBankAccount,
                                        @RequestParam(value = "businessLicensePic")String businessLicensePic,
                                        @RequestParam(value = "depositBankPermitPic")String depositBankPermitPic,
                                        @RequestParam(value = "qualificationCertificatePic")String qualificationCertificatePic) throws IOException {
        String businessLicensePath =  FileUtil.getUploadPic(businessLicensePic,"BusinessLicensePic\\");
        String depositBankPermitPath = FileUtil.getUploadPic(depositBankPermitPic,"DepositBankPermitPic\\");
        String qualificationCertificatePath = FileUtil.getUploadPic(qualificationCertificatePic,"QualificationCertificatePic\\");
        PartnerDo partnerDo = new PartnerDo();
        partnerDo.setPartnerName(partnerName);
        partnerDo.setPartnerPwd(partnerPwd);
        partnerDo.setReisterRole(MyUtil.judgeRegisterRole(reisterRole));
        partnerDo.setReigsterType(MyUtil.judgeRegisterType(reigsterType));
        partnerDo.setEnterpriseName(enterpriseName);
        partnerDo.setBusinessLicenseNum(businessLicenseNum);
        partnerDo.setLegalName(legalName);
        partnerDo.setLegalIdentityNo(legalIdentityNo);
        partnerDo.setEnterpriseResidence(enterpriseResidence);
        partnerDo.setContact(contact);
        partnerDo.setContactEmail(contactEmail);
        partnerDo.setContactPhone(contactPhone);
        partnerDo.setDepositBank(depositBank);
        partnerDo.setDepositBankAccount(depositBankAccount);
        partnerDo.setBusinessLicensePic(businessLicensePath);
        partnerDo.setDepositBankPermitPic(depositBankPermitPath);
        partnerDo.setQualificationCertificatePic(qualificationCertificatePath);

        if (this.verifyCode(contactEmail,verifyCode)) {
            partnerService.insertPartner(partnerDo);
        }
        return CommonReturnType.create(partnerDo);
    }

    @ApiOperation(value = "商家伙伴信息修改",notes = "传递参数进行商家伙伴信息修改")
    @PostMapping("/update")
    @Transactional(rollbackFor = BussinessException.class)
    public CommonReturnType update(@RequestParam(value = "partnerId")Integer partnerId,
                                     @RequestParam(value = "partnerName")String partnerName,
                                     @RequestParam(value = "partnerPwd") String partnerPwd,
                                     @RequestParam(value = "registerRole")String reisterRole,
                                     @RequestParam(value = "registerType")String reigsterType,
                                     @RequestParam(value = "enterpriseName")String enterpriseName,
                                     @RequestParam(value = "businessLicenseNum")String businessLicenseNum,
                                     @RequestParam(value = "legalName")String legalName,
                                     @RequestParam(value = "legalIdentityNo")String legalIdentityNo,
                                     @RequestParam(value = "enterpriseResidence")String enterpriseResidence,
                                     @RequestParam(value = "contact")String contact,
                                     @RequestParam(value = "contactPhone")String contactPhone,
                                     @RequestParam(value = "contactEmail")String contactEmail,
                                     @RequestParam(value = "verifyCode") String verifyCode,
                                     @RequestParam(value = "depositBank")String depositBank,
                                     @RequestParam(value = "depositBankAccount")String depositBankAccount,
                                     @RequestParam(value = "businessLicensePic")String businessLicensePic,
                                     @RequestParam(value = "depositBankPermitPic")String depositBankPermitPic,
                                     @RequestParam(value = "qualificationCertificatePic")String qualificationCertificatePic) throws IOException {
        String businessLicensePath =  FileUtil.getUploadPic(businessLicensePic,"BusinessLicensePic\\");
        String depositBankPermitPath = FileUtil.getUploadPic(depositBankPermitPic,"DepositBankPermitPic\\");
        String qualificationCertificatePath = FileUtil.getUploadPic(qualificationCertificatePic,"QualificationCertificatePic\\");
        PartnerDo partnerDo = new PartnerDo();
        partnerDo.setPartnerId(partnerId);
        partnerDo.setPartnerName(partnerName);
        partnerDo.setPartnerPwd(partnerPwd);
        partnerDo.setReisterRole(MyUtil.judgeRegisterRole(reisterRole));
        partnerDo.setReigsterType(MyUtil.judgeRegisterType(reigsterType));
        partnerDo.setEnterpriseName(enterpriseName);
        partnerDo.setBusinessLicenseNum(businessLicenseNum);
        partnerDo.setLegalName(legalName);
        partnerDo.setLegalIdentityNo(legalIdentityNo);
        partnerDo.setEnterpriseResidence(enterpriseResidence);
        partnerDo.setContact(contact);
        partnerDo.setContactEmail(contactEmail);
        partnerDo.setContactPhone(contactPhone);
        partnerDo.setDepositBank(depositBank);
        partnerDo.setDepositBankAccount(depositBankAccount);
        partnerDo.setBusinessLicensePic(businessLicensePath);
        partnerDo.setDepositBankPermitPic(depositBankPermitPath);
        partnerDo.setQualificationCertificatePic(qualificationCertificatePath);

        if (this.verifyCode(contactEmail,verifyCode)) {
            partnerService.updatePartner(partnerDo);
        }
        return CommonReturnType.create(partnerDo);
    }

    @ApiOperation(value = "发送验证码",notes = "根据收到的邮箱发送验证码")
    @PostMapping("/mail")
    public String sendMail(@RequestParam(value = "mailAddress")String to) throws MessagingException {
        if (StringUtils.isBlank(to)) {
            throw new BussinessException(EmBusinessError.PARMETER_VALIDATION_ERROR,"邮箱地址不能为空");
        }
        if (PatternKit.isEmail(to)) {
            throw new BussinessException(EmBusinessError.PARMETER_VALIDATION_ERROR,"邮件地址不正确");
        }
        String code = MailCodeUtil.getRandonString(6);
        mailService.sendHtmMail(to, code);
        //将验证码存入全局缓存中，缓存时间为60秒
        cache.set(to,code,60);
        return code;
    }

    @ApiOperation(value = "修改密码",notes = "校验新密码和旧密码在修改密码")
    @PostMapping("/password")
    public CommonReturnType updatePwd(@RequestParam("oldPwd")String oldPassword, @RequestParam("newPwd")String newPassword, HttpServletRequest request) {
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            throw new BussinessException(EmBusinessError.PARMETER_VALIDATION_ERROR,"新密码或者旧密码不能为空");
        }
        String oldPwd = PasswordUtil.encryptPassword(oldPassword);
        boolean isRight = this.getPartner(request).getPartnerPwd().equals(oldPwd);
        if (!isRight) {
            throw new BussinessException(EmBusinessError.PARMETER_VALIDATION_ERROR, "原始密码不正确");
        }
        if (newPassword.length() < 6 || newPassword.length() > 14) {
            throw new BussinessException(EmBusinessError.PARMETER_VALIDATION_ERROR, "请输入6到14位密码");
        }
        PartnerVo partnerVo = this.getPartner(request);
        PartnerDo newPartnerDo = partnerService.updatePassword(partnerVo,newPassword);
        partnerVo.setPartnerPwd(newPartnerDo.getPartnerPwd());
        HttpSession session = request.getSession();
        session.setAttribute(WebConst.LOGIN_SESSION_KEY,partnerVo);
        return CommonReturnType.create("修改成功");
    }

    /**
     * 判断验证码是否正确
     * @param email
     * @param verifyCode
     * @return
     */
    public boolean verifyCode(String email,String verifyCode) {
        String code = cache.get(email);
        if (!verifyCode.equals(code)) {
            throw new BussinessException(EmBusinessError.PARMETER_VALIDATION_ERROR,"验证码错误");
        }
        return true;
    }
}
