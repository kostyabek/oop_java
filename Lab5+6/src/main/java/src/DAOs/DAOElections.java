package src.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import src.DateTimeUtilities;
import src.ParticipantVote;

public class DAOElections implements DAO<ParticipantVote> {

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
    public ParticipantVote create(ParticipantVote entity) throws SQLException {
        String SQL = "INSERT INTO elections VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        
        String dateString = DateTimeUtilities.getDateAndTimeString(entity.getDateAndTime());
        
        preparedStatement.setString(1, dateString);
        preparedStatement.setString(2, entity.getVoterId());
        preparedStatement.setString(3, entity.getVotedForId());

        preparedStatement.execute();

        return entity;
    }

    @Override
    public boolean delete(String dateTime) throws SQLException {
        String SQL = String.format("DELETE FROM elections WHERE dateTime = \'%s\'", dateTime);
        Statement statement = connection.createStatement();
        statement.executeUpdate(SQL);
        return true;
    }

    @Override
    public List<ParticipantVote> getById(String dateTime) throws SQLException {
        String SQL = "SELECT * FROM elections WHERE dateTime LIKE " + "\'%" + dateTime + "%\'";
        Statement statement = connection.createStatement();
        List<ParticipantVote> resultList = new ArrayList<>();
        boolean isQueryResultNotEmpty = statement.execute(SQL);
        if (isQueryResultNotEmpty) {
            return packQueriedIntoList(statement);
        }
        return resultList;
    }

    private List<ParticipantVote> packQueriedIntoList(Statement statement) throws SQLException {
        List<ParticipantVote> queried = new ArrayList<>();

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next()) {
            
            Calendar date = DateTimeUtilities.convertStringDateToCalendar(resultSet.getString("dateTime"));
            
            queried.add(new ParticipantVote(
                    date,
                    resultSet.getString("voterId"),
                    resultSet.getString("votedForId")
            ));
        }
        return queried;
    }

    @Override
    public List<ParticipantVote> getAll() throws SQLException {
        String SQL = "SELECT * FROM elections";
        Statement statement = connection.createStatement();

        boolean isQueryResultNotEmpty = statement.execute(SQL);
        if (isQueryResultNotEmpty) {
            return packQueriedIntoList(statement);
        }
        return Collections.emptyList();
    }
}
