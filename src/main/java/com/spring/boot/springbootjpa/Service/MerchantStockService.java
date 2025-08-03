package com.spring.boot.springbootjpa.Service;

import com.spring.boot.springbootjpa.Model.MerchantStock;
import com.spring.boot.springbootjpa.Model.Product;
import com.spring.boot.springbootjpa.Model.User;
import com.spring.boot.springbootjpa.Repository.MerchantStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    private final MerchantStockRepository merchantStockRepository;

    private final ProductService productService;
    private final MerchantService merchantService;
    private final UserService userService;

    public Boolean addMerchantStock(MerchantStock merchantStock) {
        if (productService.checkAvailableProduct(merchantStock.getProductID())
                && merchantService.checkAvailableMerchant(merchantStock.getMerchantID())) {

            merchantStockRepository.save(merchantStock);
            return true; // check if both product and merchant exist.
        }

        return false; // one or both of them do not exist
    }

    public List<MerchantStock> getMerchantStocks(){
        return merchantStockRepository.findAll();
    }

    public Boolean updateMerchantStock(Integer merchantStockID, MerchantStock merchantStock){
        if (!productService.checkAvailableProduct(merchantStock.getProductID())
                || !merchantService.checkAvailableMerchant(merchantStock.getMerchantID())) {
            // if any of the ID's do not exist, then return false.
            return false;
        }

        MerchantStock oldMerchantStock = merchantStockRepository.getById(merchantStockID);

        if (oldMerchantStock == null){
            return false;
        }

        oldMerchantStock.setProductID(merchantStock.getProductID());
        oldMerchantStock.setMerchantID(merchantStock.getMerchantID());
        oldMerchantStock.setStock(merchantStock.getStock());

        merchantStockRepository.save(oldMerchantStock);
        return true;
    }

    public Boolean deleteMerchantStock(Integer merchantStockID){
        MerchantStock deleteMerchantStock = merchantStockRepository.getById(merchantStockID);

        if (deleteMerchantStock == null){
            return false;
        }

        merchantStockRepository.delete(deleteMerchantStock);
        return true;
    }

    public Boolean addMoreStockToProduct(Integer productID, Integer merchantID, Integer additionalStockAmount) {
        for (MerchantStock m : getMerchantStocks()) {
            if (m.getProductID().equals(productID)
                    && m.getMerchantID().equals(merchantID)) {

                m.setStock(m.getStock() + additionalStockAmount);
                merchantStockRepository.save(m);
                return true; //updated
            }
        }
        return false; // does not exist
    }

    public Boolean StockAvailable(Integer productID, Integer merchantID, Integer stockAmountToCheck) {
        for (MerchantStock m : getMerchantStocks()) {
            if (m.getProductID().equals(productID)
                    && m.getMerchantID().equals(merchantID)) {

                return m.getStock() >= stockAmountToCheck;
            }
        }
        return false; // the merchantStock does not exist
    }

    public Boolean removeStockFromProduct(Integer productID, Integer merchantID, Integer stockAmountToRemove) {
        for (MerchantStock m : getMerchantStocks()) {
            if (m.getProductID().equals(productID)
                    && m.getMerchantID().equals(merchantID)) {

                if (m.getStock() >= stockAmountToRemove) {
                    m.setStock(m.getStock() - stockAmountToRemove);
                    merchantStockRepository.save(m);
                    return true; //deducted from stock
                } else {
                    return false; // bad request, unavailable stock
                }
            }
        }
        return false; // the merchantStock does not exist
    }

    public Boolean buyProduct(Integer userID, Integer productID, Integer merchantID) {
        if (!userService.checkAvailableUser(userID)
                || !productService.checkAvailableProduct(productID)
                || !merchantService.checkAvailableMerchant(merchantID)) {
            return false; // one or all IDs do not exist
        }

        Product p = productService.getProduct(productID);
        User u = userService.getUser(userID);

        if (userService.canPay(userID, p.getPrice())
                && StockAvailable(productID, merchantID, 1)) {

            // TODO Extra steps:
            // count how many times a product was purchased
            p.setTimesPurchased(p.getTimesPurchased() + 1);

            productService.save(p);
            // store user purchase history in the format ("productID_Date,") i.e. ("100_2025-07-28,")
            u.setOrderHistory(u.getOrderHistory().concat(productID+"_"+ LocalDate.now()+","));
            userService.save(u);

            return removeStockFromProduct(productID, merchantID, 1)
                    && userService.pay(userID, p.getPrice());
        } else {
            return false;
        }
    }

}
