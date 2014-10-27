/**
 * 
 */
package com.pericles.cooperativa.gestion.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.jolbox.bonecp.BoneCPDataSource;
import com.pericles.cooperativa.gestion.auditor.AuditorAwareBean;

/**
 * Configuración JPA.
 * 
 * @author Jacin
 * 
 */
@Configuration
// (auditorAwareRef = "AuditorAwareBean")
@PropertySource("classpath:application.properties")
public class JpaConfiguration {

	// Configuración de base de datos
	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

	// Configuración de Hibernate
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH = "hibernate.max_fetch_depth";
	private static final String PROPERTY_NAME_HIBERNATE_DEFAULT_BATCH_FETCH_SIZE = "hibernate.default_batch_fetch_size";
	private static final String PROPERTY_NAME_HIBERNATE_USE_SQL_COMMENTS = "hibernate.use_sql_comments";
	private static final String PROPERTY_NAME_HIBERNATE_ID_NEW_GENERATOR_MAPPINGS = "hibernate.id.new_generator_mappings";
	private static final String PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE = "hibernate.jdbc.fetch_size";
	private static final String PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE = "hibernate.jdbc.batch_size";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_IMPORT_FILES = "hibernate.hbm2ddl.import_files";

	// Propiedades JPA
	private static final String PROPERTIY_NAME_JPA_SCHEMA_GENERATION_CREATE_DATABASE_SCHEMAS = "javax.persistence.schema-generation.create-database-schemas";
	private static final String PROPERTIY_NAME_JPA_SCHEMA_GENERATION_SCRIPTS_ACTION = "javax.persistence.schema-generation.scripts.action";
	private static final String PROPERTIY_NAME_JPA_SCHEMA_GENERATION_SCRIPTS_CREATE_TARGET = "javax.persistence.schema-generation.scripts.create-target";
	// private static final String
	// PROPERTIY_NAME_JPA_SCHEMA_GENERATION_SCRIPTS_DROP_TARGET =
	// "javax.persistence.schema-generation.scripts.drop-target";
	private static final String PROPERTIY_NAME_JPA_DATABASE_PRODUCT_NAME = "javax.persistence.database-product-name";

	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

	@Resource
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();

		dataSource.setDriverClass(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setJdbcUrl(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

		// // Cuando se optenga el datasource a través de JNDI:
		// final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		// dsLookup.setResourceRef(true);
		// DataSource dataSource =
		// dsLookup.getDataSource("jdbc/yourJdbcGoesHere");

		return dataSource;
	}

	@Bean
	public Map<String, Object> jpaProperties() {
		Map<String, Object> props = new HashMap<String, Object>();

		// Hibernate Configuration Properties

		props.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment
				.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		props.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment
				.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		props.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment
				.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
		props.put(
				PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH,
				Integer.parseInt(environment
						.getRequiredProperty(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH)));
		props.put(
				PROPERTY_NAME_HIBERNATE_DEFAULT_BATCH_FETCH_SIZE,
				Integer.parseInt(environment
						.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DEFAULT_BATCH_FETCH_SIZE)));
		props.put(PROPERTY_NAME_HIBERNATE_USE_SQL_COMMENTS, environment
				.getRequiredProperty(PROPERTY_NAME_HIBERNATE_USE_SQL_COMMENTS));
		props.put(
				PROPERTY_NAME_HIBERNATE_ID_NEW_GENERATOR_MAPPINGS,
				environment
						.getRequiredProperty(PROPERTY_NAME_HIBERNATE_ID_NEW_GENERATOR_MAPPINGS));

		// Propiedades de Hibernate JDBC y Conexión

		props.put(
				PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE,
				Integer.parseInt(environment
						.getRequiredProperty(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE)));
		props.put(
				PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE,
				Integer.parseInt(environment
						.getRequiredProperty(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE)));

		// Hibernate Cache Properties
		props.put("hibernate.cache.use_second_level_cache", "true");
		props.put("hibernate.cache.region.factory_class",
				EhCacheRegionFactory.class.getName());

		// Propiedades varias de Hibernate

		props.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, environment
				.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
		props.put(
				PROPERTY_NAME_HIBERNATE_HBM2DDL_IMPORT_FILES,
				environment
						.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_IMPORT_FILES));

		// props.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, environment
		// .getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));

		// Para evitar explícitamente incluir el mapeo de tipo
		// Ejemplo: @Type(type =
		// "org.joda.time.contrib.hibernate.PersistentDateTime")
		props.put("jadira.usertype.autoRegisterUserTypes", "true");
		props.put("jadira.usertype.databaseZone", "jvm");
		props.put("jadira.usertype.javaZone", "jvm");

		// Generación de esquema JPA/Hibernate (Comentar en producción)

		props.put(
				PROPERTIY_NAME_JPA_SCHEMA_GENERATION_CREATE_DATABASE_SCHEMAS,
				environment
						.getRequiredProperty(PROPERTIY_NAME_JPA_SCHEMA_GENERATION_CREATE_DATABASE_SCHEMAS));
		props.put(
				PROPERTIY_NAME_JPA_SCHEMA_GENERATION_SCRIPTS_ACTION,
				environment
						.getRequiredProperty(PROPERTIY_NAME_JPA_SCHEMA_GENERATION_SCRIPTS_ACTION));
		props.put(
				PROPERTIY_NAME_JPA_SCHEMA_GENERATION_SCRIPTS_CREATE_TARGET,
				environment
						.getRequiredProperty(PROPERTIY_NAME_JPA_SCHEMA_GENERATION_SCRIPTS_CREATE_TARGET));
		// props.put(
		// PROPERTIY_NAME_JPA_SCHEMA_GENERATION_SCRIPTS_DROP_TARGET,
		// environment
		// .getRequiredProperty(PROPERTIY_NAME_JPA_SCHEMA_GENERATION_SCRIPTS_DROP_TARGET));
		props.put(PROPERTIY_NAME_JPA_DATABASE_PRODUCT_NAME, environment
				.getRequiredProperty(PROPERTIY_NAME_JPA_DATABASE_PRODUCT_NAME));

		// TODO: Hibernate Envers

		return props;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();

		return hibernateJpaVendorAdapter;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {

		return new JpaTransactionManager(entityManagerFactory().getObject());
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(this.dataSource()); // this.dataSource
		lef.setJpaPropertyMap(this.jpaProperties());
		lef.setJpaVendorAdapter(this.jpaVendorAdapter());
		// Así no es necesaria la deficinición en persistence.xml
		lef.setPackagesToScan(environment
				.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
		lef.afterPropertiesSet();

		return lef;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {

		return new HibernateExceptionTranslator();
	}

	@Bean
	public AuditorAwareBean auditorAwareBean() {
		// public AuditorAware<AuditableUser> auditorProvider()

		return new AuditorAwareBean();
	}
}
