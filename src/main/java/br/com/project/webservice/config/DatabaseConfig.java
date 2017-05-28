package br.com.project.webservice.config;

import static br.com.project.webservice.config.constants.SpringConfigurationConstants.POSTGRE_DATA_SOURCE_BEAN_ALIAS;
import static br.com.project.webservice.config.constants.SpringConfigurationConstants.POSTGRE_DATA_SOURCE_BEAN_QUALIFIER;
import static br.com.project.webservice.config.constants.SpringConfigurationConstants.POSTGRE_ENTITY_PACKAGES_TO_SCAN;
import static br.com.project.webservice.config.constants.SpringConfigurationConstants.POSTGRE_MANAGER_FACTORY_BEAN_ALIAS;
import static br.com.project.webservice.config.constants.SpringConfigurationConstants.POSTGRE_PERSISTENT_UNIT_NAME;
import static br.com.project.webservice.config.constants.SpringConfigurationConstants.POSTGRE_TRANSACTION_MANAGER_BEAN_ALIAS;
import static br.com.project.webservice.config.constants.SpringConfigurationConstants.POSTGRE_TRANSACTION_MANAGER_BEAN_QUALIFIER;
import static br.com.project.webservice.config.constants.SpringConfigurationConstants.POSTGRE_TRANSACTION_TEMPLATE_BEAN_ALIAS;
import static br.com.project.webservice.config.constants.SpringConfigurationConstants.POSTGRE_TRANSACTION_TEMPLATE_BEAN_QUALIFIER;
import static br.com.project.webservice.config.constants.SpringConfigurationConstants.PROJECT_CONFIGURATION_FILE_BUNDLE_BEAN_ALIAS;
import static br.com.project.webservice.config.constants.SpringConfigurationConstants.PROJECT_CONFIGURATION_FILE_BUNDLE_BEAN_QUALIFIER;
import static br.com.project.webservice.config.constants.SpringConfigurationConstants.PROJECT_CONFIGURATION_FILE_NAME;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
	@Bean(name = PROJECT_CONFIGURATION_FILE_BUNDLE_BEAN_ALIAS)
	@Qualifier(value = PROJECT_CONFIGURATION_FILE_BUNDLE_BEAN_QUALIFIER)
	public ResourceBundle database() {
		return ResourceBundle.getBundle(PROJECT_CONFIGURATION_FILE_NAME);
	}

	@Bean(name = POSTGRE_DATA_SOURCE_BEAN_ALIAS)
	@Qualifier(POSTGRE_DATA_SOURCE_BEAN_QUALIFIER)
	public DataSource postgresDataSource(
			@Qualifier(value = PROJECT_CONFIGURATION_FILE_BUNDLE_BEAN_QUALIFIER) ResourceBundle databaseBundle)
			throws PropertyVetoException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setDriverClass("org.postgresql.Driver");
		ds.setJdbcUrl(databaseBundle.getString("postgres.url"));
		ds.setUser(databaseBundle.getString("postgres.username"));
		ds.setPassword(databaseBundle.getString("postgres.password"));
		ds.setInitialPoolSize(1);
		ds.setMinPoolSize(1);
		ds.setMaxPoolSize(10);
		ds.setAcquireIncrement(1);
		ds.setMaxStatements(0);
		ds.setAcquireRetryAttempts(15);
		ds.setAcquireRetryDelay(1000);
		ds.setBreakAfterAcquireFailure(false);
		ds.setMaxIdleTime(30000);
		ds.setMaxConnectionAge(10);
		ds.setCheckoutTimeout(30000);
		ds.setIdleConnectionTestPeriod(60);
		ds.setTestConnectionOnCheckout(true);
		ds.setPreferredTestQuery("SELECT 1");
		ds.setTestConnectionOnCheckin(true);
		return ds;
	}

	@Lazy(true)
	@Bean(name = POSTGRE_MANAGER_FACTORY_BEAN_ALIAS)
	@DependsOn(value = POSTGRE_DATA_SOURCE_BEAN_QUALIFIER)
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryPostgres(
			@Qualifier(POSTGRE_DATA_SOURCE_BEAN_QUALIFIER) DataSource dataSource,
			@Qualifier(value = PROJECT_CONFIGURATION_FILE_BUNDLE_BEAN_QUALIFIER) ResourceBundle databaseBundle) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPersistenceUnitName(POSTGRE_PERSISTENT_UNIT_NAME);		
		entityManagerFactoryBean.setPackagesToScan(POSTGRE_ENTITY_PACKAGES_TO_SCAN);
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
		jpaProperties.put("hibernate.show_sql", databaseBundle.getString("jpa.show_sql"));
		jpaProperties.put("hibernate.format_sql", databaseBundle.getString("jpa.format_sql"));
		jpaProperties.put("hibernate.generate_statistics", false);
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		return entityManagerFactoryBean;
	}

	/*
	 * @Lazy(true)
	 * 
	 * @Bean(name = POSTGRE_SQL_FACTORY_BEAN_ALIAS)
	 * 
	 * @Qualifier(value = POSTGRE_SQL_FACTORY_BEAN_QUALIFIER) public
	 * SqlSessionFactoryBean
	 * postgresSqlFactory(@Qualifier(POSTGRE_DATA_SOURCE_BEAN_QUALIFIER)
	 * DataSource dataSource, ResourceLoader resourceLoader) throws IOException
	 * { SqlSessionFactoryBean sqlSessionFactoryBean = new
	 * SqlSessionFactoryBean(); sqlSessionFactoryBean.setDataSource(dataSource);
	 * sqlSessionFactoryBean.setConfigLocation(new
	 * ClassPathResource("mybatis/mybatis-postgres-config.xml")); Resource[]
	 * resources =
	 * ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
	 * .getResources("classpath:mybatis/postgres/*.xml");
	 * sqlSessionFactoryBean.setMapperLocations(resources); return
	 * sqlSessionFactoryBean; }
	 */

	@Bean(name = POSTGRE_TRANSACTION_MANAGER_BEAN_ALIAS)
	@Qualifier(POSTGRE_TRANSACTION_MANAGER_BEAN_QUALIFIER)
	public JpaTransactionManager transactionManagerPostgres(
			@Qualifier(POSTGRE_MANAGER_FACTORY_BEAN_ALIAS) EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Lazy(true)
	@Bean(name = POSTGRE_TRANSACTION_TEMPLATE_BEAN_ALIAS)
	@Qualifier(POSTGRE_TRANSACTION_TEMPLATE_BEAN_QUALIFIER)
	public TransactionTemplate postgresTransactionTemplate(
			@Qualifier(POSTGRE_TRANSACTION_MANAGER_BEAN_QUALIFIER) JpaTransactionManager transactionManager) {
		return new TransactionTemplate(transactionManager);
	}

	/*
	 * @SuppressWarnings("deprecation")
	 * 
	 * @Lazy(true)
	 * 
	 * @Bean(name = POSTGRE_MYBATIS_MAPPER_SCANNER_BEAN_ALIAS)
	 * 
	 * @Qualifier(value = POSTGRE_MYBATIS_MAPPER_SCANNER_BEAN_QUALIFIER) public
	 * MapperScannerConfigurer configPostgresMapperScanner(
	 * 
	 * @Qualifier(POSTGRE_SQL_FACTORY_BEAN_QUALIFIER) SqlSessionFactoryBean
	 * sessionFactoryBean) throws Exception { MapperScannerConfigurer
	 * mapperScannerConfigurer = new MapperScannerConfigurer();
	 * mapperScannerConfigurer.setSqlSessionFactory(sessionFactoryBean.getObject
	 * ()); mapperScannerConfigurer.setBasePackage(
	 * POSTGRE_MYBATIS_MAPPER_PACKAGE_TO_SCAN); return mapperScannerConfigurer;
	 * }
	 */
}
