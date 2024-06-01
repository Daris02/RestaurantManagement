package com.hei.app.kfc.model.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private Integer id;
    private String name;
    private Double price;
    private Integer priceId;
    private Integer unityId;
    
    public Ingredient(Integer id, String name, Integer priceId, Integer unityId) {
        this.id = id;
        this.name = name;
        this.priceId = priceId;
        this.unityId = unityId;
    }
}
