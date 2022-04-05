package com.example.javademo.m4_juc.volatille;

/**
 * 2021/5/29
 */
public class T_1_Disorder {

    private static int  x = 0,y = 0,a = 0,b = 0;

    public static void main(String[] args) throws InterruptedException {
        int j = 0;
        for (;;){
            j++;

            x = 0;y = 0;
            a = 0;b = 0;

            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });

            Thread two = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });

            one.start();
            two.start();
            one.join();
            two.join();

            String result = "第" + j + "次 (" + x + "," + y+")";

            if (x == 0 && y == 0){
                System.out.println(result);
                break;
            }
        }
    }
}
