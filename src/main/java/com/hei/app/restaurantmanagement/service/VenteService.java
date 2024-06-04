package com.hei.app.restaurantmanagement.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.hei.app.restaurantmanagement.model.entity.IngredientMenu;
import com.hei.app.restaurantmanagement.model.entity.Menu;
import com.hei.app.restaurantmanagement.model.entity.Restaurant;
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
    private final RestaurantService restaurantService;

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

    public Object summaryMenus(String start, String end) {
        Instant startInstant = dateStringToInstant(start);
        Instant endInstant = dateStringToInstant(end);
        List<Vente> ventesFiltered = getAll().stream().filter(v -> (v.getCreateAt().isAfter(startInstant) && v.getCreateAt().isBefore(endInstant))).toList();
        List<Map<String, Object>> all = new ArrayList<>();
        List<Menu> allMenus = menuService.getAll();
        for (Restaurant restaurant : restaurantService.getAll()) {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("ID restaurant", restaurant.getId());
            result.put("Lieu du restaurant", restaurant.getLocation());
            for (Menu menu : allMenus) {
                long nbrOfMenu = ventesFiltered.stream().filter(v -> v.getMenuId() == menu.getId() && v.getRestaurantId() == restaurant.getId()).count();
                result.put("Nbre vendus " + menu.getName(), nbrOfMenu);
                result.put("Montant vendus " + menu.getName(), nbrOfMenu * menu.getSellPrice());
            }
            all.add(result);
        }
        return all;
    }

    private boolean canMakeVente(Menu menu, StockMove stockMove) {
        for (IngredientMenu ingredientMenu : menu.getIngredients()) {
            Stock stockOfIngredient = stockService.getByIngredientId(ingredientMenu.getIngredientId());
            if (stockOfIngredient != null && stockOfIngredient.getValue() < ingredientMenu.getQuantity()) 
                throw new RuntimeException("Ingredient stock not enough");
            stockMove.setIngredientId(ingredientMenu.getIngredientId());
            stockMove.setValue(ingredientMenu.getQuantity());
            stockService.save(stockMove);
        }
        return true;
    }

    private Instant dateStringToInstant(String dateString) {
        return Timestamp.valueOf(dateString + " 00:00:00").toInstant().minusSeconds(14400);
    }
}
