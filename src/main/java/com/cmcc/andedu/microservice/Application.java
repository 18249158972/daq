package com.cmcc.andedu.microservice;

import com.cmcc.andedu.microservice.config.AppBaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by LY on 2015/11/18.
 */
//@EnableFeignClients
//@EnableDiscoveryClient
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.cmcc.andedu")
@Import({AppBaseConfiguration.class})
public class Application {

    static Logger logger = LoggerFactory.getLogger(Application.class);

    @Bean
    protected ServletContextListener listener() {
        return new ServletContextListener() {
            public void contextInitialized(ServletContextEvent sce) {
                logger.info("ServletContext initialized");
            }
            public void contextDestroyed(ServletContextEvent sce) {
                logger.info("ServletContext destroyed");
            }
        };
    }

    @Bean
    protected ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        //线程池所使用的缓冲队列
        poolTaskExecutor.setQueueCapacity(1000);
        //线程池维护线程的最少数量
        poolTaskExecutor.setCorePoolSize(10);
        //线程池维护线程的最大数量
        poolTaskExecutor.setMaxPoolSize(20);
        //线程池维护线程所允许的空闲时间
        poolTaskExecutor.setKeepAliveSeconds(30);
        poolTaskExecutor.initialize();
        return poolTaskExecutor;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
