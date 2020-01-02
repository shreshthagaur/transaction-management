package com.transfer.money;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.transfer.money"})
public class TransferMoneyApplication {

    public static void main(String args[]){
        SpringApplication.run(TransferMoneyApplication.class);
    }
}
