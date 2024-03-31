package model;

/**
 * Clas that represents users
 *
 * @author Heidi Wännman
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;

    public User() {
    }

    public String passwordHandler() {

        return password;
    }

    public void changePassword() {

    }

    public boolean passwordCheck() {

        return true;

    }
}
