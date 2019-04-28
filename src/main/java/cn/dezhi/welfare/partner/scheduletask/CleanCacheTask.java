package cn.dezhi.welfare.partner.scheduletask;

import cn.dezhi.welfare.partner.util.CodeMapCache;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author xjk
 * @date 2019/4/14 -  17:23
 * 每天晚上十二点定时清除map中的验证码缓存
 **/
public class CleanCacheTask {

    private CodeMapCache codeMapCache = CodeMapCache.single();

    /**
     * 每天十二点定时触发
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void process() {
        if (codeMapCache.cachePool.size() > 300) {
            codeMapCache.clean();
        }
    }
}
