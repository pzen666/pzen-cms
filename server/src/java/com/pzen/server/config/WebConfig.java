package com.pzen.server.config;

//import com.pzen.server.filter.DecryptRequestInterceptor;
//import com.pzen.server.filter.EncryptResponseInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Autowired
//    private EncryptResponseInterceptor encryptResponseInterceptor;
//
//    @Autowired
//    private DecryptRequestInterceptor decryptRequestInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(encryptResponseInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(decryptRequestInterceptor).addPathPatterns("/**");
//    }


}

