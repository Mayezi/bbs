package com.mayezi;

import com.mayezi.common.config.SiteConfig;
import com.mayezi.interceptor.CommonInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableConfigurationProperties(SiteConfig.class)
public class BBSApplication extends WebMvcConfigurerAdapter{

    @Autowired
    private CommonInterceptor commonInterceptor;
    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(commonInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**");
    }

    public static void main(String[] args) {
		SpringApplication.run(BBSApplication.class, args);
	}
}
