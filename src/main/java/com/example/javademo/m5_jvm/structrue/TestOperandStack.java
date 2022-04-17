package com.example.javademo.m5_jvm.structrue;

/**
 * 2022/4/17
 */
public class TestOperandStack {

    public static void main(String[] args) {
        // 第一类问题 比如for循环里 无区别
        int i1 = 10;
        i1++;
        int i2 = 10;
        ++i2;

        System.out.println("i1 = "+i1);//11
        System.out.println("i2 = "+i2);//11

        //
        int i3 = 10;
        int i4 = i3++;
        int i5 = 10;
        int i6 =  ++i5;

        System.out.println("i4 = "+i4);// 10
        System.out.println("i6 = "+i6);// 11

        //
        int i7 = 10;
        i7 = i7++;
        int i8 = 10;
        i8 = ++i8;
        System.out.println("i7 = "+i7);//10
        System.out.println("i8 = "+i8);//11

        //
        int i9 = 10;
        int i10 = i9++ + ++i9;
        System.out.println("i10 = "+i10);//22
    }
}
