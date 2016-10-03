package ru.mail.park.java.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.mail.park.java.model.UserProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Дмитрий on 03.10.2016.
 */

@Service
public class AccountService {
    private Map<String, UserProfile> userNameToUser = new HashMap<>();

    public UserProfile addUser(String login, String password, String email) {
        final UserProfile userProfile = new UserProfile(login, email, password);
        userNameToUser.put(login, userProfile);
        return userProfile;
    }

    public UserProfile getUser(String login) {
        return userNameToUser.get(login);
    }

}