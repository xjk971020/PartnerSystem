package cn.dezhi.welfare.partner.service;

import javax.mail.MessagingException;

/**
 * @author xjk
 * @date 2019/4/13 -  23:14
 **/
public interface IMailService {
    /**
     * 发送邮件
     * @param to 邮件收件人
     * @param verifyCode 邮件验证码
     */
    void sendHtmMail(String to,  String verifyCode) throws MessagingException;
}
