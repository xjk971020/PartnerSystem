package cn.dezhi.welfare.partner.service.impl;

import cn.dezhi.welfare.partner.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author xjk
 * @date 2019/4/13 -  23:14
 **/
@Service
public class MailServiceImpl implements IMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${mail.fromMail.addr}")
    private String from;

    @Override
    public void sendHtmMail(String to, String verifyCode) throws MessagingException {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("verifyCode",verifyCode);
        //将模板引擎内容解析成html字符串
        String emailContent = templateEngine.process("emailTemplate",context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper .setFrom(from);
        helper.setTo(to);
        helper.setSubject("验证码");
        helper.setText(emailContent,true);
        try {
            mailSender.send(mimeMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
