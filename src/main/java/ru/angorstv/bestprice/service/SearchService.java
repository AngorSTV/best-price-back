package ru.angorstv.bestprice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.angorstv.bestprice.common.Direct;
import ru.angorstv.bestprice.common.SearchRequest;
import ru.angorstv.bestprice.entity.Product;
import ru.angorstv.bestprice.harvester.AliHarvester;
import ru.angorstv.bestprice.harvester.OzonHarvester;
import ru.angorstv.bestprice.harvester.WildberriesHarvester;

import java.util.LinkedList;
import java.util.List;

@Service
public class SearchService {
    
    private final WildberriesHarvester wildberriesHarvester;
    private final AliHarvester aliHarvester;
    private final OzonHarvester ozonHarvester;
    
    public SearchService(WildberriesHarvester wildberriesHarvester, AliHarvester aliHarvester, OzonHarvester ozonHarvester) {
        this.wildberriesHarvester = wildberriesHarvester;
        this.aliHarvester = aliHarvester;
        this.ozonHarvester = ozonHarvester;
    }
    
    public Page<Product> search(SearchRequest searchRequest) throws JsonProcessingException {
        List<Product> products = new LinkedList<>();
        products.addAll(wildberriesHarvester.getProducts(searchRequest.getTerm()));
        //products.addAll(aliHarvester.getProducts(searchRequest.getTerm()));
        //products.addAll(ozonHarvester.getProducts(searchRequest.getTerm()));
        //productRepository.saveAll(products);
        return getFromBd(searchRequest);
    }
    
    private Page<Product> getFromBd(SearchRequest searchRequest) {
        String term = searchRequest.getTerm();
        String field;
        switch (searchRequest.getSorting()) {
            case RELEVANT -> field = "name";
            case SHOP -> field = "shop";
            default -> field = "price";
        }
        Sort sort;
        if (searchRequest.getDirect().equals(Direct.ASC)) {
            sort = Sort.by(field).ascending();
        } else {
            sort = Sort.by(field).descending();
        }
        PageRequest pageRequest = PageRequest.of(searchRequest.getPage(), searchRequest.getPageSize(), sort);
        return null; //productRepository.findByNamePaging(term, pageRequest);
    }
}
