package ru.angorstv.bestprice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.angorstv.bestprice.entity.Product;
import ru.angorstv.bestprice.service.SearchService;

import java.util.List;

@SpringBootTest
class BestPriceApplicationTests {

    @Autowired
    private SearchService searchService;

    @Test
    void contextLoads() throws JsonProcessingException {
        List<Product> products = searchService.search("huawei nova 8");
    }

}
