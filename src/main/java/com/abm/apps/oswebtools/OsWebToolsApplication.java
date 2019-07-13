package com.abm.apps.oswebtools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OsWebToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OsWebToolsApplication.class, args);
    }

}
