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
import com.hei.app.restaurantmanagement.model.entity.IngredientMenu;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class IngredientMenuRepository extends AutoCRUD<IngredientMenu, Integer> {
    @Override
    protected String getTableName() {
        return "ingredientMenu";
    }

    @Override
    protected IngredientMenu mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new IngredientMenu(
                resultSet.getInt("id"),
                resultSet.getDouble("quantity"),
                resultSet.getInt("menuId"),
                resultSet.getInt("ingredientId")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<IngredientMenu> getByMenuId(Integer id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<IngredientMenu> ingredients = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName() + " WHERE menuId = " + id + ";";

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
