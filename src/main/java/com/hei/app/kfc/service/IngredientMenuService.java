package com.hei.app.kfc.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.hei.app.kfc.model.entity.IngredientMenu;
import com.hei.app.kfc.repository.IngredientMenuRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IngredientMenuService {
    private final IngredientMenuRepository repository;

    public IngredientMenu getById(Integer id) {
        return repository.getById(id);
    }

    public List<IngredientMenu> getAll() {
        return repository.findAll();
    }

    public List<IngredientMenu> saveAll(List<IngredientMenu> toSaves) {
        return toSaves.stream().map(this::save).toList();
    }

    private IngredientMenu save(IngredientMenu toSave) {
        repository.save(toSave);
        Optional<IngredientMenu> ingredientMenu = repository.findAll().stream()
                .max(Comparator.comparingInt(IngredientMenu::getId))
                .stream().findFirst();
        return ingredientMenu.orElse(toSave);
    }

    public List<IngredientMenu> getByMenuId(Integer id) {
        return repository.getByMenuId(id);
    }
    
}
