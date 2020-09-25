package pojo;

public class LoginPayload {
    public String userName;
    public String password;

    public LoginPayload(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
