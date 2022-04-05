package com.example.javademo.m5_jvm.p_1_classLoader.myClassloader;

/**
 * 2021/6/13
 * 模拟计算薪水
 */
public class OADemo {

    public static void main(String[] args) throws Exception {
        Double salary = 2000d;
//        URL url = new URL("file:D:\\Java\\DemoCode\\salaryCalc\\out\\artifacts\\salaryCalc_jar\\salaryCalc.jar");
//        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});

        MyClassLoader myClassLoader = new MyClassLoader("file:D:\\Java\\DemoCode\\salaryCalc\\out\\artifacts\\salaryCalc_jar\\salaryCalc.jar");


        Double money = clacSalary(salary, myClassLoader);
        System.out.println("到手工资：" + money);
    }

    private static Double clacSalary(Double salary, ClassLoader classLoader) throws Exception {
        Class<?> clazz = classLoader.loadClass("com.demo.SalaryCalc");
        Object obj = clazz.newInstance();
        return (Double) clazz.getMethod("cal", Double.class).invoke(obj, salary);
    }


}
