package com.example.javademo.m4_juc.aqs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2021/7/1
 */
@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @RequestMapping("/order")
    public String order() {
        tradeService.decStock();
        return "success";
    }
}
