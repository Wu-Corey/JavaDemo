package main.java.com.example.javademo.m5_jvm.p_1_classLoader;

/**
 * 2021/6/12
 */
public class ClassLoaderDemo1 {

    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader cl1 = ClassLoaderDemo1.class.getClassLoader();
        // sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println("cl1->" + cl1);
        // sun.misc.Launcher$ExtClassLoader@5caf905d
        System.out.println("cl1的父加载器" + cl1.getParent());
        // null 实际上 BootStrap Classloader 由C++编写，虚拟机的一部分，不是Java类，故打印不出来
        System.out.println("cl1的父加载器的父加载器" + cl1.getParent().getParent());

        // String int 等基础类由 BootStrap Classloader加载
        ClassLoader cl2 = String.class.getClassLoader();
        System.out.println("cl2:"+ cl2);
        System.out.println(cl1.loadClass("java.util.List").getClass().getClassLoader());

        // -verbose:class -verbose:gc 加上参数，可在启动时打印类加载情况
        System.out.println("BootStrap Classloader 类加载目录：" + System.getProperty("sun.boot.class.path"));
        System.out.println("Extension Classloader 类加载目录：" + System.getProperty("java.ext.dirs"));
        System.out.println("AppClassloader 类加载目录：" + System.getProperty("java.class.path"));
    }
}
