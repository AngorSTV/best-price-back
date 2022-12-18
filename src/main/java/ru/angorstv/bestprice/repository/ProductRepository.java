package ru.angorstv.bestprice.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.angorstv.bestprice.entity.Product;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
}
