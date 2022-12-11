package ru.angorstv.bestprice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.angorstv.bestprice.common.Shop;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ProductId implements Serializable {
    private String originalId;
    
    //@Enumerated(EnumType.STRING)
    private Shop shop;
}
