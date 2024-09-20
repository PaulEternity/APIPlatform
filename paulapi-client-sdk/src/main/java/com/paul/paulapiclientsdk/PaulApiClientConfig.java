package com.paul.paulapiclientsdk;

import com.paul.paulapiclientsdk.client.PaulApiClient;
import lombok.Data;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("paulapi.client")
@Data
@ComponentScan
public class PaulApiClientConfig {

	private String accessKey;

	private String secretKey;

	@Bean
	public PaulApiClient paulApiClient() {
		return new PaulApiClient(accessKey, secretKey);
	}

}
