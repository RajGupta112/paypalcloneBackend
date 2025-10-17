package com.paypal.transaction_service.service;

import com.paypal.transaction_service.entity.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    List<Transaction> getAllTransaction();

}
