package com.example.javademo.m5_jvm.p_1_classLoader.myClassloader;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.security.SecureClassLoader;

/**
 * 2021/6/13
 * 自定义类加载器 用来读自定义的 .myclass文件
 */
public class MyClassLoader extends SecureClassLoader {

    private String classPath;

    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    // 先找到文件，读取，获取byte[]
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b;
        String filePath = "D:\\Java\\DemoCode\\salaryCalc\\out\\artifacts\\salaryCalc_jar\\com\\demo\\SalaryCalc.myclass";
        // 用流读取
        ByteArrayBuffer bab = new ByteArrayBuffer();
        int i;
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            while ((i = fis.read()) != -1){
                bab.write(i);
            }
            b = bab.toByteArray();
        } catch (Exception e) {
            throw new ClassNotFoundException("自定义文件不存在");
        }

        return defineClass(name, b, 0, b.length);
    }
}
