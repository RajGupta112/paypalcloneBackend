package com.paypal.transaction_service.controller;

import com.paypal.transaction_service.dto.TransferRequest;
import com.paypal.transaction_service.entity.Transaction;
import com.paypal.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private  final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction){
        Transaction created= transactionService.createTransaction(transaction);
        return  ResponseEntity.ok(created);
    }


    @GetMapping("/all")
    public  ResponseEntity<List<Transaction>> getAll(){
        return ResponseEntity.ok(transactionService.getAllTransaction());
    }
}
