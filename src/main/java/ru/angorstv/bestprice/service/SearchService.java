package ru.angorstv.bestprice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.angorstv.bestprice.common.SearchRequest;
import ru.angorstv.bestprice.entity.Product;
import ru.angorstv.bestprice.harvester.AliHarvester;
import ru.angorstv.bestprice.harvester.OzonHarvester;
import ru.angorstv.bestprice.harvester.WildberriesHarvester;
import ru.angorstv.bestprice.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.LinkedList;
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
    public List<Product> search(SearchRequest searchRequest) throws JsonProcessingException {
        List<Product> products = new LinkedList<>();
        products.addAll(wildberriesHarvester.getProducts(searchRequest.getTerm()));
        //products.addAll(aliHarvester.getProducts(searchRequest.getTerm()));
        //products.addAll(ozonHarvester.getProducts(searchRequest.getTerm()));
        productRepository.saveAll(products);
        return getFromBd(searchRequest);
    }

    private List<Product> getFromBd(SearchRequest searchRequest) {
        String term = searchRequest.getTerm();
        int offset = searchRequest.getPage() * searchRequest.getPageSize();
        int size = searchRequest.getPageSize();
        String field;
        switch (searchRequest.getSorting()) {
            case RELEVANT -> field = "name";
            case SHOP -> field = "shop";
            default -> field = "price";
        }
        return productRepository.findByNamePaging(term, PageRequest.of(searchRequest.getPage(), searchRequest.getPageSize(), Sort.by(field).ascending())).getContent();
    }
}
