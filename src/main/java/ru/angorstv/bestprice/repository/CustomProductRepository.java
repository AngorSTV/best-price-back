package ru.angorstv.bestprice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.angorstv.bestprice.entity.Product;

public interface CustomProductRepository {

    Page<Product> findByNamePaging(String term, Pageable pageable);
}
