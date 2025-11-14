package com.paypal.wallet_service.services;

import com.paypal.wallet_service.dto.CreateWalletRequest;
import com.paypal.wallet_service.dto.CreditRequest;
import com.paypal.wallet_service.dto.WalletResponse;
import com.paypal.wallet_service.entity.Transaction;
import com.paypal.wallet_service.entity.Wallet;
import com.paypal.wallet_service.exception.NotFoundException;
import com.paypal.wallet_service.reository.TransactionRepository;
import com.paypal.wallet_service.reository.WalletHoldRepository;
import com.paypal.wallet_service.reository.WalletRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletHoldRepository walletHoldRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request){
        Wallet wallet = new Wallet();
        wallet.setUserId(request.getUserId());
        wallet.setCurrency(request.getCurrency());
        Wallet saved = walletRepository.save(wallet);

        return new WalletResponse(
                saved.getId(),
                saved.getUserId(),
                saved.getCurrency(),
                saved.getBalance(),
                saved.getAvailableBalance(),saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }

    public WalletResponse credit(CreditRequest request){
        System.out.println("CREDIT request received :UserId="+ request.getUserId()
        + "amount="+request.getAmount()+ "currency="+ request.getCurrency());

        Wallet wallet= walletRepository.findByUserIdandCurrency(request.getUserId(), "INR").orElseThrow(()->
                new NotFoundException("Wallet not found for user:"+ request.getUserId()));

        wallet.setBalance(wallet.getBalance()+ request.getAmount());
        wallet.setAvailableBalance(wallet.getAvailableBalance()+ request.getAmount());
        Wallet saved= walletRepository.save(wallet);
        Long amount= request.getAmount();
        transactionRepository.save(new Transaction(wallet.getId(), "CREDIT",amount, "SUCESS"));

        System.out.println("CREDIT done : walletId"+ saved.getId()+ " newBalance="+saved.getBalance()+ "availablebalance="+ saved.getAvailableBalance())
        ;
        return  new WalletResponse(saved.getId(), saved.getUserId(), saved.getCurrency(),saved.getBalance(), saved.getAvailableBalance(), saved.getCreatedAt(),saved.getUpdatedAt());
    }
}
