package com.bank.account.services;

import com.bank.account.domain.User;

public interface UserService {

    User createUser(User user);

    User getUser(int userId);

}
