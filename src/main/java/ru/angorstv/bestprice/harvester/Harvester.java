package ru.angorstv.bestprice.harvester;

import ru.angorstv.bestprice.entity.Product;

import java.util.List;

public interface Harvester {
    List<Product> getProducts(String value);
}
