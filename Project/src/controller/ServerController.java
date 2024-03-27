package controller;

/**
 * Class that starts the whole server. This includes all controllers mm
 */
public class ServerController {
    private DatabaseController dbController;
    private RecipeController recipeController;
    private UserController userController;
    private ConnectionController clientListener;

    public ServerController() {
        dbController = new DatabaseController();
        recipeController = new RecipeController(dbController);
        userController = new UserController(dbController);
    }

    public void startServer() {
        clientListener = new ConnectionController(this);

    }

    public void newConnection(ConnectionController.ClientHandler newClientHandler) {

    }
}




