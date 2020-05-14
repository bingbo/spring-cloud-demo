package com.ibingbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
