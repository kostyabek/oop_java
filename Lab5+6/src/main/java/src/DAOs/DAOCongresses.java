package src.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DAOCongresses implements DAO<String> {

    private Connection connection;

    @Override
    public Boolean setConnection(String url) throws SQLException {
        connection = DriverManager.getConnection(url);
        return true;
    }

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public String create(String date) throws SQLException {
        String SQL = "INSERT INTO congresses VALUES(?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(2, date);

        preparedStatement.execute();

        return date;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        try {
            int intId = Integer.valueOf(id);
            String SQL = String.format("DELETE FROM congresses WHERE dateTime = \'%s\'", intId);
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    @Override
    public List<String> getById(String id) throws SQLException {
        try {
            int intId = Integer.valueOf(id);
            String SQL = String.format("SELECT * FROM congresses WHERE id = \'%s\'", id);
            Statement statement = connection.createStatement();
            List<String> resultList = new ArrayList<>();
            boolean isQueryResultNotEmpty = statement.execute(SQL);
            if (isQueryResultNotEmpty) {
                return packQueriedIntoList(statement);
            }
            return resultList;
        } catch (NumberFormatException ex) {
            return Collections.emptyList();
        }
    }
    
    private List<String> packQueriedIntoList(Statement statement) throws SQLException {
        List<String> queried = new ArrayList<>();

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next()) {
            queried.add(resultSet.getString("dateTime"));
        }
        return queried;
    }

    @Override
    public List<String> getAll() throws SQLException {
        String SQL = "SELECT * FROM congresses";
        Statement statement = connection.createStatement();

        boolean isQueryResultNotEmpty = statement.execute(SQL);
        if (isQueryResultNotEmpty) {
            return packQueriedIntoList(statement);
        }
        return Collections.emptyList();
    }
}
