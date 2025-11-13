package com.paypal.wallet_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private  Long UserId;

    @Column(nullable = false,length = 3)
    private String currency ="INR";


    @Column(nullable = false)
    private Long balance;

    @Column(nullable = false)
    private Long availableBalance;

    @Column(nullable = false)
    private LocalDateTime createdAt= LocalDateTime.now();

    @Column(nullable = false)
    private  LocalDateTime updatedAt= LocalDateTime.now();



}
