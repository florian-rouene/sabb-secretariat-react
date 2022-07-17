package fr.sabb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class SabbSecretariat {

    public static void main(String[] args) {
        SpringApplication.run(SabbSecretariat.class, args);
    }
}
