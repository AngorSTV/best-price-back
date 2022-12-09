package ru.angorstv.bestprice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

@Slf4j
@RestController
@RequestMapping("/api")
public class SearchController {
    
    private final SearchService searchService;
    
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> search(@RequestParam String term,
                                                @RequestParam(required = false) Sorting sort,
                                                @RequestParam(required = false) Direct direct,
                                                @RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) Integer size) throws JsonProcessingException {
        SearchRequest searchRequest = new SearchRequest(term, sort, direct, page, size);
        return ResponseEntity.ok(searchService.search(searchRequest));
    }
}
