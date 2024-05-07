package model;
import java.io.Serial;
import java.io.Serializable;

/**
 * Clas that represents users
 *
 * @author Heidi Wännman
 */
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 222333444L;
    private int userID;
    private String userName;
    private String password;
    private String role;

    public User(int userID, String userName, String password, String role) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
