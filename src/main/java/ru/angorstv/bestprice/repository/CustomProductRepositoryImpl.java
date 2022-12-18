package ru.angorstv.bestprice.repository;

import co.elastic.clients.json.JsonData;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import ru.angorstv.bestprice.entity.Product;

public class CustomProductRepositoryImpl implements CustomProductRepository {
    
    private final ElasticsearchOperations operations;
    
    public CustomProductRepositoryImpl(ElasticsearchOperations operations) {
        this.operations = operations;
    }
    
    @Override
    public long cleanIndex(String date) {
        ByQueryResponse queryResponse = operations.delete(NativeQuery.builder()
            .withQuery(q -> q
                .range(r -> r
                    .field("lastUpDate")
                    .lte(JsonData.of(date))
                    .format("yyyy-MM-dd")))
            .build(), Product.class);
        return queryResponse.getDeleted();
    }
}
