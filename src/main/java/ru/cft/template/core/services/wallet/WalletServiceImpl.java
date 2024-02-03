package ru.cft.template.core.services.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.template.api.dto.wallet.request.HesoyamDTO;
import ru.cft.template.api.dto.wallet.response.GetHesoyamDTO;
import ru.cft.template.api.dto.wallet.response.GetWalletDTO;
import ru.cft.template.api.exceptions.UserNotFoundException;
import ru.cft.template.core.repositories.bill.BillRepository;
import ru.cft.template.core.repositories.user.UserRepository;
import ru.cft.template.core.repositories.wallet.WalletRepository;
import ru.cft.template.core.models.Bill;
import ru.cft.template.core.enums.TransactionType;
import ru.cft.template.core.models.User;
import ru.cft.template.core.models.Wallet;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    private static final int MAX_ATTEMPTS = 3;
    private static final int RETRY_DELAY_IN_SECONDS = 30;
    private final WalletRepository walletRepository;
    private final BillRepository billRepository;
    private final UserRepository userRepository;

//    @Value("${hesoyam.retry.maxAttempts}")
//    private Integer maxAttempts;
//
//    @Value("${hesoyam.retry.delayInSeconds}")
//    private Integer retryDelayInSeconds;

    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setAmount(0L);
        wallet.setLastUpdate(new Date());

        return walletRepository.save(wallet);
    }

    public GetWalletDTO getWallet(UUID id) {//todo: refactor for authorized users
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return mapWalletToDTO(user.getWallet());
    }

    private GetWalletDTO mapWalletToDTO(Wallet wallet) {
        GetWalletDTO walletDTO = new GetWalletDTO();
        walletDTO.setId(wallet.getId());
        walletDTO.setAmount(wallet.getAmount());
        walletDTO.setLastUpdate(wallet.getLastUpdate());

        return walletDTO;
    }

    public GetHesoyamDTO hesoyam(UUID userId, HesoyamDTO dto) {//todo: refactor for authorized
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Wallet wallet = user.getWallet();

        long amount = dto.getAmount();
        Bill bill = createBill(wallet, amount, user);

        return mapToHesoyamDTO(bill);
    }

    private GetHesoyamDTO mapToHesoyamDTO(Bill bill) {
        GetHesoyamDTO hesoyamDTO = new GetHesoyamDTO();
        hesoyamDTO.setBillId(bill.getId());
        hesoyamDTO.setAmount(bill.getAmount());

        return hesoyamDTO;
    }

    private Bill createBill(Wallet wallet, long amount, User user) {
        boolean success = false;

        Bill bill = new Bill();
        bill.setAmount(amount);
        bill.setTransactionDate(new Date());
        bill.setType(TransactionType.refill);
        bill.setUser(user);

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            success = tryHesoyam(wallet, amount, bill);

            if (success) {
                break;
            } else if (attempt < MAX_ATTEMPTS) {
                sleep(RETRY_DELAY_IN_SECONDS * 1000L);
            }
        }

        if (!success) {
            performGuaranteedHesoyam(wallet, amount, bill);
        }

        return billRepository.save(bill);
    }

    private boolean tryHesoyam(Wallet wallet, long amount, Bill bill) {

        if (Math.random() <= 0.25) {
            wallet.setAmount(wallet.getAmount() + amount);
            wallet.setLastUpdate(new Date());


            bill.setStatus("successful");
            return true;
        } else {
            bill.setStatus("failed");
            return false;
        }
    }

    private void performGuaranteedHesoyam(Wallet wallet, long amount, Bill bill) {

        wallet.setAmount(wallet.getAmount() + amount);
        wallet.setLastUpdate(new Date());

        bill.setStatus("successful");
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
