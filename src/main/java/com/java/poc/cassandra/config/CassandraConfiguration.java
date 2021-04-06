package com.java.poc.cassandra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


/**
 * @author kanhaiya kumar
 *
 */
@Configuration
@EnableCassandraRepositories(basePackages = { "com.java.poc.cassandra.repository" })
public class CassandraConfiguration extends AbstractCassandraConfiguration {

	@Override
	protected String getKeyspaceName() {
		return "studentkeyspace";
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	@Override
	protected int getPort() {
		return 9042;
	}
	
	@Override
	protected String getContactPoints() {
		return "127.0.0.1";
	}
	
	@Override
	public String[] getEntityBasePackages() {
		return new String[] {"com.java.poc.cassandra.model"};
	}

}
