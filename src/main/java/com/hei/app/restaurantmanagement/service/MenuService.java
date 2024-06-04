package com.hei.app.restaurantmanagement.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.hei.app.restaurantmanagement.model.entity.Menu;
import com.hei.app.restaurantmanagement.repository.MenuRepository;

@Service
@AllArgsConstructor
public class MenuService {
    private final MenuRepository repository;
    private final IngredientMenuService ingredientsService;

    public Menu getById(Integer id) {
        Menu menu = repository.getById(id);
        menu.setIngredients(ingredientsService.getByMenuId(id));
        return menu;
    }

    public List<Menu> getAll() {
        return repository.findAll().stream()
                .map(m -> {
                    m.setIngredients(ingredientsService.getByMenuId(m.getId()));
                    return m;
                }).collect(Collectors.toList());
    }

    public List<Menu> saveAll(List<Menu> toSaves) {
        return toSaves.stream().map(this::save).toList();
    }
    
    private Menu save(Menu toSave) {
        toSave.setCreateAt(Instant.now());
        repository.save(toSave);
        Optional<Menu> menu = repository.findAll().stream()
            .max(Comparator.comparingInt(Menu::getId))
            .stream().findFirst();
        return menu.orElse(toSave);
    }

}
