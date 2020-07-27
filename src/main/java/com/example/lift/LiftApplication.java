package com.example.lift;

import com.example.lift.buttonpanel.PanelConfig;
import com.example.lift.config.ApplicationConfig;
import com.example.lift.rest.ButtonPushEndpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {ButtonPushEndpoint.class, ApplicationConfig.class, PanelConfig.class})
public class LiftApplication {
	public static void main(String[] args) {
		SpringApplication.run(LiftApplication.class, args);
	}
}
