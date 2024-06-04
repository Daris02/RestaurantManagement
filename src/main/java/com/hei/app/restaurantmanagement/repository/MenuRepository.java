package com.hei.app.restaurantmanagement.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.hei.app.restaurantmanagement.model.AutoCRUD;
import com.hei.app.restaurantmanagement.model.entity.Menu;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class MenuRepository extends AutoCRUD<Menu, Integer> {
    @Override
    protected String getTableName() {
        return "menu";
    }

    @Override
    protected Menu mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Menu(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("costPrice"),
                resultSet.getDouble("sellPrice"),
                resultSet.getTimestamp("createAt").toInstant(),
                new ArrayList<>()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
