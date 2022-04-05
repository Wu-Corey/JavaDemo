package com.example.javademo.m4_juc.refTypeAndThreadLocal;


import com.example.javademo.m4_juc.utils.SleepHelper;

/**
 * 2021/5/31
 */
public class T_4_ThreadLocal {
    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(() ->{
            new SleepHelper().sleepSecond(1);
            tl.set(new Person("zhangsan"));
            System.out.println(tl.get());
            // 移除当前线程里该ThreadLocal对应的value 防止内存泄漏
            tl.remove();
        }).start();

        new Thread(() ->{
            new SleepHelper().sleepSecond(2);
            System.out.println(tl.get());
        }).start();
    }

}

class Person {
    public Person(String name) {
        this.name = name;
    }
    String name;
}
