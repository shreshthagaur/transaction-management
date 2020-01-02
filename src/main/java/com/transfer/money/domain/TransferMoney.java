package com.transfer.money.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonPropertyOrder({"creditAccount","debitAccount","amount"})
public class TransferMoney {

    @JsonProperty(value = "creditAccount" , required = true)
    private String creditAccount;

    @JsonProperty(value = "debitAccount" , required = true)
    private String debitAccount;

    @JsonProperty(value = "amount" , required = true)
    private BigDecimal amount;

    @JsonCreator
    public TransferMoney(final String creditAccount , final String debitAccount , final BigDecimal amount){
        this.creditAccount = creditAccount;
        this.debitAccount = debitAccount;
        this.amount =amount;
    }
}
