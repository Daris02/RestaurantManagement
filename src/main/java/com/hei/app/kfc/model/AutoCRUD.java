package com.hei.app.kfc.model;

import com.hei.app.kfc.config.ConnectionDB;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AutoCRUD<T, ID> implements CRUD<T, ID> {

    protected abstract String getTableName();

    protected abstract T mapResultSetToEntity(ResultSet resultSet);

    @Override
    public T getById(ID id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + getTableName() + " WHERE id = " + id + ";";

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

    @Override
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + getTableName();
        List<T> listAll = new ArrayList<>();

        try {
            connection = ConnectionDB.createConnection();
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listAll.add(mapResultSetToEntity(resultSet));
            }
            return listAll;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public T save(T toSave) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = ConnectionDB.createConnection();
            assert connection != null;
            statement = connection.createStatement();
            Class<?> currentClass = toSave.getClass();
            List<Field> fields = new ArrayList<>();
            
            fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
            
            while (currentClass.getSuperclass() != null) {
                currentClass = currentClass.getSuperclass();
                fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
            }
            
            Field[] allFields = fields.toArray(new Field[fields.size()]);

            StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + getTableName() + " (");
            for (Field field : allFields) {
                if (field.getName() == "id" ||
                    field.getName() == "menus" ||
                    field.getName() == "price" ||
                    field.getName() == "ingredients") continue;
                queryBuilder.append(field.getName()).append(", ");
            }
            queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
            queryBuilder.append(") VALUES ( ");

            for (Field field : allFields) {
                if (field.getName() == "id" ||
                    field.getName() == "menus" ||
                    field.getName() == "price" ||
                    field.getName() == "ingredients") continue;
                field.setAccessible(true);
                Object value = field.get(toSave);
                queryBuilder.append("'").append(value).append("', ");
            }
            queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
            queryBuilder.append(")");

            String insertQuery = queryBuilder.toString();
            statement.executeUpdate(insertQuery);
            return toSave;

        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public T getByIdRecent(ID id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + getTableName() + 
            " WHERE id = " + id + " " +
            " ORDER BY createAt DESC LIMIT 1;";

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

    public T getByName(String name) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + getTableName() + " WHERE name = '" + name + "';";

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
