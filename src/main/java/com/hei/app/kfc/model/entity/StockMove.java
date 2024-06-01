package com.hei.app.kfc.model.entity;

import java.time.Instant;

import com.hei.app.kfc.model.enums.MoveType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockMove extends Stock {
    private MoveType type;

    public StockMove(Integer id, Double value, Instant createAt, Integer ingredientId, Integer restaurantId,
            MoveType type) {
        super(id, value, createAt, ingredientId, restaurantId);
        this.type = type;
    }
}
