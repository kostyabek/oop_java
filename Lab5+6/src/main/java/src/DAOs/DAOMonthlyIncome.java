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
import src.DateParticipant;
import src.DateTimeUtilities;
import src.Participant;
import src.Regions;

public class DAOMonthlyIncome implements DAO<DateParticipant> {

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
    public DateParticipant create(DateParticipant entity) throws SQLException {
        Participant participant = entity.getParticipant();

        String SQL = "INSERT INTO monthly_income VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        preparedStatement.setString(1, entity.getDateString());
        preparedStatement.setString(2, participant.getId());
        preparedStatement.setString(3, participant.firstName);
        preparedStatement.setString(4, participant.lastName);
        preparedStatement.setString(5, participant.patronymic);
        preparedStatement.setString(6, participant.region.toString());
        preparedStatement.setString(7, String.valueOf(participant.currentMonthlyIncome));

        preparedStatement.execute();

        return entity;
    }

    @Override
    public List<DateParticipant> getById(String participantId) throws SQLException {
        String SQL = String.format("SELECT * FROM monthly_income WHERE participantId = \'%s\'", participantId);
        Statement statement = connection.createStatement();
        List<DateParticipant> resultList = new ArrayList<>();
        boolean isQueryResultNotEmpty = statement.execute(SQL);
        if (isQueryResultNotEmpty) {
            return packQueriedIntoList(statement);
        }
        return resultList;
    }

    private List<DateParticipant> packQueriedIntoList(Statement statement) throws SQLException {
        List<DateParticipant> queried = new ArrayList<>();

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next()) {
            Participant participant = new Participant(
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("patronymic"),
                    Regions.valueOf(resultSet.getString("region")),
                    resultSet.getString("participantId"),
                    Double.valueOf(resultSet.getString("income")));
            
            Calendar date = DateTimeUtilities.convertStringDateToCalendar(resultSet.getString("date"));
            
            queried.add(new DateParticipant(date, participant));
        }
        return queried;
    }

    @Override
    public List<DateParticipant> getAll() throws SQLException {
        String SQL = "SELECT * FROM monthly_income";
        Statement statement = connection.createStatement();

        boolean isQueryResultNotEmpty = statement.execute(SQL);
        if (isQueryResultNotEmpty) {
            return packQueriedIntoList(statement);
        }
        return Collections.emptyList();
    }
}
