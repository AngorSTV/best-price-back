package ru.angorstv.bestprice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.angorstv.bestprice.repository.ProductRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class IndexGarbageCollector {
    
    @Value("${igc.actual.time}")
    private Integer actualTime;
    
    private final ProductRepository repository;
    
    public IndexGarbageCollector(ProductRepository repository) {
        this.repository = repository;
    }
    
    @Scheduled(cron = "${igc.cron.expression}")
    public void clean() {
        String date = LocalDateTime.now().minusMinutes(actualTime).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
        long cleanIndex = repository.cleanIndex(date);
        log.info("IGC delete " + cleanIndex + " products.");
    }
}
