package cn.dezhi.welfare.partner;

import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xjk
 * @date 2019/4/20 -  9:47
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisService {

    @Autowired
    private ValueOperations<String,Object> valueOperations;

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test() {
//        valueOperations.set("11233","123123");
//        System.out.println(valueOperations.get("11233"));
//        redisTemplate.opsForValue().set("123","123");
//        System.out.println(redisTemplate.opsForValue().get("123"));
    }

}
