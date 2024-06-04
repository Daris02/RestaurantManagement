package com.hei.app.restaurantmanagement.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hei.app.restaurantmanagement.config.ConnectionDB;
import com.hei.app.restaurantmanagement.model.AutoCRUD;
import com.hei.app.restaurantmanagement.model.entity.Ingredient;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class IngredientRepository extends AutoCRUD<Ingredient, Integer> {
    @Override
    protected String getTableName() {
        return "ingredient";
    }

    @Override
    protected Ingredient mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Ingredient(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("priceId"),
                resultSet.getInt("unityId")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ingredient> getByMenuId(Integer id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT i.* FROM " + getTableName() + 
                        " i INNER JOIN ingredientMenu im ON i.id = im.ingredientId " +
                        " WHERE im.menuId = " + id + ";";

        try {
            connection = ConnectionDB.createConnection();
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ingredients.add(mapResultSetToEntity(resultSet));
            }
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
