package com.hei.app.kfc.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hei.app.kfc.model.entity.Ingredient;
import com.hei.app.kfc.model.entity.Price;
import com.hei.app.kfc.repository.IngredientRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IngredientService {
    private final IngredientRepository repository;
    private final PriceService priceService;

    public Ingredient getById(Integer id) {
        Ingredient ingredient = repository.getById(id);
        ingredient.setPrice(priceService.getById(ingredient.getPriceId()).getValue());
        return ingredient;
    }

    public List<Ingredient> getAll() {
        return repository.findAll().stream()
                    .map(i -> {
                        i.setPrice(priceService.getById(i.getPriceId()).getValue());
                        return i;
                    })
                    .collect(Collectors.toList());
    }

    public List<Ingredient> saveAll(List<Ingredient> toSaves) {
        return toSaves.stream().map(this::save).toList();
    }

    public Ingredient save(Ingredient toSave) {
        Ingredient ingredient = repository.getByName(toSave.getName());
        if (ingredient != null) return ingredient;
        
        Price price = priceService.save(new Price(toSave.getPrice()));
        toSave.setPriceId(price.getId());
        
        repository.save(toSave);

        Optional<Ingredient> ingredientOptional = repository.findAll().stream()
                .max(Comparator.comparingInt(Ingredient::getId))
                .stream().findFirst();
        if (ingredientOptional.isPresent()) {
            Ingredient ingredientSaved = ingredientOptional.get();
            ingredientSaved.setPrice(toSave.getPrice());
            return ingredientSaved;
        }
        return toSave;
    }

    public List<Ingredient> getAllByMenuId(Integer id) {
        List<Ingredient> ingredients = repository.getByMenuId(id);
        return ingredients;
    }
}
