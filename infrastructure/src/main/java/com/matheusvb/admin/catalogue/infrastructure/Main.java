package com.matheusvb.admin.catalogue.infrastructure;

import com.matheusvb.admin.catalogue.application.UseCase;
import com.matheusvb.admin.catalogue.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("hello application");

        SpringApplication.run(WebServerConfig.class, args);
    }
}