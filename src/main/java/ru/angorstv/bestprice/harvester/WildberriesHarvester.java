package ru.angorstv.bestprice.harvester;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.angorstv.bestprice.common.Shop;
import ru.angorstv.bestprice.entity.Product;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class WildberriesHarvester implements Harvester {
    private final static String url = "https://search.wb.ru/exactmatch/ru/common/v4/search";
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    
    
    public List<Product> getProducts(String value) {
        String prefix = "?appType=1&couponsGeo=2,7,3,6,19,21,8&curr=rub&dest=-389344,-108081,-1030059,-5856841&emp=0&lang=ru&locale=ru&page=1&pricemarginCoeff=1.0&query=";
        String suffix = "&reg=1&regions=68,64,83,4,38,80,33,70,82,86,30,69,22,66,31,48,1,40&resultset=catalog&sort=popular&spp=17";
        List<Product> products = new LinkedList<>();
        
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url + prefix + value + suffix, String.class);
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
            jsonNode.get("data").get("products").forEach(node -> {
                Product product = Product.builder()
                    .originalId(node.get("id").asText())
                    .shop(Shop.WILDBERRIES)
                    .name(node.get("name").asText())
                    .brand(node.get("brand").asText())
                    .price(node.get("salePriceU").asInt() / 100)
                    .lastUpDate(LocalDateTime.now())
                    .build();
                products.add(product);
            });
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        log.info("WildberriesHarvester found " + products.size() + " products.");
        return products;
    }
    
}
