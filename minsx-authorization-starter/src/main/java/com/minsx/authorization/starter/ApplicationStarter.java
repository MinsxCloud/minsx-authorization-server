package com.minsx.authorization.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * ApplicationStarter Created by Joker on 2017/10/31.
 */
//@RestController
@SpringBootApplication
@EnableAuthorizationServer
@ComponentScan(basePackages = "com.minsx.authorization")
public class ApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

}
