package com.spring.boot.springbootjpa.Service;

import com.spring.boot.springbootjpa.Model.Merchant;
import com.spring.boot.springbootjpa.Repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository merchantRepository;

    public void addMerchant(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    public List<Merchant> getMerchants() {
        return merchantRepository.findAll();
    }

    public Boolean updateMerchant(Integer merchantID, Merchant merchant) {
        Merchant oldMerchant = merchantRepository.getById(merchantID);

        if (oldMerchant == null) {
            return false;
        }

        oldMerchant.setName(merchant.getName());

        merchantRepository.save(oldMerchant);
        return true;

    }

    public Boolean deleteMerchant(Integer merchantID) {
        Merchant deleteMerchant = merchantRepository.getById(merchantID);

        if (deleteMerchant == null) {
            return false;
        }

        merchantRepository.delete(deleteMerchant);
        return true;

    }

    public Boolean checkAvailableMerchant(Integer merchantID){
        Merchant checkMerchant = merchantRepository.getById(merchantID);

        if (checkMerchant == null){
            return false;
        }

        return checkMerchant.getId().equals(merchantID); // always true?
    }
}
