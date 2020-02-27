package br.com.roavila.pontocloud;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PontoCloudApp {

	public static void main(String[] args) {
		SpringApplication.run(PontoCloudApp.class, args);
	}

	@Bean
	public GroupedOpenApi userOpenApi() {
		String[] paths = { "/v1/**" };
		String[] packagedToMatch = { "br.com.roavila.pontocloud.controller" };
		return GroupedOpenApi.builder().setGroup("users").pathsToMatch(paths).packagesToScan(packagedToMatch)
				.build();
	}

}
