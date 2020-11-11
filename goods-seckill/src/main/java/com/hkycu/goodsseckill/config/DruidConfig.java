package com.hkycu.goodsseckill.config;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.servlet.ServletRegistration;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/** 阿里数据源监控配置 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }

    // 配置Druid的监控
    // 1. 配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123456");

        // 默认允许所有访问的白名单
        initParams.put("allow", "");
        // 设置ip的黑名单, deny的优先级高于allow
        initParams.put("deny", "192.168.0.156");

        bean.setInitParameters(initParams);
        return bean;
    }

    // 2. 配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String, String> initParmas = new HashMap<>();
        initParmas.put("exclusions", "*.js,*.css,/druid/*");

        bean.setInitParameters(initParmas);
        bean.setUrlPatterns(Arrays.asList("/*"));

        return bean;
    }
}