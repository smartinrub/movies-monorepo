package org.smartinrub.orchestratorservice;

import org.smartinrub.orchestratorservice.client.ReviewClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = ReviewClient.class)
public class OrchestratorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrchestratorServiceApplication.class, args);
    }
}
