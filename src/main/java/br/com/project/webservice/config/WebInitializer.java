package br.com.project.webservice.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{SpringConfig.class,DatabaseConfig.class , SpringDataConfig.class, SecurityConfig.class, SpringWebConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{ SpringWebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		System.out.println("Testando API");
		return new String[] { "/api/**" };
	}

}
