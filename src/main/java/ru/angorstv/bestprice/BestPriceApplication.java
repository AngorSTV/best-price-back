package ru.angorstv.bestprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BestPriceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BestPriceApplication.class, args);
    }
}
