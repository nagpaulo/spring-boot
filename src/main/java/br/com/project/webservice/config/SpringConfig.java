package br.com.project.webservice.config;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"br.com.project.webservice.auth.services", "br.com.project.webservice.services", "br.com.project.webservice.auth.validator"})
public class SpringConfig {
	@Bean(name = "project")
	@Qualifier(value = "project")
	public ResourceBundle project() {
		return ResourceBundle.getBundle("project");
	}
}
