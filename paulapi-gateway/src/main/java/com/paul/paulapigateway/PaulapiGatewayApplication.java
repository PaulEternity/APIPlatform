package com.paul.paulapigateway;

import com.paul.project.provider.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;

@SpringBootApplication
@EnableDubbo

public class PaulapiGatewayApplication {

	@DubboReference
	private DemoService demoService;

	public static void main(String[] args) {
		SpringApplication.run(PaulapiGatewayApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(PaulapiGatewayApplication.class, args);
		PaulapiGatewayApplication application = context.getBean(PaulapiGatewayApplication.class);
		String result = application.doSayHello("world");
		String result2 = application.doSayHello("world");
		System.out.println(result);
		System.out.println(result2);
	}
	public String doSayHello(String word) {
		return demoService.sayHello(word);
	}
//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route("path_route", r -> r.path("/get")
//						.uri("http://httpbin.org"))
//				.route("host_route", r -> r.host("*.myhost.org")
//						.uri("http://httpbin.org"))
//
//				.build();
//	}
}
