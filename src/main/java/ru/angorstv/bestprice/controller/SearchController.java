package ru.angorstv.bestprice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.angorstv.bestprice.service.SearchService;

@Slf4j
@RestController
@RequestMapping("/api")
public class SearchController {

    private final SearchService searchService;
    private final ObjectMapper objectMapper;

    public SearchController(SearchService searchService, ObjectMapper objectMapper) {
        this.searchService = searchService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam String value) throws JsonProcessingException {
        String result = objectMapper.writeValueAsString(searchService.search(value));
        return ResponseEntity.ok(result);
    }
}
