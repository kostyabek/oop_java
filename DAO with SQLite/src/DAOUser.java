import java.sql.*;
import java.util.ArrayList;
import java.util.List;


class DAOUser implements DAO<User> {
    Connection connection;

    @Override
    public Connection setConnection(String url) throws SQLException {
        connection = DriverManager.getConnection(url);
        return connection;
    }

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public User create(User entity) throws SQLException {
        String SQL = "INSERT INTO users(firstName, lastName, salary) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, entity.getFirstName());
        preparedStatement.setString(2, entity.getLastName());
        preparedStatement.setString(3, String.valueOf(entity.getSalary()));
        preparedStatement.execute();

        SQL = "SELECT MAX(id) FROM users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        entity.setId(resultSet.getInt(1));
        return entity;
    }

    @Override
    public boolean update(User entity) throws SQLException {
        String SQL = "UPDATE users SET firstName = ?, lastName = ?, salary = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, entity.getFirstName());
        preparedStatement.setString(2, entity.getLastName());
        preparedStatement.setString(3, String.valueOf(entity.getSalary()));
        preparedStatement.setString(4, String.valueOf(entity.getId()));
        preparedStatement.execute();

        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String SQL = "DELETE FROM users WHERE id = " + id;

        Statement statement = connection.createStatement();

        statement.executeUpdate(SQL);
        return true;
    }

    @Override
    public List<User> findById(int id) throws SQLException {
        String SQL = "SELECT * FROM users WHERE id = " + id;
        Statement statement = connection.createStatement();

        boolean isQueryResultNotEmpty = statement.execute(SQL);
        if (isQueryResultNotEmpty) {
            return packQueriedUsersIntoList(statement);
        }
        return new ArrayList<>();
    }

    @Override
    public List<User> getAll() throws SQLException {
        String SQL = "SELECT * FROM users";
        Statement statement = connection.createStatement();

        boolean isQueryResultNotEmpty = statement.execute(SQL);
        if (isQueryResultNotEmpty) {
            return packQueriedUsersIntoList(statement);
        }
        return new ArrayList<>();
    }

    private List<User> packQueriedUsersIntoList(Statement statement) throws SQLException {
        List<User> queriedUsers = new ArrayList<>();

        ResultSet resultSet = statement.getResultSet();

        int i = 0;
        while(resultSet.next()) {
            queriedUsers.add(new User(resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getDouble("salary")));
            queriedUsers.get(i).setId(resultSet.getInt("id"));
            i++;
        }
        return queriedUsers;
    }
}