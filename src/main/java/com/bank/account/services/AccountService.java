package com.bank.account.services;

import java.math.BigDecimal;

public interface AccountService {


    void deposit(int userId, BigDecimal amount);

    void withdrawal(int userId, BigDecimal valueOf);
}
