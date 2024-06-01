package com.hei.app.kfc.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hei.app.kfc.model.entity.Menu;
import com.hei.app.kfc.repository.MenuRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

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
