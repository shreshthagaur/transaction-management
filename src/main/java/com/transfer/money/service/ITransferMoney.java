package com.transfer.money.service;

import com.transfer.money.domain.TransferMoney;

public interface ITransferMoney {

    String transferAmount(final TransferMoney transferMoneyAttributes);

    String openAccount(final String accountNumber);
}
