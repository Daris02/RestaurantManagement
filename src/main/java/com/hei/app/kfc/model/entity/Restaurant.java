package com.hei.app.kfc.model.entity;

import java.util.List;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    private Integer id;
    private String location;
    private List<Menu> menus;

    public Restaurant(Integer id, String location) {
        this.id = id;
        this.location = location;
    }
}
