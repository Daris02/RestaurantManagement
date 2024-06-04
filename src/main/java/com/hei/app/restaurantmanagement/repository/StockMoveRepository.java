package com.hei.app.restaurantmanagement.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.hei.app.restaurantmanagement.model.AutoCRUD;
import com.hei.app.restaurantmanagement.model.entity.StockMove;
import com.hei.app.restaurantmanagement.model.enums.MoveType;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class StockMoveRepository extends AutoCRUD<StockMove, Integer> {
    @Override
    protected String getTableName() {
        return "stockmove";
    }

    @Override
    protected StockMove mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new StockMove(
                resultSet.getInt("id"),
                resultSet.getDouble("value"),
                resultSet.getTimestamp("createAt").toInstant().minusSeconds(14400),
                resultSet.getInt("ingredientId"),
                resultSet.getInt("restaurantId"),
                MoveType.valueOf(resultSet.getString("type"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
