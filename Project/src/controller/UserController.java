package controller;

import model.DatabaseCommunicator;
import model.Recipe;
import view.ClientConnection;
import model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
    public UserController(DatabaseCommunicator databaseCommunicator, DatabaseController databaseController) {
        this.databaseController = databaseController;
        this.databaseCommunicator = databaseCommunicator;
        users = new HashMap<>();
    }

    /**
     * This method returns the values in the {@link HashMap} containing {@link User} as {@link ArrayList}
     * @return A list of users
     * @author Anton Persson
     */
    public ArrayList<User> getAllUsers() {
        setUsers();
        Collection<User> userCollection = users.values();
        return new ArrayList<>(userCollection);
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
            databaseController.addUser(user);
        } catch (Exception e) {
            System.out.println("addUser fel");
        }
        System.out.println("Hit fungerar det");
    }
    public ArrayList<Recipe> getUserFavoriteRecipes(User user){
        ArrayList<Recipe> recipes = databaseController.getFavoriteRecipes(user);
        return recipes;
    }
    public ArrayList<Recipe> getOwnRecipes(User user){
        ArrayList<Recipe> recipes = databaseController.getOwnRecipes(user);
        return recipes;
    }
    public void addFavoriteRecipe(Recipe recipe, User user){
        databaseController.addFavoriteRecipe(user, recipe);
    }

}