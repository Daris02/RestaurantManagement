package com.hei.app.restaurantmanagement.service;

import java.util.List;
import java.util.Optional;
import java.time.Instant;
import java.util.Comparator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.hei.app.restaurantmanagement.model.entity.IngredientMenu;
import com.hei.app.restaurantmanagement.model.entity.Menu;
import com.hei.app.restaurantmanagement.model.entity.Stock;
import com.hei.app.restaurantmanagement.model.entity.StockMove;
import com.hei.app.restaurantmanagement.model.entity.Vente;
import com.hei.app.restaurantmanagement.model.enums.MoveType;
import com.hei.app.restaurantmanagement.repository.VenteRepository;

@Service
@AllArgsConstructor
public class VenteService {
    private final VenteRepository repository;
    private final MenuService menuService;
    private final StockService stockService;

    public Vente getById(Integer id) {
        return repository.getById(id);
    }

    public List<Vente> getAll() {
        return repository.findAll();
    }

    public List<Vente> saveAll(List<Vente> toSaves) {
        return toSaves.stream().map(this::save).toList();
    }

    private Vente save(Vente toSave) {
        toSave.setCreateAt(Instant.now().plusSeconds(10800));
        Menu menu = menuService.getById(toSave.getMenuId());
        if (menu == null) return null;
        toSave.setAmount(menu.getSellPrice());

        StockMove stockMove = new StockMove();
        stockMove.setCreateAt(menu.getCreateAt());
        stockMove.setRestaurantId(toSave.getRestaurantId());
        stockMove.setType(MoveType.OUT);

        if (canMakeVente(menu, stockMove)) {
            repository.save(toSave);
        }

        Optional<Vente> vente = repository.findAll().stream()
                .max(Comparator.comparingInt(Vente::getId))
                .stream().findFirst();
        return vente.orElse(toSave);
    }

    private boolean canMakeVente(Menu menu, StockMove stockMove) {
        for (IngredientMenu ingredientMenu : menu.getIngredients()) {
            Stock stockOfIngredient = stockService.getByIngredientId(ingredientMenu.getIngredientId());
            if (stockOfIngredient.getValue() < ingredientMenu.getQuantity()) 
                throw new RuntimeException("Ingredient stock not enough");
            stockMove.setIngredientId(ingredientMenu.getIngredientId());
            stockMove.setValue(ingredientMenu.getQuantity());
            stockService.save(stockMove);
        }
        return true;
    }
}
