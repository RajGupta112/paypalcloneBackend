package com.paypal.reward_service;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private Long senderId;

    @Column(nullable = false)
    private  Long  receiverId;

    @Column(nullable = false)
    @Positive(message = "Amount must be positive")
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    @Column(nullable = false)
    private  String status;


    @PrePersist
    public  void prePersist(){
        if(timeStamp==null){
            timeStamp=LocalDateTime.now();
        }
        if(status==null){
            status="Pending";
        }
    }

}
