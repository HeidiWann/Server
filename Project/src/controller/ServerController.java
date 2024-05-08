package controller;

import model.DatabaseCommunicator;
import view.ClientConnection;
import java.sql.SQLException;

/**
 * Class that starts the whole server. This includes all controllers.
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class ServerController {
    private DatabaseController databaseController;
    private RecipeController recipeController;
    private UserController userController;
    private ConnectionController connectionController;
    private DatabaseCommunicator databaseCommunicator;
    private ClientConnection clientConnection;


    /**
     * Clas constructor. It starts a few controllers
     *
     * @author Anton Jansson
     * @author Heidi Wännman
     */
    public ServerController() throws SQLException {
        this.databaseCommunicator = new DatabaseCommunicator();
        databaseCommunicator.getDatabaseconnection();
        this.databaseController = new DatabaseController(databaseCommunicator);
        this.userController = new UserController(databaseCommunicator);
        this.recipeController = new RecipeController(databaseCommunicator);
        this.connectionController = new ConnectionController(userController, recipeController);
    }
}




