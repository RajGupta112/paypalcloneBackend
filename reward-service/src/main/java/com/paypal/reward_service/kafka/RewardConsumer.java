package com.paypal.reward_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.reward_service.Transaction;
import com.paypal.reward_service.entity.Reward;
import com.paypal.reward_service.repository.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RewardConsumer {

      private  final RewardRepository rewardRepository;
      private final ObjectMapper objectMapper;

      @KafkaListener(topics = "txn-initiated",groupId = "reward-group")
      public void consumeTransaction(Transaction transaction){
           try{
                if(rewardRepository.existByTransactionId(transaction.getId())) {
                    System.out.println("Reward already exists for transaction" + transaction.getId());
                }else {
                    Reward reward= new Reward();
                    reward.setUserId(transaction.getSenderId());
                    reward.setPoints(transaction.getAmount() * 100);
                    reward.setSentAt(LocalDateTime.now());
                    reward.setTransactionId(transaction.getId());
                    rewardRepository.save(reward);
                    System.out.println("Reward saved"+reward);
                }
           }catch (Exception e){
               System.out.println("Failed to process transaction"+transaction.getId());
    throw e;
           }
      }
}
