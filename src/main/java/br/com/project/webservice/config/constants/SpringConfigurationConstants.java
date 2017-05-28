package br.com.project.webservice.config.constants;

public interface SpringConfigurationConstants {

	// XXX PROJECT PROPERTIES
	String PROJECT_CONFIGURATION_FILE_BUNDLE_BEAN_ALIAS = "database";
	String PROJECT_CONFIGURATION_FILE_BUNDLE_BEAN_QUALIFIER = "database";
	String PROJECT_CONFIGURATION_FILE_NAME = "project";

	// XXX POSTGRE CONSTANTS
	String POSTGRE_DATA_SOURCE_BEAN_ALIAS = "postgresDataSource";
	String POSTGRE_DATA_SOURCE_BEAN_QUALIFIER = "postgresDataSource";

	String POSTGRE_MANAGER_FACTORY_BEAN_ALIAS = "entityManagerFactoryPostgres";
	String POSTGRE_MANAGER_FACTORY_BEAN_QUALIFIER = "entityManagerFactoryPostgres";

	String POSTGRE_SQL_FACTORY_BEAN_ALIAS = "postgresSqlFactory";
	String POSTGRE_SQL_FACTORY_BEAN_QUALIFIER = "postgresSqlFactory";

	String POSTGRE_MYBATIS_MAPPER_SCANNER_BEAN_ALIAS = "postgresMapperScannerConfigurer";
	String POSTGRE_MYBATIS_MAPPER_SCANNER_BEAN_QUALIFIER = "postgresMapperScannerConfigurer";

	String POSTGRE_TRANSACTION_MANAGER_BEAN_ALIAS = "postgresTransactionManager";
	String POSTGRE_TRANSACTION_MANAGER_BEAN_QUALIFIER = "postgresTransactionManager";

	String POSTGRE_TRANSACTION_TEMPLATE_BEAN_ALIAS = "postgresTransactionTemplate";
	String POSTGRE_TRANSACTION_TEMPLATE_BEAN_QUALIFIER = "postgresTransactionTemplate";

	String POSTGRE_PERSISTENT_UNIT_NAME = "postgresPU";

	String[] POSTGRE_ENTITY_PACKAGES_TO_SCAN = new String[] { "br.com.project.webservice.entity", "br.com.project.webservice.models",
			"br.com.project.webservice.auth.model" };

	String POSTGRE_MYBATIS_MAPPER_PACKAGE_TO_SCAN = "br.com.project.webservice.dao.mappers.postgres";

}
