package com.youssef.gestion_banc;

import com.youssef.gestion_banc.configuration.RsaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaConfig.class)
public class GestionBancApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionBancApplication.class, args);
    }

}
