package com.example.javademo.m4_juc.thread_basic;

import lombok.extern.slf4j.Slf4j;

/**
 * 2021/9/11
 */
@Slf4j
public class T_4_TwoPhaseTermination {

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        Thread.sleep(3500);
        twoPhaseTermination.stop();

    }
}
@Slf4j(topic = "t.TwoPhaseTermination")
class TwoPhaseTermination{
    // 监控线程
    private Thread monitor;

    public void start(){
        monitor = new Thread(()->{
            while (true){
                Thread thread = Thread.currentThread();
                if (thread.isInterrupted()){
                    log.info("处理监控线程结束后...");
                    break;
                }
                try {
                    thread.sleep(1000);         // 可能在此打断
                    log.info("执行监控中，监控逻辑..."); // 可能在此打断
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    thread.interrupt(); // 如果在睡眠中被打断需要额外补充打断标记
                }
            }
        },"monitor_thread");

        monitor.start();
    }
    public void stop(){
        monitor.interrupt();
    }
}
