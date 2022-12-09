package ru.angorstv.bestprice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.angorstv.bestprice.common.Shop;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class ProductId implements Serializable {
    private String originalId;
    
    @Enumerated(EnumType.STRING)
    private Shop shop;
}
