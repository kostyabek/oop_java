import java.sql.*;
import java.util.List;


public interface DAO<T> {

    Connection setConnection(String url) throws SQLException;
    void closeConnection() throws SQLException;
    T create(T entity) throws SQLException;
    boolean update(T entity) throws SQLException;
    boolean delete(int id) throws SQLException;
    List<T> findById(int id) throws SQLException;
    List<T> getAll() throws SQLException;
}
