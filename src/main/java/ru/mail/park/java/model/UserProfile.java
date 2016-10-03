package ru.mail.park.java.model;

/**
 * Created by Дмитрий on 03.10.2016.
 */

public class UserProfile {
    private String login;
    private String email;
    private String password;

    public UserProfile(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
