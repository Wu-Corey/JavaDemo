package com.example.javademo.util;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 2022/3/27
 */
public class TimeUtil {


    /**
     * 获取两个时间之前相差的毫秒值
     * @param start
     * @param end
     * @return
     */
    public static Long between(LocalDateTime start,LocalDateTime end){
        return Duration.between(start,end).toMillis();
    }

}
