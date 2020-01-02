package com.transfer.money.service;

import com.transfer.money.domain.Account;
import com.transfer.money.domain.TransferMoney;
import com.transfer.money.repository.TransferMoneyRepository;
import com.transfer.money.service.exception.TransferMoneyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class TransferMoneyService implements ITransferMoney {

    final TransferMoneyRepository transferMoneyRepository;

    public TransferMoneyService(final  TransferMoneyRepository transferMoneyRepository){
        this.transferMoneyRepository = transferMoneyRepository;
    }

    @Override
    public String transferAmount(final TransferMoney transferMoneyAttributes) {
        log.debug("Transfer initiated between parties with account numbers , Debit  &  Credit : {} " , transferMoneyAttributes.getDebitAccount() , transferMoneyAttributes.getCreditAccount());
        if(!(checkIfAccountExists(transferMoneyAttributes.getCreditAccount()) && checkIfAccountExists(transferMoneyAttributes.getDebitAccount())))
            throw new TransferMoneyException("Transfer cannot be initiated. Debit or Credit Account doesn't exist");

        //Debit from debit account.
        Account debitAccount = fetchAccount(transferMoneyAttributes.getDebitAccount());
        log.debug(" Debit Account - Amount {} " , debitAccount.getAmount());
        if(validateDebit(debitAccount,transferMoneyAttributes.getAmount()))
        transferMoneyRepository.save(new Account(debitAccount.getAccountNumber(),debitAccount.getAmount().subtract(transferMoneyAttributes.getAmount())));


        //Credit from credit account.
        Account creditAccount = fetchAccount(transferMoneyAttributes.getCreditAccount());
        log.debug("Credit Account - Amount {} " , creditAccount.getAmount());
        transferMoneyRepository.save(new Account(creditAccount.getAccountNumber(),creditAccount.getAmount().add(transferMoneyAttributes.getAmount())));

        return "Transfer done successfully!";
    }

    @Override
    public String openAccount(final String accountNumber) {

        if(checkIfAccountExists(accountNumber))
            throw new TransferMoneyException("Account already exists!.");

        Account newAccount = new Account(accountNumber,BigDecimal.ZERO);

        transferMoneyRepository.save(newAccount);

        return "Account opened successfully";
    }


    private boolean checkIfAccountExists(final String accountNr){
        log.info("Check Account Nr : {} ",accountNr);
        return transferMoneyRepository.existsById(accountNr);
    }

    private boolean validateDebit(final Account account, final BigDecimal amount){
       if(account.getAmount().compareTo(amount) == -1)
            throw new TransferMoneyException("Transfer cannot be initiated. Beneficiary account doesn't have enough balance.");

       return true;
    }

    private Account fetchAccount(final String accountNr){
        Optional<Account> optionalAccount = transferMoneyRepository.findById(accountNr);
        if(!optionalAccount.isPresent())
           throw new TransferMoneyException("Account not available.");
        return optionalAccount.get();
    }
}
