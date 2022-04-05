package com.example.javademo.m4_juc.aqs;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


/**
 * 2021/7/1
 */
@Service
@Slf4j
public class TradeService {


    @Autowired
    JdbcTemplate jdbcTemplate;

    MyLock myLock = new MyLock();

    /**
     * 扣减库存
     *
     * @return
     */
    public String decStock() {


        myLock.lock();
        Integer stock = jdbcTemplate.queryForObject("select stock from goods_stock where id = 1", Integer.class);
        if (stock <= 0) {
            log.info("库存不足，下单失败！");
            myLock.unLock();
            return "库存不足，下单失败！";
        }
        stock--;

        jdbcTemplate.update("update goods_stock set stock = ? where id = 1", stock);

        log.info("下单成功，当前剩余库存：" + stock);
        myLock.unLock();

        return "下单成功，当前剩余库存：" + stock;

    }

}
