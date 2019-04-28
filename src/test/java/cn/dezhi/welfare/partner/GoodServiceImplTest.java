package cn.dezhi.welfare.partner;

import cn.dezhi.welfare.partner.dao.GoodsDoMapper;
import cn.dezhi.welfare.partner.entity.dataObject.GoodsDo;
import cn.dezhi.welfare.partner.service.IGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author xjk
 * @date 2019/4/19 -  17:34
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodServiceImplTest {
    @Autowired
    private GoodsDoMapper goodsDoMapper;

    @Autowired
    private IGoodsService goodsService;

    @Test
    public void run() {
//        Map map = goodsDoMapper.getGoodsCount();
//        System.out.println(map.get("countAll"));
//        System.out.println(map.get("countNotPass"));
//        GoodsDo goodsDo = goodsDoMapper.selectByPrimaryKey(1);
//        System.out.println(goodsDo);
//        System.out.println(goodsService.getGoodsByPage(1, 10));
    }
}
