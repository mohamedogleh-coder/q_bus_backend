package com.hammi.q_bus_backend.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "myPrimaryEntityConfig",
        basePackages = {
                "com.hammi.q_bus_backend.modules",
        },
        transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryDataSourceConfig {

    @Primary
    @ConfigurationProperties("spring.datasource.primary")
    @Bean
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource primaryDataSource() {
        return primaryDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean myPrimaryEntityConfig(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("primaryDataSource") DataSource dataSource) {
        return entityManagerFactoryBuilder.dataSource(dataSource)
                .packages("com.hammi.q_bus_backend.modules")
                .build();
    }

    @Bean
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("myPrimaryEntityConfig") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}