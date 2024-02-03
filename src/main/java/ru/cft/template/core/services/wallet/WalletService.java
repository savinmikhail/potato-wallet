package ru.cft.template.core.services.wallet;

import ru.cft.template.api.dto.wallet.request.HesoyamDTO;
import ru.cft.template.api.dto.wallet.response.GetWalletDTO;
import ru.cft.template.api.dto.wallet.response.GetHesoyamDTO;
import ru.cft.template.core.models.Wallet;

import java.util.UUID;

public interface WalletService {

    Wallet createWallet();
    GetWalletDTO getWallet(UUID id);
    GetHesoyamDTO hesoyam(UUID id, HesoyamDTO dto);
}
