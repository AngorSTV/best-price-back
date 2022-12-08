package ru.angorstv.bestprice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.angorstv.bestprice.common.Direct;
import ru.angorstv.bestprice.common.SearchRequest;
import ru.angorstv.bestprice.common.Sorting;
import ru.angorstv.bestprice.entity.Product;
import ru.angorstv.bestprice.service.SearchService;

import java.util.List;

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
    public ResponseEntity<List<Product>> search(@RequestParam String term,
                                                @RequestParam(required = false) Sorting sort,
                                                @RequestParam(required = false) Direct direct,
                                                @RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) Integer size) throws JsonProcessingException {
        SearchRequest searchRequest = new SearchRequest(term, sort, direct, page, size);
        return ResponseEntity.ok(searchService.search(searchRequest));
    }
}
