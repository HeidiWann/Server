package model;



import javafx.scene.image.ImageView;
import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 222333444L;
    private int userID;
    private String userName;
    private String passWord;
    private ImageView profilePicture;

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public User(int userId, String username, String password) {
        this.userID = userId;
        this.userName = username;
        this.passWord = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setProfilePicture(ImageView profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPassword() {
        return passWord;
    }

    public ImageView getProfilePicture() {
        return profilePicture;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String toString(){
        return "UserId: " + userID + ", Username: " + userName + ", Userpassword: " + passWord;
    }
}