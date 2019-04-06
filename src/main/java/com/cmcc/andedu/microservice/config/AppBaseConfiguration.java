package com.cmcc.andedu.microservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import com.cmcc.andedu.microservice.config.beans.WebConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * Created by LY on 2015/11/18.
 */
public class AppBaseConfiguration {

    @LoadBalanced
    @Bean
    RestTemplate loadBalanced() {
        return new RestTemplate();
    }

    @Primary
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public WebConfig webConfig(){
        return new WebConfig();
    }
}
