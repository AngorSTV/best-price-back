package ru.angorstv.bestprice.harvester;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.angorstv.bestprice.entity.Product;

import java.util.List;

public interface Harvester {
    List<Product> getProducts(String value) throws JsonProcessingException;
}
