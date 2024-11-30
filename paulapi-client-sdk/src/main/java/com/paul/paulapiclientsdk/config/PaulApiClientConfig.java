package com.paul.paulapiclientsdk.config;

import com.paul.paulapiclientsdk.Service.ApiService;
import com.paul.paulapiclientsdk.Service.impl.ApiServiceImpl;
import com.paul.paulapiclientsdk.client.PaulApiClient;
import org.springframework.context.annotation.Bean;

public class PaulApiClientConfig {

    private String accessKey;

    private String secretKey;

    private String host;

    @Bean
    public PaulApiClient paulApiClient() {
        return new PaulApiClient(accessKey,secretKey);
    }

    @Bean
    public ApiService apiService(){
        ApiServiceImpl apiService = new ApiServiceImpl();
    }
}
