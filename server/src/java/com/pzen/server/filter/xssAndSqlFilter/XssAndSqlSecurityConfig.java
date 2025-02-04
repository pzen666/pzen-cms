package com.pzen.server.filter.xssAndSqlFilter;


import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pzen
 */

@Configuration
public class XssAndSqlSecurityConfig {

    // 定义过滤器配置参数的键作为常量 excludes用于配置不需要参数过滤的请求url;
    private static final String EXCLUDES = "excludes";
    // 定义过滤器配置参数的键作为常量 isIncludeRichText默认为true，主要用于设置富文本内容是否需要过滤。
    private static final String IS_INCLUDE_RICH_TEXT = "isIncludeRichText";

    @Bean(name = "customXssFilterRegistration")
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssAndSqlFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssAndSqlFilter");
        registration.setOrder(Integer.MAX_VALUE);

        // 使用更有意义的变量名：xssFilterConfig
        Map<String, String> xssFilterConfig = new HashMap<>();
        // 配置不需要参数过滤的请求url，使用常量键
        xssFilterConfig.put(EXCLUDES, "/favicon.ico,/img/*,/js/*,/css/*");
        // 设置富文本内容是否需要过滤，使用常量键
        xssFilterConfig.put(IS_INCLUDE_RICH_TEXT, "false");

        // 将配置参数设置为过滤器的初始化参数
        registration.setInitParameters(xssFilterConfig);

        return registration;
    }
}
