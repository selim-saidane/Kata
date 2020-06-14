package com.bank.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Request implements Serializable {

    private int userId;

    private BigDecimal amount;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
