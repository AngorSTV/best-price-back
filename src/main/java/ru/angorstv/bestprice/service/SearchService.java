package ru.angorstv.bestprice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import ru.angorstv.bestprice.entity.Product;
import ru.angorstv.bestprice.harvester.AliHarvester;
import ru.angorstv.bestprice.harvester.OzonHarvester;
import ru.angorstv.bestprice.harvester.WildberriesHarvester;
import ru.angorstv.bestprice.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final WildberriesHarvester wildberriesHarvester;
    private final AliHarvester aliHarvester;
    private final OzonHarvester ozonHarvester;
    private final ProductRepository productRepository;

    public SearchService(WildberriesHarvester wildberriesHarvester, AliHarvester aliHarvester, OzonHarvester ozonHarvester, ProductRepository productRepository) {
        this.wildberriesHarvester = wildberriesHarvester;
        this.aliHarvester = aliHarvester;
        this.ozonHarvester = ozonHarvester;
        this.productRepository = productRepository;
    }

    @Transactional
    public List<Product> search(String value) throws JsonProcessingException {
        List<Product> products = new ArrayList<>();
        products.addAll(wildberriesHarvester.getProducts(value));
        //products.addAll(aliHarvester.getProducts(value));
        products.addAll(ozonHarvester.getProducts(value));
        productRepository.saveAll(products);
        List<Product> resultProducts = productRepository.findValue(value);
        return resultProducts;
    }
}
