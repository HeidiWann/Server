package view;

import controller.ConnectionController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import static controller.Constants.*;


/**
 * This class holds the logic behind the communication of the Server and Client
 *
 * @author Salma Omar
 * @author Anton Persson
 */
public class ClientConnection implements Runnable {
    private final Socket socket;
    private final ConnectionController connectionController;
    private int intention;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private boolean listenForIntention;
    private boolean listenForObject;

    /**
     * Initiates the instance variables
     *
     * @param socket               {@link Socket} that enables communication between server and client
     * @param connectionController {@link ConnectionController} that processes the data gathered from the client
     * @throws IOException
     * @author Salma Omar
     */
    public ClientConnection(Socket socket, ConnectionController connectionController) throws IOException {
        this.socket = socket;
        this.connectionController = connectionController;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * This method first give values to the instance variables that are booleans. After that, while the thread isn't
     * interrupted, the method checks if the {@link ObjectInputStream} is available. If it is, the method proceeds to
     * either listen for an intention or an object and send them to the {@link ConnectionController} for processing.
     * If the inputs stream isn't available, the thread sleeps for 100 milliseconds. Finally, the method closes the
     * connection
     *
     * @author Anton Persson
     * @author Salma Omar
     * @author Heidi Wänmann
     */
    @Override
    public void run() {
        try {
            listenForIntention = true;
            listenForObject = false;
            while (!Thread.currentThread().isInterrupted()) {
                if (ois.available() > OK) {
                    if (listenForIntention) {
                        int intention = ois.readInt();
                        this.intention = intention;
                        connectionController.revealClientIntention(this, intention);
                    }
                    if (listenForObject) {
                        Object objectFromClient = ois.readObject();
                        connectionController.packUpObject(this, intention, objectFromClient);
                    }
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        System.out.println("Blev fel på grund av hur sleep fungerar");
                    }
                }
            }
        } catch (IOException | SQLException e) {
            System.out.println("Something went wrong in the communication: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
        System.out.println("Connection was closed");
    }

    /**
     * This method sends an intention to the client by using {@link ObjectOutputStream}
     *
     * @param intention An int that tells the client what to do
     * @author Anton Persson
     */
    public void sendIntention(int intention) {
        try {
            oos.writeInt(intention);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Could not send the intention");
            throw new RuntimeException(e);
        }
    }

    /**
     * This method sends an Object to the client by using {@link ObjectOutputStream}
     *
     * @param object The {@link Object} to send
     * @author Anton Persson
     */
    public void sendObject(Object object) {
        try {
            oos.writeObject(object);
            oos.flush();
        } catch (Exception e) {
            System.out.println("Something went wrong when sending object " + e.getMessage());
        }
    }

    /**
     * This method sets the value of the boolean
     *
     * @param listenForIntention The value that is to be given to the boolean
     * @author Anton Persson
     */
    public void setListenForIntention(boolean listenForIntention) {
        this.listenForIntention = listenForIntention;
    }

    /**
     * This method sets the value of the boolean
     *
     * @param listenForObject The value that is to be given to the boolean
     * @author Anton Persson
     */
    public void setListenForObject(boolean listenForObject) {
        this.listenForObject = listenForObject;
    }
}