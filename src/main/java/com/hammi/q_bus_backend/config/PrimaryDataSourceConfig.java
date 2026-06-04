package com.hammi.q_bus_backend.config; /// /package com.hammi.q_bus_backend.config;
/// /
/// /import org.springframework.beans.factory.annotation.Qualifier;
/// /import org.springframework.boot.context.properties.ConfigurationProperties;
/// /import org.springframework.boot.jdbc.DataSourceBuilder;
/// /import org.springframework.context.annotation.Bean;
/// /import org.springframework.context.annotation.Configuration;
/// /import org.springframework.context.annotation.Primary;
/// /import org.springframework.jdbc.datasource.DataSourceTransactionManager;
/// /import org.springframework.transaction.PlatformTransactionManager;
/// /
/// /import javax.sql.DataSource;
/// /
/// /@Configuration
/// /public class DataSourceConfig {
/// /
/// /    @Primary
/// /    @Bean(name = "primary")
/// /    @ConfigurationProperties("spring.datasource.primary")
/// /    public DataSource primaryDataSource() {
/// /        return DataSourceBuilder.create().build();
/// /    }
/// /
/// /
/// /    @Primary
/// /    @Bean(name = "firstTransactionManager")
/// /    public PlatformTransactionManager firstTransactionManager(
/// /            @Qualifier("primary") DataSource firstDataSource) {
/// /        return new DataSourceTransactionManager(firstDataSource);
/// /    }
/// /
/// /
/// /}
//
//
//
//package com.hammi.q_bus_backend.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import jakarta.persistence.EntityManagerFactory;
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = {
//                "com.hammi.q_bus_backend.modules.buses",
//                "com.hammi.q_bus_backend.modules.categories",
//                "com.hammi.q_bus_backend.modules.print_documents"
//                // Drivers marka laga reebo — taa Supabase ayaa qaadanaysa
//        },
//        entityManagerFactoryRef = "entityManagerFactory",
//        transactionManagerRef   = "firstTransactionManager"
//)
//public class DataSourceConfig {
//
//    @Primary
//    @Bean(name = "primaryDataSource")
//    @ConfigurationProperties("spring.datasource.primary")
//    public DataSource primaryDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Primary
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            EntityManagerFactoryBuilder builder, @Qualifier("primaryDataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages(
//                        "com.hammi.q_bus_backend.modules.bookings",
//                        "com.hammi.q_bus_backend.modules.buses"
//                )
//                .persistenceUnit("primary")
//                .build();
//    }
//
//    @Primary
//    @Bean(name = "firstTransactionManager")
//    public PlatformTransactionManager firstTransactionManager(
//            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "myPrimaryEntityConfig",
        basePackages = {
                "com.hammi.q_bus_backend.modules",
        }
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

}