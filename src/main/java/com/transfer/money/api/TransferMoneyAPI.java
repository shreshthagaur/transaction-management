package com.transfer.money.api;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.transfer.money.domain.TransferMoney;
import com.transfer.money.service.ITransferMoney;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RestController
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Slf4j
public class TransferMoneyAPI {

    @Autowired
    ITransferMoney transferMoneyService;

    @PostMapping("/transfer")
    public String initiateTransferMoney(@RequestBody final TransferMoney transferMoney){
        log.info("Begin transfer of money between parties.");
        log.debug("Account to be credited : {} " , transferMoney.getCreditAccount());
        log.debug("Account to be debited : {} " , transferMoney.getDebitAccount());
        return transferMoneyService.transferAmount(transferMoney);
    }

    @PostMapping("/create")
    public String initiateAccountOpening(@RequestBody String accountNumber){
        log.info("Begin : Open new account.");
        log.debug("Account to open {}" , accountNumber);
        return transferMoneyService.openAccount(accountNumber);
    }
}
