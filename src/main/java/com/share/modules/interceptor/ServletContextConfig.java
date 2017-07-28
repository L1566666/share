package com.share.modules.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by lin on 2017/7/9 20:14.
 */
@Configuration
public class ServletContextConfig extends WebMvcConfigurerAdapter {

    /*
    解决在拦截器中无法注入对象问题
     */
    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/index/index1")
                .addPathPatterns("/isLogin")
                .addPathPatterns("/share/save")
                .addPathPatterns("/supportOrStep/supportOrStep")
        ;
    }

}
