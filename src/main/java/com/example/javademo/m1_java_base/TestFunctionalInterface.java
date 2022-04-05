package com.example.javademo.m1_java_base;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 2022/3/26
 */
public class TestFunctionalInterface {

    public static void main(String[] args) {
        System.out.println("testPredicate");
        testPredicate();
        System.out.println("testFunction");
        testFunction();

        ArrayList<Object> list = new ArrayList<>();
        List<Integer> collect = list.stream()
                .filter(Objects::nonNull)
                .map(Object::hashCode)
                .collect(Collectors.toList());

    }

    static void testPredicate(){
        Predicate<String> p01= str -> str.isEmpty() || str.trim().isEmpty();

        // 测试传入的字符串是否为空
        System.out.println(p01.test(""));
        System.out.println(p01.test(" "));
        System.out.println(p01.test("admin"));

        // 用户手机格式合法性

        // 行为封装到内部接口里
        Predicate<String> p02 = MyStringInter.checkPhone();
        // 直接写行为
        Predicate<String> p03 = (e)-> Pattern.compile("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[0-9])" +
                "|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$").matcher(e).matches();

        System.out.println(p02.test("18824241461"));
        System.out.println(p02.test("110"));
        System.out.println(p03.test("18824241461"));

        String action = "";
        Predicate<String> p04 = (a) -> StringUtils.isEmpty(a);

        // 如果是index或为main或action为空
        if (p04.or((a) -> a.equals("index")).or((a) -> a.equals("main")).test(action)) {
            System.out.println("网站主页面...");
        } else {
            System.out.println("其他页面...");
        }

    }

    // 注意内部类不能有静态方法 所以可以定义为接口，来进行调用
    public interface MyStringInter {
        static Predicate<String> checkPhone(){
            String checkPhone= "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[0-9])" +
                    "|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
            return (e)-> Pattern.compile(checkPhone).matcher(e).matches();
        }
    }

    static void testFunction(){
        // 实现用户密码 Base64加密操作
        Function<String,String> f01=(password)-> Base64.getEncoder().encodeToString(password.getBytes());
        System.out.println(f01.apply("123456"));
    }


}
