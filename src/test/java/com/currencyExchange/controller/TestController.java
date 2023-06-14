package com.currencyExchange.controller;

import com.currencyExchange.bean.BillChangeResponse;
import com.currencyExchange.service.Service_Exchanger;
import com.currencyExchange.service.Service_UpdateCoins;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest
class TestController {

    @MockBean
    private Service_Exchanger serviceExchanger;

    @MockBean
    private Service_UpdateCoins serviceUpdateCoins;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testProject() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/ok"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void ChangeTest1() throws Exception {
        // Mocking data
        String billAmount = "20";
        boolean usemaxcoin = false;
        // Test with false max count coins
        List<BillChangeResponse> expectedResponse = Arrays.asList( new BillChangeResponse(10, 2));
        // Configure mock service
        when(serviceExchanger.getCoinChangeForBill(20, usemaxcoin)).thenReturn(expectedResponse);
        // Perform the test
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/currency/get_change")
                        .param("bill_amount", billAmount))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"coinUsed\":10,\"numberOfCoinsUsed\":2}]"))
                .andReturn();
    }

    @Test
    void ChangeTest2() throws Exception {
        // Mocking data
        String billAmount = "20";
        // Test with true max coins used
        boolean usemaxcoin = true;
        List<BillChangeResponse> expectedResponse = Arrays.asList( new BillChangeResponse(1, 20));
        // Configure mock service
        when(serviceExchanger.getCoinChangeForBill(20, usemaxcoin)).thenReturn(expectedResponse);
        // Perform the test
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/currency/get_change")
                        .param("bill_amount", billAmount).param("use_max_coins", "true"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"coinUsed\":1,\"numberOfCoinsUsed\":20}]"))
                .andReturn();
    }

    //@Test
    void testGetChangeForBill_InvalidBill() throws Exception {
        // Mocking data
        String billAmount = "212";
        // Perform the test
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/get_change")
                        .param("bill_amount", billAmount).param("use_min_coins", "false"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json("{\n" +
                        "    \"timestamp\": \"2023-06-12T23:38:18.684+00:00\",\n" +
                        "    \"status\": 500,\n" +
                        "    \"error\": \"Internal Server Error\",\n" +
                        "    \"path\": \"/currency/get_change\"\n" +
                        "}"))
                ;
    }

}
