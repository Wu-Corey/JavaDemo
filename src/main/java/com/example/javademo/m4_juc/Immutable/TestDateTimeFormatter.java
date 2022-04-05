package com.example.javademo.m4_juc.Immutable;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * 2022/3/21
 */
@Slf4j
public class TestDateTimeFormatter {

    public static void main(String[] args) {
        String dateStr = "1997-07-03";

        //testSimpleDateFormatter(dateStr);// 线程不安全，会抛异常
        testDateTimeFormatter(dateStr);

    }

    private static void testSimpleDateFormatter(String dateStr){
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                Date date = null;
                try {
                    date = parser.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                log.info("{}",date);
            }).start();
        }
    }

    private static void testDateTimeFormatter(String dateStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                TemporalAccessor date = formatter.parse(dateStr);
                log.info("{}",date);
            }).start();
        }
    }
}
