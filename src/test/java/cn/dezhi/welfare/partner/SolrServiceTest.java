package cn.dezhi.welfare.partner;

import cn.dezhi.welfare.partner.service.impl.SolrService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xjk
 * @date 2019/4/26 -  21:08
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrServiceTest {
    @Autowired
    SolrService solrService;

    @Test
    public void test() {
        System.out.println(solrService.searchGoods("str", 1, 1));
    }

    @Test
    public void test2() {
//        String a = "1,3,4, ,2,6,7";
//        String[] str = a.split(",");
//        List<String> list = new ArrayList();
//        for (int i = 0; i < str.length; i++) {
//            list.add(str[i]);
//        }
//
//        System.out.println(list);
    }
}
