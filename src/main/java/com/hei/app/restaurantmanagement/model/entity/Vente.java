package com.hei.app.restaurantmanagement.model.entity;

import lombok.Data;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vente {
    private Integer id;
    private Double amount;
    private Instant createAt;
    private Integer restaurantId;
    private Integer menuId;
}
