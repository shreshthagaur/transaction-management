package com.transfer.money.service.exception;

public class TransferMoneyException extends RuntimeException {

    public TransferMoneyException(final String errorDescription){
        super(errorDescription);
    }
}
