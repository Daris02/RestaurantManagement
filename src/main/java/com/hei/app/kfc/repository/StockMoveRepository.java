package com.hei.app.kfc.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.hei.app.kfc.config.ConnectionDB;
import com.hei.app.kfc.model.AutoCRUD;
import com.hei.app.kfc.model.entity.StockMove;
import com.hei.app.kfc.model.enums.MoveType;

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
                resultSet.getTimestamp("createAt").toInstant(),
                resultSet.getInt("ingredientId"),
                resultSet.getInt("restaurantId"),
                MoveType.valueOf(resultSet.getString("type"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public StockMove getByIngredientId(Integer id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + getTableName() + 
                        " WHERE ingredientId = " + id +
                        " ORDER BY createAt ASC ;";

        try {
            connection = ConnectionDB.createConnection();
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return mapResultSetToEntity(resultSet);
            }
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
        return null;
    }
}
