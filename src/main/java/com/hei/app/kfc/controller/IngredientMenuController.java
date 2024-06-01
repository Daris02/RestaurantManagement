package com.hei.app.kfc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hei.app.kfc.model.entity.IngredientMenu;
import com.hei.app.kfc.service.IngredientMenuService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/ingredients-menu")
public class IngredientMenuController {
    private final IngredientMenuService service;

    @GetMapping({"", "/"})
    public List<IngredientMenu> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public IngredientMenu getById(@PathVariable("id") Integer id) {
        return service.getById(id);
    }

    @PostMapping({"", "/"})
    public List<IngredientMenu> create(@RequestBody List<IngredientMenu> ingredientMenus) {
        return service.saveAll(ingredientMenus);
    }
}
