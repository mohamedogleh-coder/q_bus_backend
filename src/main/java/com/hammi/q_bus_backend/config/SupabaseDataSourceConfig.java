package com.hammi.q_bus_backend.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "supabseEntityManager",
        transactionManagerRef = "supabaseTransactionManager",
        basePackages = {"com.hammi.q_bus_backend.supabase"}
)
public class SupabaseDataSourceConfig {

    @ConfigurationProperties("spring.datasource.secondary")
    @Bean
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean
    public DataSource supabaseDataSource() {
        return secondaryDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean supabseEntityManager(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("supabaseDataSource") DataSource dataSource) {
        return entityManagerFactoryBuilder.dataSource(dataSource)
                .packages("com.hammi.q_bus_backend.supabase")
                .build();
    }

    @Bean
    public PlatformTransactionManager supabaseTransactionManager(
            @Qualifier("supabseEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}