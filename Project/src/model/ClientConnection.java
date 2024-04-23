package model;

import controller.ConnectionController;
import controller.DatabaseController;
import controller.RecipeController;
import controller.UserController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import static controller.Constans.OK;


/**
 * This class manages connections form clients by setting up a listener.
 * It lets the ConnectionController deal with handling these connections.
 *
 * @author Salma Omar
 */

public class ClientConnection implements Runnable {

    private Socket socket;
    private ConnectionController connectionController;
    private DatabaseController databaseController;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private UserController userController;
    private RecipeController recipeController;

    /**
     * Creates a new ClientConnection, passing in the ConnectionsController to handle incoming connections.
     *
     * @param connectionController the ConnectionController responsible for managing incoming client connections.
     */
    public ClientConnection(Socket socket, ConnectionController connectionController) throws IOException {
        this.socket = socket;
        this.connectionController = connectionController;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

    }
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (ois.available() > 0) {
                    int intention = ois.readInt();
                    Object object = ois.readObject();
                    if (object instanceof User) {
                        databaseController.checkUserExists("userName");
                        databaseController.register((User) object);
                    } else if (object instanceof Recipe) {
                        handleTheRecipes();
                    }
                    connectionController.revealClientIntention(intention, this, object);
                } else {

                    Thread.sleep(100);
                }
            }
        } catch (IOException | SQLException e) {
            System.out.println("Something went wrong in the communication: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted");
        } finally {
            closeConnection();
        }
    }
    public void closeConnection() {
        try {
            oos.close();
            ois.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendIntention(int intention) {
        try {
            oos.writeInt(intention);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Could not send the intention");
            throw new RuntimeException(e);
        }
    }
    public ObjectInputStream getObjectInputStream() {
        return ois;
    }
    public ObjectOutputStream getObjectOutputStream() {
        return oos;
    }
    private void handleReceivingUser() throws IOException, ClassNotFoundException, SQLException {
        User user = (User) ois.readObject();
        userController.getNewUserInfo();
    }
    private void handleTheRecipes() throws IOException, ClassNotFoundException {
        Recipe recipe = (Recipe) ois.readObject();
        recipeController.sendRecipe(oos, recipe);
    }


}

