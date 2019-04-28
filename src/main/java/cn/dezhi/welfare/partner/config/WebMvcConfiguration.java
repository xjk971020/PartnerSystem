package cn.dezhi.welfare.partner.config;

import cn.dezhi.welfare.partner.inteceptor.BaseInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * @author xjk
 * @date 2019/4/15 -  17:34
 **/
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Resource
    private BaseInterceptor baseInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/images/BusinessLicensePic/**").addResourceLocations("file:D:/IdeaProjects/PartnerSystem/src/main/resources/static/images/BusinessLicensePic/");
        registry.addResourceHandler("/static/images/DepositBankPermitPic/**").addResourceLocations("file:D:/IdeaProjects/PartnerSystem/src/main/resources/static/images/DepositBankPermitPic/");
        registry.addResourceHandler("/static/images/QualificationCertificatePic/**").addResourceLocations("file:D:/IdeaProjects/PartnerSystem/src/main/resources/static/images/QualificationCertificatePic/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor);
    }

}
