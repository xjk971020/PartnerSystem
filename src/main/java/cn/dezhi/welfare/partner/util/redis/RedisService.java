package cn.dezhi.welfare.partner.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xjk
 * @date 2019/4/20 -  12:07
 **/
@Component
public class RedisService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 缓存时间设置
     * @param key
     * @param expireTime
     * @param timeUnit
     */
    public void expire(String key, long expireTime, TimeUnit timeUnit) {
        redisTemplate.expire(key,expireTime,timeUnit);
    }

    /**
     * 设置key永不过期
     * @param key
     */
    public void persist(String key) {
        redisTemplate.persist(key);
    }
}
