package com.hammi.q_bus_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
@SpringBootApplication
public class QBusBackendApplication {

    static void main(String[] args) {
        SpringApplication.run(QBusBackendApplication.class, args);
    }

}
