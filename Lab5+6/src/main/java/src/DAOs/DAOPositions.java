package src.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.PositionParticipant;

public class DAOPositions implements DAO<PositionParticipant> {

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
    public boolean update(PositionParticipant entity) throws SQLException {
        String SQL = "UPDATE positions SET participantId = ? WHERE position = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, entity.getParticipantId());
        preparedStatement.setString(2, entity.getPosition());

        preparedStatement.execute();
        return true;
    }

    @Override
    public List<PositionParticipant> getById(String position) throws SQLException {
        String SQL = String.format("SELECT * FROM positions WHERE position = %s", position);
        Statement statement = connection.createStatement();

        List<PositionParticipant> resultList = new ArrayList<>();

        boolean isQueryResultNotEmpty = statement.execute(SQL);
        if (isQueryResultNotEmpty) {
            return packQueriedIntoList(statement);
        }
        return resultList;
    }

    @Override
    public List<PositionParticipant> getAll() throws SQLException {
        String SQL = "SELECT * FROM positions";
        Statement statement = connection.createStatement();

        boolean isQueryResultNotEmpty = statement.execute(SQL);
        if (isQueryResultNotEmpty) {
            return packQueriedIntoList(statement);
        }
        return new ArrayList<>();
    }

    private List<PositionParticipant> packQueriedIntoList(Statement statement) throws SQLException {
        List<PositionParticipant> queried = new ArrayList<>();

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next()) {
            queried.add(new PositionParticipant(
                    resultSet.getString("position"),
                    resultSet.getString("participantId")));
        }
        return queried;
    }
}
