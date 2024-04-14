package model;

/**
 * Clas that represents users
 *
 * @author Heidi Wännman
 */
public class User {
    private int userID;
    private String userName;

    private String password;
    private String role;

    public User() {
    }

    public User(int userID, String userName,  String password) {
    }

    public String passwordHandler() {

        return password;
    }

    public void changePassword() {

    }

    public boolean passwordCheck() {

        return true;

    }

    public String getUsername() {

        return userName;
    }

    public String getPassword() {

        return password;
    }

    public int getId() {
        return userID;
    }
}
