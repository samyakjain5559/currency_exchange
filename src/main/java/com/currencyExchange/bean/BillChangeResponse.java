package com.currencyExchange.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillChangeResponse {

    private Integer CoinUsed;
    private Integer NumberOfCoinsUsed;

}
