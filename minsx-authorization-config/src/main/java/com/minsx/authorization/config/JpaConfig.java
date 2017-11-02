package com.minsx.authorization.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages="com.minsx.authorization")
@EnableJpaRepositories(basePackages="com.minsx.authorization")
public class JpaConfig {

}
