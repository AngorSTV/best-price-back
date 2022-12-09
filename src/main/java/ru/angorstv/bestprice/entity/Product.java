package ru.angorstv.bestprice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.angorstv.bestprice.common.Shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Builder
@Getter
@Setter
@Entity
@IdClass(ProductId.class)
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String originalId;
    
    @Id
    @Enumerated(EnumType.STRING)
    private Shop shop;
    
    @Column(length = 2048)
    private String name;
    private String brand;
    private Integer price;
    @Column(length = 2048)
    private String url;
    private String description;
    private String imageUrl;
}

