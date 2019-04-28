package cn.dezhi.welfare.partner.inteceptor;

import cn.dezhi.welfare.partner.entity.viewObjcet.PartnerVo;
import cn.dezhi.welfare.partner.util.MyUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author xjk
 * @date 2019/4/18 -  12:59
 * 拦截器
 **/
@Component
public class BaseInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        PartnerVo partnerVo = MyUtil.getPartnerDo(request);
        String origin = request.getHeader("Origin");
        List<String> allowList = Arrays.asList("http://localhost:8080","http://192.168.1.173:8080");
        if(origin == null) {
            origin = request.getHeader("Referer");
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Origin", allowList.contains(origin) ? origin : "");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");

        // 处理uri
//        if (!uri.startsWith("/login") && null == partnerVo) {
//            response.sendRedirect(request.getContextPath() + "/login");
//            return false;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
