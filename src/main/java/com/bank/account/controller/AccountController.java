package com.bank.account.controller;


import com.bank.account.domain.Request;
import com.bank.account.domain.Transaction;
import com.bank.account.services.AccountService;
import com.bank.account.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/v1/bank/")
public class AccountController {

    private AccountService accountService;
    private TransactionService transactionService;

    AccountController(AccountService accountService,
                      TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/account/deposit")
    public ResponseEntity deposit(@RequestBody Request request) {
        accountService.deposit(request.getUserId(), request.getAmount());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/account/withdrawal")
    public ResponseEntity withdrawal(@RequestBody Request request) {
        accountService.deposit(request.getUserId(), request.getAmount());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/account/transactions/{userId}")
    public ResponseEntity<Set<Transaction>> deposit(@PathVariable int userId) {
        return ResponseEntity.ok(transactionService.getHistoric(userId));
    }

}
