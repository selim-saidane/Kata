package com.bank.account.services;

import com.bank.account.domain.Account;
import com.bank.account.domain.Transaction;
import com.bank.account.domain.User;
import com.bank.account.exceptions.AccountException;
import com.bank.account.services.impl.AccountServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private UserService userService;

    @Mock
    private TransactionService transactionService;


    private User user;

    @Before
    public void init() {
        user = new User();
        user.setUserName("Selim");
        Account account = new Account();
        user.setAccount(account);
    }

    @Test
    public void userShouldBeAbleToMakeDepositOnHisAccount() {
        //Given
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.TEN);
        Mockito.when(userService.getUser(user.getId())).thenReturn(user);
        Mockito.when(transactionService.createTransaction(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(transaction);

        //When
        accountService.deposit(user.getId(), BigDecimal.valueOf(10));

        //Then
        assertEquals(BigDecimal.valueOf(10), user.getAccount().getBalance());
    }

    @Test
    public void userShouldBeAbleToWithdrawalMoney() {
        //Given
        Account account = new Account();
        account.setBalance(BigDecimal.TEN);
        user.setAccount(account);
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.TEN);
        Mockito.when(userService.getUser(user.getId())).thenReturn(user);
        Mockito.when(transactionService.createTransaction(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(transaction);

        //When
        accountService.withdrawal(user.getId(), BigDecimal.valueOf(10));

        //Then
        assertEquals(BigDecimal.ZERO, user.getAccount().getBalance());
    }

    @Test(expected = AccountException.class)
    public void userShouldNotBeAbleToWithdrawalAnAmountSuperiorThanActualBalance() {
        //Given
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.TEN);
        Mockito.when(userService.getUser(user.getId())).thenReturn(user);
        Mockito.when(transactionService.createTransaction(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(transaction);

        //When
        accountService.withdrawal(user.getId(), BigDecimal.valueOf(10));

    }

}
