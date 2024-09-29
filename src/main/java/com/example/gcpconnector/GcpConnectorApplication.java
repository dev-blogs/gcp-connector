package com.example.gcpconnector;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class GcpConnectorApplication {

	public static void main(String[] args) throws IOException {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(GcpConnectorApplication.class).bannerMode(Banner.Mode.OFF);
		ConfigurableApplicationContext context = builder.web(WebApplicationType.NONE).registerShutdownHook(false).run(args);
		GcpConnector connector = context.getBean(GcpConnector.class);
		connector.checkGcp();
	}

}
