package com.hkycu.goodsseckill.config;

import com.hkycu.goodsseckill.filter.LoginFilter;
import com.hkycu.goodsseckill.interceptor.AuthorityInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Bean("myFilter")
    public Filter loginFilter(){
        return new LoginFilter();
    }

    @Bean
    public FilterRegistrationBean loginFilterRegistratoin(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new DelegatingFilterProxy("myFilter"));

        registrationBean.addUrlPatterns("/**");
        registrationBean.setOrder(1);
        return registrationBean;
    }
    @Resource
    private AuthorityInterceptor authorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(authorityInterceptor);

        addInterceptor
                .addPathPatterns("/**")                 // 拦截配置
                .excludePathPatterns("/static/**");     // 排除配置：静态资源图片
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}
