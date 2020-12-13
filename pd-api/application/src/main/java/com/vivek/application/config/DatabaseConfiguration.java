package com.vivek.application.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The Class DatabaseConfiguration.
 */
@Configuration
@EnableJpaRepositories("com.vivek.dao.repository")
@EntityScan("com.vivek.dao.domain")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
