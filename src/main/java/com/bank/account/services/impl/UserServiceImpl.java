package com.bank.account.services.impl;

import com.bank.account.domain.Account;
import com.bank.account.domain.User;
import com.bank.account.exceptions.AccountException;
import com.bank.account.services.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    private final HashMap<Integer, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            throw new AccountException("User name is mandatory");
        }

        //Creation of account for this user
        Account account = new Account();
        user.setAccount(account);

        //Saving user
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUser(int userId) {
        User user = users.get(userId);
        if (user != null) {
            return user;
        } else {
            throw new AccountException("User not found");
        }

    }

}
