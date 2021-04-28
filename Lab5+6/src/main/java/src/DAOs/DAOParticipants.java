package src.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.Participant;
import src.Regions;

public class DAOParticipants implements DAO<Participant> {

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
    public Participant create(Participant entity) throws SQLException {
        String SQL = "INSERT INTO participants VALUES(?, ?, ?, ?, ?, 0)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, entity.getId());
        preparedStatement.setString(2, entity.firstName);
        preparedStatement.setString(3, entity.lastName);
        preparedStatement.setString(4, entity.patronymic);
        preparedStatement.setString(5, entity.region.toString());
        preparedStatement.execute();

        return entity;
    }

    @Override
    public boolean update(Participant entity) throws SQLException {
        String SQL = "UPDATE participants SET "
                + "firstName = ?, "
                + "lastName = ?, "
                + "patronymic = ?, "
                + "region = ?, "
                + "netWorth = ?"
                + " WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, entity.firstName);
        preparedStatement.setString(2, entity.lastName);
        preparedStatement.setString(3, entity.patronymic);
        preparedStatement.setString(4, entity.region.toString());
        preparedStatement.setString(5, String.valueOf(entity.netWorth));
        preparedStatement.setString(6, entity.getId());
        preparedStatement.execute();

        return true;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        String SQL = "DELETE FROM participants WHERE id = \'" + id + "\'";

        Statement statement = connection.createStatement();

        statement.executeUpdate(SQL);
        return true;
    }

    @Override
    public List<Participant> getById(String id) throws SQLException {
        String SQL = "SELECT * FROM participants WHERE id = \'" + id + "\'";
        Statement statement = connection.createStatement();
        List<Participant> resultList = new ArrayList<>();

        boolean isQueryResultNotEmpty = statement.execute(SQL);
        if (isQueryResultNotEmpty) {
            return packQueriedIntoList(statement);
        }
        return resultList;
    }

    public List<Participant> getByCriteria(String input, String field) throws SQLException {
        String SQL = "SELECT * FROM participants WHERE " + field + " LIKE " + "\'%" + input + "%\'";
        Statement statement = connection.createStatement();
        List<Participant> resultList = new ArrayList<>();

        boolean isQueryResultNotEmpty = statement.execute(SQL);
        if (isQueryResultNotEmpty) {
            return packQueriedIntoList(statement);
        }
        return resultList;
    }

    @Override
    public List<Participant> getAll() throws SQLException {
        String SQL = "SELECT * FROM participants";
        Statement statement = connection.createStatement();

        boolean isQueryResultNotEmpty = statement.execute(SQL);
        if (isQueryResultNotEmpty) {
            return packQueriedIntoList(statement);
        }
        return new ArrayList<>();
    }

    private List<Participant> packQueriedIntoList(Statement statement) throws SQLException {
        List<Participant> queried = new ArrayList<>();

        ResultSet resultSet = statement.getResultSet();

        int i = 0;
        while (resultSet.next()) {
            queried.add(new Participant(
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("patronymic"),
                    Regions.valueOf(resultSet.getString("region")),
                    resultSet.getString("id"),
                    Double.parseDouble(resultSet.getString("netWorth"))));
            i++;
        }
        return queried;
    }

}
