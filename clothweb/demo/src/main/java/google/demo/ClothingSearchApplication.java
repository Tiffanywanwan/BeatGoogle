package google.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"google.demo", "google.util", "google.config"})
public class ClothingSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClothingSearchApplication.class, args);
    }
}


