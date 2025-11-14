package com.paypal.wallet_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWalletRequest {

    @NotNull(message = "UserId is required")
    private Long userId;

    // Optional: allow user to choose currency, default handled in service.
    private String currency = "INR";

    @NotNull(message = "Initial balance is required")
    @Positive(message = "Balance must be a positive amount")
    private Long balance;
}
