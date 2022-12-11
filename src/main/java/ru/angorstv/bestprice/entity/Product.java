package ru.angorstv.bestprice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import ru.angorstv.bestprice.common.Shop;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product")
public class Product {
    @Id
    private String originalId;
    private Shop shop;
    private String name;
    private String brand;
    private Integer price;
    private String url;
    private String description;
    private String imageUrl;
    @Field(type = FieldType.Date)
    private LocalDateTime lastUpDate;
}

