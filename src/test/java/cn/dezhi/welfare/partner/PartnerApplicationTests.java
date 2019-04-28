package cn.dezhi.welfare.partner;

import cn.dezhi.welfare.partner.entity.dataObject.PartnerDo;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.service.IPartnerService;
import cn.dezhi.welfare.partner.util.CodeMapCache;
import cn.dezhi.welfare.partner.util.MyUtil;
import cn.dezhi.welfare.partner.util.shiro.PasswordUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartnerApplicationTests {

    CodeMapCache codeMapCache = CodeMapCache.single();

    @Autowired
    IPartnerService partnerService;

    @Test
    public void contextLoads() {
        System.out.println(System.currentTimeMillis());
//        System.out.println((String) codeMapCache.get("1234567@qq.com"));
//        PartnerDo partnerDo = new PartnerDo();
//        partnerDo.setPartnerId(1);
//        partnerDo.setPartnerName("xjk111");
//        partnerDo.setPartnerPwd(PasswordUtil.encryptPassword("1234567"));
//
//
//        try {
//            System.out.println(partnerService.updatePartner(partnerDo));
//        } catch (BussinessException e) {
//            System.out.println(e.getErrMsg());
        }

//        System.out.println(partnerService.selectByPrimaryKey(2));
//    }

}
