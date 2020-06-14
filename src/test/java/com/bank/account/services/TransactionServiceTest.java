package com.bank.account.services;

import com.bank.account.domain.Account;
import com.bank.account.domain.Transaction;
import com.bank.account.domain.User;
import com.bank.account.domain.enums.TransactionType;
import com.bank.account.services.impl.TransactionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private UserService userService;

    private User user;

    @Before
    public void init() {
        user = new User();
        user.setUserName("Selim");
        Account account = new Account();
        user.setAccount(account);
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.TEN);
        transaction.setDate(LocalDate.now());
        account.getTransactions().add(transaction);
    }


    @Test
    public void userShouldBeAbleToSeeHistoricOfTransactions() {
        //Given
        Mockito.when(userService.getUser(user.getId())).thenReturn(user);

        //When
        Set<Transaction> transactions = transactionService.getHistoric(user.getId());

        //Then
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
        Iterator iterator = transactions.iterator();
        Transaction firstTransaction = (Transaction) iterator.next();
        assertEquals(BigDecimal.TEN,firstTransaction.getAmount());
        assertEquals(LocalDate.now(),firstTransaction.getDate());
    }

    @Test
    public void transactionShouldBeCreated() {
        //When
        Transaction transaction = transactionService.createTransaction(BigDecimal.TEN, BigDecimal.ONE, TransactionType.INPUT);

        //Then
        assertNotNull(transaction);
        assertEquals(BigDecimal.ONE,transaction.getAmount());
        assertEquals(BigDecimal.valueOf(11),transaction.getBalance());
        assertEquals(TransactionType.INPUT, transaction.getTransactionType());
        assertEquals(LocalDate.now(),transaction.getDate());
    }
}
