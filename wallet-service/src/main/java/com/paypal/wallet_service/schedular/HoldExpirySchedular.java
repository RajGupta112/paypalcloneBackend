package com.paypal.wallet_service.schedular;

import com.paypal.wallet_service.entity.WalletHold;
import com.paypal.wallet_service.reository.WalletHoldRepository;
import com.paypal.wallet_service.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HoldExpirySchedular {

    private final WalletHoldRepository walletHoldRepository;

    private  final WalletService walletService;

    @Scheduled(fixedDelayString = "${wallet.hold.expiry.scan-rate-ms:60000")
    public  void expiredOldHolds(){
        LocalDateTime now= LocalDateTime.now();

        List<WalletHold> expired= walletHoldRepository.findByStatusAndExpiresAtBefore("ACTIVE",now);

        for(WalletHold hold:expired){
            String ref= hold.getHoldReference();
            try{
                walletService.releaseHold(ref);
                System.out.println("Expired hold released"+ref);
            }catch (Exception e){
                System.out.println(" Failed to release expired hold"+ref+ ":"+e.getMessage());
            }
        }
    }
}
