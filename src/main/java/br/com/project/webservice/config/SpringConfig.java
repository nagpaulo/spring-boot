package br.com.project.webservice.config;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
	@Bean(name = "application")
	@Qualifier(value = "application")
	public ResourceBundle project() {
		return ResourceBundle.getBundle("application");
	}
}
