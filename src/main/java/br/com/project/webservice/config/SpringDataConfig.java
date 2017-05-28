package br.com.project.webservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
public class SpringDataConfig {
	@Configuration
	@EnableJpaRepositories(basePackages = {"br.com.project.webservice.dao.postgres","br.com.project.webservice.auth.repository","br.com.project.webservice.repository"}, 
		transactionManagerRef="postgresTransactionManager" , entityManagerFactoryRef = "entityManagerFactoryPostgres")
	public static class DummyToConfigSpringDataPostgres{
		
	}
}
