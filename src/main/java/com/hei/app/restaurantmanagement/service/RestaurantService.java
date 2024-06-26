package com.hei.app.restaurantmanagement.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.hei.app.restaurantmanagement.model.entity.Restaurant;
import com.hei.app.restaurantmanagement.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repository;
    private final MenuService menuService;

    public Restaurant getById(Integer id) {
        Restaurant restaurant  = repository.getById(id);
        restaurant.setMenus(menuService.getAll());
        return restaurant;
    }

    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = repository.findAll();
        for (Restaurant restaurant : restaurants) restaurant.setMenus(menuService.getAll());
        return restaurants;
    }

    public List<Restaurant> saveAll(List<Restaurant> toSaves) {
        return toSaves.stream().map(this::save).toList();
    }

    private Restaurant save(Restaurant toSave) {
        repository.save(toSave);
        Optional<Restaurant> restaurant = repository.findAll().stream()
            .max(Comparator.comparingInt(Restaurant::getId))
            .stream().findFirst();
        return restaurant.orElse(toSave);
    }

}
