package com.paypal.wallet_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {

    private Long id;
    private Long userId;
    private String currency;
    private Long balance;
    private Long availableBalance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
