import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        DAO<User> daoUser = new DAOUser();
        User user1 = new User("Kostya", "Biektin", 2650.67);
        User user2 = new User("John", "Smith", 1000);
        User user3 = new User("Rob", "Williams", 3257.02);
        User user4 = new User("Mark", "Polo", 1713);
        User user5 = new User("Harrison", "Ford", 4513.83);

        try {
            daoUser.setConnection("jdbc:sqlite:users.db");

            daoUser.create(user1);
            daoUser.create(user2);
            daoUser.create(user3);
            daoUser.create(user4);
            daoUser.create(user5);

            user5.setLastName("Dickson");
            daoUser.update(user5);

            daoUser.delete(user3.getId());

            List<User> queriedUsers = daoUser.findById(60);
            System.out.println("----------BY ID----------");
            for (User user: queriedUsers) {
                System.out.println(user);
            }
            System.out.println();

            System.out.println("----------GET ALL----------");
            queriedUsers = daoUser.getAll();
            for (User user: queriedUsers) {
                System.out.println(user);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        } finally {
            try {
                daoUser.closeConnection();
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
