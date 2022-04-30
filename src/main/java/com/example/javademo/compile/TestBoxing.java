package com.example.javademo.compile;

/**
 * 2022/4/30
 */
public class TestBoxing {

    public static void main(String[] args) {
        Integer a = 1, b = 2, c = 3, d = 3, e = 321, f = 321;
        Long g = 3L;
        System.out.println(c == d); // true 比较的是对象 从缓存中取所以地址相同
        System.out.println(e == f); // false 不从缓存中取，是两个不同的对象
        System.out.println(c == (a + b)); // true  比较的是对象 从缓存中取所以地址相同
        System.out.println(c.equals(a + b)); // true 重写了equals，比较的是值
        System.out.println(g == (a + b)); // true Long也有缓存池，从缓存中取出了同一个对象
        System.out.println(g.equals(a + b)); // false
    }

}
