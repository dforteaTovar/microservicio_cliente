package com.david.micro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class AppConfig {
	
	//donde esta la ruta dentro del applicationContext
	@Value("${config.base.endpoint}")
	private String url;
	@Bean
	@LoadBalanced
	public WebClient.Builder registrarCliente() {
		
		//Servicio a donde nos vamos a conectar
		return WebClient.builder().baseUrl(url);
	}

}
