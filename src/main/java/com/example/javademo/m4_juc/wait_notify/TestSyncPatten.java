package com.example.javademo.m4_juc.wait_notify;

import com.example.javademo.m4_juc.utils.SleepHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 2022/2/24
 * 同步模式-保护性暂停
 */
public class TestSyncPatten {

    public static Log log= LogFactory.getLog(TestSyncPatten.class);

    public static void main(String[] args) {

        GuardedObject guardedObject = new GuardedObject();
        new Thread(()->{
            log.info("开始获取结果");
            log.info(guardedObject.get());
        },"t1").start();

        new Thread(()->{
            log.info("开始下载");
            SleepHelper.sleepSecond(10);
            List<String> contents = DownLoader.download();
            guardedObject.set(contents);
        },"t2").start();
    }

}


class GuardedObject{
    private Object response;

    public Object get(){
        synchronized (this){
            while (response == null){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    public void set(Object obj){
        synchronized (this){
            response = obj;
            notifyAll();
        }
    }
}

class DownLoader{

    public static List<String> download(){
        List<String> lines = new ArrayList<>();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("https://www.baidu.com").openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.defaultCharset()));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
