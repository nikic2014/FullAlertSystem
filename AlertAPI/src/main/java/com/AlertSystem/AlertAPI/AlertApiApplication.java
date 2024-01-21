package com.AlertSystem.AlertAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
public class AlertApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlertApiApplication.class, args);
	}

	@Primary
	@Bean
	public ServerProperties serverProperties() {
		ServerProperties serverProperties = new ServerProperties();
		serverProperties.setPort(8181);
		return serverProperties;
	}
	@Bean
	public HttpEncodingAutoConfiguration httpEncodingAutoConfiguration(ServerProperties serverProperties) {
		return new HttpEncodingAutoConfiguration(serverProperties);
	}
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
