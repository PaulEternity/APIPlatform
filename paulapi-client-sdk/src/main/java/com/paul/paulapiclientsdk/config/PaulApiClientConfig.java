package com.paul.paulapiclientsdk.config;

import com.paul.paulapiclientsdk.Service.ApiService;
import com.paul.paulapiclientsdk.Service.impl.ApiServiceImpl;
import com.paul.paulapiclientsdk.client.PaulApiClient;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("paul.api.client")
@ComponentScan
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
        apiService.setPaulApiClient(new PaulApiClient(accessKey,secretKey));
        if(StringUtils.isNotBlank(host)){
            apiService.setGatewayHost(host);
        }
        return apiService;
    }
}
