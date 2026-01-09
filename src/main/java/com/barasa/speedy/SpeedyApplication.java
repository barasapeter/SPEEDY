package com.barasa.speedy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.barasa.speedy.common.util.MpesaEnvConfig;

@EnableConfigurationProperties(MpesaEnvConfig.class)
@SpringBootApplication
public class SpeedyApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpeedyApplication.class, args);
	}

}
