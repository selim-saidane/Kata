package com.bank.account.services.impl;

import com.bank.account.domain.Transaction;
import com.bank.account.domain.enums.TransactionType;
import com.bank.account.services.TransactionService;
import com.bank.account.services.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Service
public class TransactionServiceImpl implements TransactionService {

    private UserService userService;

    public TransactionServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Set<Transaction> getHistoric(int userId) {
        return userService.getUser(userId).getAccount().getTransactions();
    }

    @Override
    public Transaction createTransaction(BigDecimal balance, BigDecimal amount, TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDate.now());
        transaction.setAmount(amount);
        transaction.setBalance(getBalance(balance, amount, transactionType));
        transaction.setTransactionType(transactionType);
        return transaction;
    }

    private BigDecimal getBalance(BigDecimal balance, BigDecimal amount, TransactionType transactionType) {
        return transactionType.equals(TransactionType.INPUT) ? balance.add(amount) : balance.subtract(amount);
    }

}
