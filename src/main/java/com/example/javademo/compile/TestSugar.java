package com.example.javademo.compile;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 2022/4/30
 */
public class TestSugar {

    public static void main(String[] args) {
        method1();
    }

    private static void method1() {
        List<Integer> list = Arrays.asList(1,2,3,4);

        int sum = 0;
        for (Integer i : list) {
            sum += i;
        }
        System.out.println(sum);
    }

    private static void method2() {
        List<Integer> list = Arrays.asList(new Integer[]{
                Integer.valueOf(1),
                Integer.valueOf(2),
                Integer.valueOf(3),
                Integer.valueOf(4),
        });

        int sum = 0;
        for(Iterator iterator = list.iterator(); iterator.hasNext();){
            int i = ((Integer)iterator.next()).intValue();
            sum += i;
        }
        System.out.println(sum);
    }
}
