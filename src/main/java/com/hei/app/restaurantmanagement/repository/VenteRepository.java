package com.hei.app.restaurantmanagement.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.hei.app.restaurantmanagement.model.AutoCRUD;
import com.hei.app.restaurantmanagement.model.entity.Vente;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class VenteRepository extends AutoCRUD<Vente, Integer> {
    @Override
    protected String getTableName() {
        return "vente";
    }

    @Override
    protected Vente mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Vente(
                resultSet.getInt("id"),
                resultSet.getDouble("amount"),
                resultSet.getTimestamp("createAt").toInstant(),
                resultSet.getInt("restaurantId"),
                resultSet.getInt("menuId")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
