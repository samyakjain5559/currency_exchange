package com.currencyExchange.service;

import com.currencyExchange.bean.BillChangeResponse;
import com.currencyExchange.entity.Coin;
import com.currencyExchange.exceptions.Exceptions;
import com.currencyExchange.repository.Repository_CoinUpdate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class TestChangeFinder {

    @Mock
    private Repository_CoinUpdate repositoryCoinUpdate;

    private Service_Exchanger serviceExchanger;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        serviceExchanger = new Service_Exchanger(repositoryCoinUpdate);
    }

    @Test
    void getChangeWithMaxCoins() {
        // Mocking data
        int amount = 20;
        boolean maximumCoins = true;
        List<Coin> availableCoinEntities = Arrays.asList(
                new Coin(1L, "", new BigDecimal(1), 100L, null),
                new Coin(1L, "", new BigDecimal(5), 100L, null)
        );
        List<BillChangeResponse> expectedResponse = new ArrayList<>();
        expectedResponse.add(BillChangeResponse.builder().CoinUsed(1).NumberOfCoinsUsed(20).build());
        when(repositoryCoinUpdate.findByCountGreaterThan(0)).thenReturn(availableCoinEntities);
        List<BillChangeResponse> actualResponse = serviceExchanger.getCoinChangeForBill(amount, maximumCoins);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getChangeWithMinCoins() {
        // Mocking data
        int amount = 20;
        boolean maximumCoins = false;
        List<Coin> availableCoinEntities = Arrays.asList(
                new Coin(1L, "", new BigDecimal(1), 100L, null),
                new Coin(1L, "", new BigDecimal(10), 100L, null)
        );
        List<BillChangeResponse> expectedResponse = new ArrayList<>();
        expectedResponse.add(BillChangeResponse.builder().CoinUsed(10).NumberOfCoinsUsed(2).build());
        when(repositoryCoinUpdate.findByCountGreaterThan(0)).thenReturn(availableCoinEntities);
        List<BillChangeResponse> actualResponse = serviceExchanger.getCoinChangeForBill(amount, maximumCoins);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testNotEnoughChange() {
        // Mocking data
        int amount = 100;
        boolean maximumCoins = true;
        List<Coin> availableCoinEntities = Arrays.asList(
                new Coin(1L, "", new BigDecimal(1), 0L,null),
                new Coin(2L, "", new BigDecimal(5), 5L, null)
        );
        when(repositoryCoinUpdate.findByCountGreaterThan(0)).thenReturn(availableCoinEntities);
        Assertions.assertThrows(Exceptions.class, () ->
                serviceExchanger.getCoinChangeForBill(amount, maximumCoins)
        );

    }

}
