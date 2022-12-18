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
    private String id;
    
    @Field(type = FieldType.Text)
    private String originalId;
    
    @Field(type = FieldType.Auto)
    private Shop shop;
    
    @Field(type = FieldType.Text)
    private String name;
    
    @Field(type = FieldType.Text)
    private String brand;
    
    @Field(type = FieldType.Integer)
    private Integer price;
    
    @Field(type = FieldType.Text)
    private String url;
    
    @Field(type = FieldType.Text)
    private String description;
    
    @Field(type = FieldType.Text)
    private String imageUrl;
    
    @Field(type = FieldType.Date)
    private LocalDateTime lastUpDate;
    
    public static ProductBuilder builder() {
        return new CustomProductBuilder();
    }
    
    private static class CustomProductBuilder extends ProductBuilder {
        @Override
        public Product build() {
            super.id(super.shop + "_" + super.originalId);
            return super.build();
        }
    }
}

