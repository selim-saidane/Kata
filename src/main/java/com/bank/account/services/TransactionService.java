package com.bank.account.services;

import com.bank.account.domain.Transaction;
import com.bank.account.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Set;

public interface TransactionService {

    Set<Transaction> getHistoric(int userId);

    Transaction createTransaction(BigDecimal balance, BigDecimal amount, TransactionType transactionType);

}
