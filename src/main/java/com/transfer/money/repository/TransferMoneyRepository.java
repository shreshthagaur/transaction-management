package com.transfer.money.repository;

import com.transfer.money.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferMoneyRepository extends JpaRepository<Account,String> {

}
