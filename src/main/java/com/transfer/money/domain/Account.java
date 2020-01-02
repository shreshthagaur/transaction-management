package com.transfer.money.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {

    @Id
    private String accountNumber;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    public Account(){
        super();
    }

    public Account(final String accountNumber , final BigDecimal amount){
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
}
