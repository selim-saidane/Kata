package com.bank.account.services.impl;

import com.bank.account.domain.Account;
import com.bank.account.domain.Transaction;
import com.bank.account.domain.User;
import com.bank.account.domain.enums.TransactionType;
import com.bank.account.exceptions.AccountException;
import com.bank.account.services.AccountService;
import com.bank.account.services.TransactionService;
import com.bank.account.services.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private UserService userService;

    private TransactionService transactionService;

    AccountServiceImpl(UserService userService,
                       TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @Override
    public void withdrawal(int userId, BigDecimal amountRequested) {
        User user = userService.getUser(userId);
        Account account = user.getAccount();
        if (amountRequested.compareTo(account.getBalance()) > 0) {
            throw new AccountException("The following amount is not authorized : " + amountRequested);
        }
        Transaction transaction = transactionService.createTransaction(account.getBalance(), amountRequested.abs(), TransactionType.OUTPUT);
        account.getTransactions().add(transaction);
        account.setBalance(account.getBalance().subtract(transaction.getAmount()));
    }

    @Override
    public void deposit(int userId, BigDecimal amountRequested) {
        User user = userService.getUser(userId);
        Account account = user.getAccount();
        Transaction transaction = transactionService.createTransaction(account.getBalance(), amountRequested.abs(), TransactionType.INPUT);
        account.getTransactions().add(transaction);
        account.setBalance(account.getBalance().add(transaction.getAmount()));
    }

}
