package cn.dezhi.welfare.partner;

import cn.dezhi.welfare.partner.service.IBrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xjk
 * @date 2019/4/20 -  21:42
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class BranServiceImplTest {

    @Autowired
    private IBrandService brandService;

    @Test
    public void test() {
//        System.out.println(brandService.selectBrandNameByBrandId(1));
    }
}
