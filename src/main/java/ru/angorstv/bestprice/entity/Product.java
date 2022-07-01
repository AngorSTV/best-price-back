package ru.angorstv.bestprice.entity;

import lombok.*;
import ru.angorstv.bestprice.harvester.Shop;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String originalId;

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
