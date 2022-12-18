package ru.angorstv.bestprice.entity;

import org.junit.jupiter.api.Test;
import ru.angorstv.bestprice.common.Shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {
    
    @Test
    void builder() {
        Product product = Product.builder()
            .originalId("1001")
            .shop(Shop.WILDBERRIES)
            .name("test product")
            .brand("test brand")
            .price(100)
            .build();
        assertEquals("WILDBERRIES_1001", product.getId());
    }
}