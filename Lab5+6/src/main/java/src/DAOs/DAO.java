package src.DAOs;

import java.sql.*;
import java.util.List;


public interface DAO<T> {

    Boolean setConnection(String url) throws SQLException;
    void closeConnection() throws SQLException;
    default T create(T entity) throws SQLException {
        return entity;
    }
    default boolean update(T entity) throws SQLException {
        return false;
    }
    default boolean delete(String id) throws SQLException {
        return false;
    }
    List<T> getById(String id) throws SQLException;
    List<T> getAll() throws SQLException;
}

