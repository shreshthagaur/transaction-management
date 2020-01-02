package com.transfer.money.service;

import com.transfer.money.domain.Account;
import com.transfer.money.domain.TransferMoney;
import com.transfer.money.repository.TransferMoneyRepository;
import com.transfer.money.service.exception.TransferMoneyException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class TransferMoneyServiceTest {

    @Mock
    private TransferMoneyRepository transferMoneyRepository;

    @InjectMocks
    private TransferMoneyService transferMoneyService;

    private TransferMoney createDummyTransferOrder(){
        return new TransferMoney("123456789","678901234",BigDecimal.TEN);
    }

    @DisplayName("Should create a new account if account doesn't exist.")
    @Test
    public void shouldCreateNewAccount(){
        when(transferMoneyRepository.existsById(anyString())).thenReturn(false);
        String message = transferMoneyService.openAccount("123456789");
        assertAll(
                () -> assertEquals("Account opened successfully", message)
        );
    }

    @Test
    @DisplayName("Should not create a new account when account already exists")
    public void shouldNotCreateAnAccountWhenAccountExists(){
        when(transferMoneyRepository.existsById(anyString())).thenReturn(true);
        assertThrows(TransferMoneyException.class, () -> {
            transferMoneyService.openAccount("123456789");
        },"Account already exists!.");
    }

    @Test
    @DisplayName("Should throw exception when debit or credit accounts are not already available")
    public void shouldThrowExceptionWhenTransferAccountNotExists(){
        when(transferMoneyRepository.existsById(anyString())).thenReturn(false);
        assertThrows(TransferMoneyException.class, () -> {
            transferMoneyService.transferAmount(createDummyTransferOrder());
        },"Transfer cannot be initiated. Debit or Credit Account doesn't exist");
    }

    @Test
    @DisplayName("Should throw exception when debit account don't have enough funds")
    public void shouldThrowExceptionWhenAccountNotHaveEnoughFunds(){
        Optional<Account> optionalAccount = Optional.of(new Account("1234567",BigDecimal.ONE));

        when(transferMoneyRepository.existsById(anyString())).thenReturn(true);
        when(transferMoneyRepository.findById(anyString())).thenReturn(optionalAccount);
        assertThrows(TransferMoneyException.class, () -> {
            transferMoneyService.transferAmount(createDummyTransferOrder());
        },"Transfer cannot be initiated. Beneficiary account doesn't have enough balance.");
    }

    @Test
    @DisplayName("Should initiate transfer successfully.")
    public void shouldInitiateTransferSuccess(){
        Optional<Account> optionalAccount = Optional.of(new Account("1234567",new BigDecimal(11)));

        when(transferMoneyRepository.existsById(anyString())).thenReturn(true);
        when(transferMoneyRepository.findById(anyString())).thenReturn(optionalAccount);
        when(transferMoneyRepository.save(any())).thenReturn(any());
        transferMoneyService.transferAmount(createDummyTransferOrder());
    }
}