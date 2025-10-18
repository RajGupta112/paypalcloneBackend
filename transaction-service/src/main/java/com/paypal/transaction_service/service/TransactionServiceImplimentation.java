package com.paypal.transaction_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.transaction_service.entity.Transaction;
import com.paypal.transaction_service.kafka.KafkaEventProducer;
import com.paypal.transaction_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImplimentation implements TransactionService {

    private final TransactionRepository transactionRepository;

    private  final ObjectMapper objectMapper;

    private final KafkaEventProducer kafkaEventProducer;

    @Override
    public Transaction createTransaction(Transaction request) {
        Long senderId = request.getSenderId();
        Long receiverId= request.getReceiverId();
        Double amount= request.getAmount();

        Transaction transaction= new Transaction();
        transaction.setSenderId(senderId);
        transaction.setReceiverId(receiverId);
        transaction.setAmount(amount);
        transaction.setTimeStamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");

        System.out.println("Incoming Transaction obejct:"+transaction);
        Transaction saved= transactionRepository.save(transaction);
        System.out.println("Saved Transaction from DB:"+saved);

        try{
//          String evenPayload= objectMapper.writeValueAsString(saved);
          String key= String.valueOf(saved.getId());
          kafkaEventProducer.sendTransactionEvent(key,saved);
            System.out.println("Kafka message sent");
        } catch (Exception e) {
            System.out.println("Failed to send Kafka event"+e.getMessage());
            e.printStackTrace();
        }

        return  saved;
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }
}
