package cn.dezhi.welfare.partner.util;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xjk
 * @date 2019/4/14 -  16:33
 * 邮箱验证码缓存
 **/
public class CodeMapCache {
    /**
     * 默认存储1024个缓存
     */
    private static final int DEFAULT_CACHES = 1024;

    private static final CodeMapCache cache = new CodeMapCache();

    public static CodeMapCache single() {
        return cache;
    }

    /**
     * 缓存容器
     */
    public Map<String, CacheObject> cachePool;

    public CodeMapCache() {
        this(DEFAULT_CACHES);
    }

    public CodeMapCache(int cacheCount) {
        cachePool = new ConcurrentHashMap<>(cacheCount);
    }

    /**
     * 读取一个缓存
     *
     * @param key 缓存key
     * @param <T>
     * @return
     */
    public <T> T get(String key) {
        CacheObject cacheObject = cachePool.get(key);
        if (null != cacheObject) {
            long cur = System.currentTimeMillis() / 1000;
            if (cacheObject.getExpired() < 0 || cacheObject.getExpired() > cur) {
                Object result = cacheObject.getValue();
                return (T) result;
            }
        }
        return null;
    }

    /**
     * 设置一个缓存
     * 永不过期
     * @param key   缓存key
     * @param value 缓存value
     */
    public void set(String key, Object value) {
        this.set(key, value, -1);
    }

    /**
     * 设置一个缓存并带过期时间
     *
     * @param key     缓存key
     * @param value   缓存value
     * @param expired 过期时间，单位为秒
     */
    public void set(String key, Object value, long expired) {
        expired = expired > 0 ? System.currentTimeMillis() / 1000 + expired : expired;
        CacheObject cacheObject = new CacheObject(key, value, expired);
        cachePool.put(key, cacheObject);
    }

    /**
     * 清空容器中所有已过期的验证码
     */
    public void clean() {
        Iterator<String> iterator = cachePool.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            long cur = System.currentTimeMillis() / 1000;
            if (cur > cachePool.get(key).getExpired()) {
                cachePool.remove(key);
            }
        }
    }

    static class CacheObject {
        private String key;
        private Object value;
        private long expired;

        public CacheObject(String key, Object value, long expired) {
            this.key = key;
            this.value = value;
            this.expired = expired;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public long getExpired() {
            return expired;
        }
    }
}
