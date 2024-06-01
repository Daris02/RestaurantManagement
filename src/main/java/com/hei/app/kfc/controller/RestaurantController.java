package com.hei.app.kfc.controller;

import com.hei.app.kfc.model.entity.Restaurant;
import com.hei.app.kfc.service.RestaurantService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService service;

    @GetMapping({"", "/"})
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable("id") Integer id) {
        return service.getById(id);
    }
    
    @PostMapping({"", "/"})
    public List<Restaurant> create(@RequestBody List<Restaurant> restaurants) {
        return service.saveAll(restaurants);
    }
}
