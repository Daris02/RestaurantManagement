package com.hei.app.restaurantmanagement.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.hei.app.restaurantmanagement.model.AutoCRUD;
import com.hei.app.restaurantmanagement.model.entity.Restaurant;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class RestaurantRepository extends AutoCRUD<Restaurant, Integer> {
    @Override
    protected String getTableName() {
        return "restaurant";
    }

    @Override
    protected Restaurant mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Restaurant(
                resultSet.getInt("id"),
                resultSet.getString("location")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
