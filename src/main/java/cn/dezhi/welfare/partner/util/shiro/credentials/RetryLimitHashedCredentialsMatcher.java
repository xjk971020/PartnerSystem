package cn.dezhi.welfare.partner.util.shiro.credentials;

import cn.dezhi.welfare.partner.constant.WebConst;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.response.error.EmBusinessError;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xjk
 * @date 2019/2/15 -  15:45
 * 当登录失败次数超过五次时,锁定账户
 **/
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {
        String email = (String) token.getPrincipal();
        AtomicInteger retryCount = passwordRetryCache.get(email);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(email, retryCount);
        }
        if (retryCount.incrementAndGet() > WebConst.LOGIN_ERROR_COUNT) {
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            passwordRetryCache.remove(email);
        }
        return matches;
    }
}
