package ru.angorstv.bestprice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.angorstv.bestprice.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM PRODUCT WHERE LOCATE(?, NAME)")
    List<Product> findValue(String value);
}
