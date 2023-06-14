package com.currencyExchange.service;

import com.currencyExchange.repository.Repository_CoinUpdate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class Service_UpdateCoins {

    private final Repository_CoinUpdate repositoryCoinUpdate;

    public void setCoinsCount(String coinValue, Integer countCount){
        repositoryCoinUpdate.updateCoinCount(new BigDecimal(coinValue), Long.valueOf(countCount));
    }

}
