package com.client.system.info.api.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Application config class
 *
 */
@Configuration
@PropertySource("classpath:systemInfo.properties")
@EnableTransactionManagement
public class ApplicationConfig { 
	@Autowired
	private Environment environment;

	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
		return factory -> factory.setContextPath("/systemInfo");
	}

	/**
	 * Data Source bean
	 * 
	 * @return
	 */
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		DataSource dataSource = DataSourceBuilder.create().username(environment.getProperty("systemInfo.datasource.username"))
				.password(environment.getProperty("systemInfo.datasource.password"))
				.url(environment.getProperty("systemInfo.datasource.url"))
				.driverClassName(environment.getProperty("systemInfo.datasource.driverClassName")).build();
		return dataSource;
	}

}
