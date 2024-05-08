package controller;

import model.DatabaseCommunicator;
import view.ClientConnection;
import model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clas responsible for handling logic related to users
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class UserController {
    private DatabaseCommunicator databaseCommunicator;
    private DatabaseController databaseController;
    private HashMap<String, User> users;

    /**
     * Clas constructor
     * @param databaseCommunicator
     * @author Anton Jansson
     */
    public UserController(DatabaseCommunicator databaseCommunicator) {
        this.databaseCommunicator = databaseCommunicator;
        users = new HashMap<>();
    }

    /**
     * This method returns the values in the {@link HashMap} containing {@link User} as {@link ArrayList}
     * @return A list of users
     * @author Anton Persson
     */
    public ArrayList<User> getAllUsers() {
        return (ArrayList<User>) users.values();
    }

    /**
     * This method creates an {@link ArrayList} and then stores every {@link User} that is in the database in it.
     * The method then loops through every User in the list and puts the Users userName as key and the Users as value
     * in a {@link HashMap}
     *
     * @author Anton Persson
     */
    public void setUsers() {
        ArrayList<User> listOfUsers = new ArrayList<>();
        try {
            listOfUsers = databaseController.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (User user : listOfUsers) {
            String userName = user.getUserName();
            users.put(userName, user);
        }
    }

    /**
     * This method adds a {@link User} to the database by calling a method in {@link DatabaseController}
     * @param user that is to be added to the database
     * @author Anton Persson
     */
    public void addUser(User user) {
        String userName = user.getUserName();
        String password = user.getPassword();

        users.put(userName, user);
        try {
            databaseController.addUser(userName, password);
        } catch (Exception e) {

        }
    }
}