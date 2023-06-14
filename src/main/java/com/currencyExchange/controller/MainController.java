package com.currencyExchange.controller;


import com.currencyExchange.bean.BillChangeResponse;
import com.currencyExchange.bean.CoinRequest;
import com.currencyExchange.service.Service_Exchanger;
import com.currencyExchange.service.Service_UpdateCoins;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
@Validated
public class MainController {

    private final Service_Exchanger serviceExchanger;

    private final Service_UpdateCoins serviceUpdateCoins;

    @PostMapping(value = "/coin_update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void setCoinCount(@Valid @RequestBody CoinRequest coinRequest){
        serviceUpdateCoins.setCoinsCount(coinRequest.getCoinType(), coinRequest.getCoinCount());
    }

    @GetMapping(value = "/ok", produces = MediaType.APPLICATION_JSON_VALUE)
    public String ping(){
        return new Date()+"";
    }

    @GetMapping(value = "/get_change", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<BillChangeResponse> getChangeForBill(@Valid @RequestParam("bill_amount")
                                                                 @Pattern(regexp="^(1|2|5|10|20|50|100)$", message="Bill is not valid")
                                                                 String billAmount,
                                                     @RequestParam(value = "use_max_coins", required = false, defaultValue = "false") boolean maximumCoins){
        return serviceExchanger.getCoinChangeForBill((Integer.parseInt(billAmount)), maximumCoins);
    }

}
