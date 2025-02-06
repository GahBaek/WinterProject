package com.example.winterdeom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WinterDeomApplication {

    public static void main(String[] args) {
        SpringApplication.run(WinterDeomApplication.class, args);
    }

}
