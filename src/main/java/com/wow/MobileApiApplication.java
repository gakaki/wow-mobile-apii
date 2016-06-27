package com.wow;

/**
 * Created by zhengzhiqing on 16/6/18.
 */

import com.wow.mobileapi.interceptor.MustLoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class MobileApiApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MobileApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MobileApiApplication.class, args);
    }

    /**
     * 配置拦截器
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MustLoginInterceptor()).addPathPatterns("/v1.0/users/**");
        registry.addInterceptor(new MustLoginInterceptor()).addPathPatterns("/v1.0/orders/**");
        registry.addInterceptor(new MustLoginInterceptor()).addPathPatterns("/v1.0/attributes/**");
    }

    /**
     * spring boot 定时任务
     */
    @Scheduled(cron = "0 0 22 * * ?")
    public void reportCurrentTime() {
        // put your scheduler code here
    }

    /**
     * If you need to run some specific code once the SpringApplication has started, you can implement the ApplicationRunner or CommandLineRunner interfaces.
     *
     * @param args
     */
    public void run(String... args) {
        // Do something...
        logger.info("Mobile api is started and be ready to accept requests from app(iOS/Android).");
    }
}