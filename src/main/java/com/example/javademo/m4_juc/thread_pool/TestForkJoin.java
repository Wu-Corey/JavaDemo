package com.example.javademo.m4_juc.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 2022/3/27
 */
@Slf4j
public class TestForkJoin {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> result = forkJoinPool.submit(new MyTask(5));
        System.out.println(result.get());
    }
}

@Slf4j
class MyTask extends RecursiveTask<Integer>{

    private int n;

    @Override
    public String toString() {
        return "MyTask{" +
                "n=" + n +
                '}';
    }

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n == 1){
            log.info("join:{}",n);
            return 1;
        }

        MyTask prevTask = new MyTask(n - 1);


        prevTask.fork();
        log.info("fork:{},{}",n ,prevTask);

        Integer result =  n + prevTask.join();

        log.info("join:{}+{}={}",n ,prevTask,result);
        return result;
    }
}
