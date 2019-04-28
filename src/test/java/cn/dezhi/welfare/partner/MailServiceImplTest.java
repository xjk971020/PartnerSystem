package cn.dezhi.welfare.partner;

import cn.dezhi.welfare.partner.service.IMailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;

/**
 * @author xjk
 * @date 2019/4/13 -  23:23
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {
    @Autowired
    IMailService mailService;

    @Test
    public void test() {
//        try {
//            mailService.sendHtmMail("823840954@qq.com",  "123456");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
    }
}
