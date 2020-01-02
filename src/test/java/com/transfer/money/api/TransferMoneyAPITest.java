package com.transfer.money.api;

import com.transfer.money.domain.TransferMoney;
import com.transfer.money.service.TransferMoneyService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@WebMvcTest(TransferMoneyAPI.class)
public class TransferMoneyAPITest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransferMoneyService transferMoneyService;

    @Test
    @DisplayName("Should open account successfully.")
    public void shouldOpenAccountSuccessfully() throws Exception{
        when(transferMoneyService.openAccount(anyString())).thenReturn("Account opened successfully");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/create")
                .accept(APPLICATION_JSON).content("{\n" +
                        "\t\"accountNumber\":\"0503368545\n" +
                        "}")
                .contentType(APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    @DisplayName("Should initiate money transfer successfully")
    public void shouldInitiateMoneyTransfer() throws Exception{
        when(transferMoneyService.transferAmount(createDummyTransferOrder())).thenReturn("Transfer done successfully!");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/transfer")
                .accept(APPLICATION_JSON).content("{\n" +
                        "\t\"creditAccount\":\"0503368545\",\n" +
                        "\t\"debitAccount\":\"0503368546\",\n" +
                        "\t\"amount\":3.4\n" +
                        "}")
                .contentType(APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private TransferMoney createDummyTransferOrder(){
        return new TransferMoney("123456789","678901234",BigDecimal.TEN);
    }
}