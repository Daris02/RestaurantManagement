package com.hei.app.kfc.controller;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hei.app.kfc.model.entity.Ingredient;
import com.hei.app.kfc.service.IngredientService;

@RestController
@AllArgsConstructor
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService service;

    @GetMapping({"", "/"})
    public List<Ingredient> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Ingredient getById(@PathVariable("id") Integer id) {
        return service.getById(id);
    }

    @PostMapping({"", "/"})
    public List<Ingredient> create(@RequestBody List<Ingredient> ingredients) {
        return service.saveAll(ingredients);
    }
}
