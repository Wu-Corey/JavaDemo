package com.example.javademo.m4_juc.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2022/3/26
 */
@Slf4j
public class TestMyThreadPool {

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(1, 1000, TimeUnit.MILLISECONDS, 1,
                (queue,task)->{
//                    task.run();
                    throw new RuntimeException("抛弃此任务");
                });
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threadPool.execute(()->{
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("任务：{}", finalI);
            });
        }

    }
}

@Slf4j
class ThreadPool{
    private BlockingQueue<Runnable> taskQueue;

    private HashSet<Worker> workers = new HashSet<>();

    private int coreSize;

    private long timeOut;

    private TimeUnit timeUnit;

    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize, long timeOut, TimeUnit timeUnit,int capacity,RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeOut = timeOut;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(capacity);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task){
        synchronized (workers){
            if(workers.size() < coreSize){
                Worker worker = new Worker(task);
                log.info("新增worker：{}",worker);
                workers.add(worker);
                worker.start();
            }else {
                taskQueue.tryPut(task,rejectPolicy);
            }
        }
    }



    class Worker extends Thread{
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            while (task != null || (task =  taskQueue.poll(1000,TimeUnit.MILLISECONDS)) != null){
                try {
                    log.info("任务正在执行：{}",task);
                }catch (Exception e){

                }finally {
                    task = null;
                }
            }

            synchronized (workers){
                log.info("此worker被移除：{}",this);
                workers.remove(this);
            }
        }
    }



}
@FunctionalInterface
interface RejectPolicy<T>{
    void reject(BlockingQueue<T> queue,T task);
}

@Slf4j
class BlockingQueue<T>{

    private Deque<T> queue = new ArrayDeque<>();

    private int capacity;

    private ReentrantLock lock = new ReentrantLock();

    private Condition fullWaitSet = lock.newCondition();

    private Condition emptyWaitSet = lock.newCondition();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public T poll(long timeOut, TimeUnit timeUnit){
        lock.lock();
        try {
            long nanos = timeUnit.toNanos(timeOut);
            while (queue.isEmpty()){
                try {
                    if (nanos <= 0){
                        return null;
                    }
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

    public T take(){
        lock.lock();
        try {
            while (queue.isEmpty()){
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            log.info("从队列中取出任务:{}",t);
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }


    public void put(T t){
        lock.lock();
        try {
            while (queue.size() == capacity){
                try {
                    log.info("等待加入任务队列...");
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("任务加入队列:{}",t);
            queue.addLast(t);
            emptyWaitSet.signal();
        }finally {
            lock.unlock();
        }
    }

    public void tryPut(T t, RejectPolicy<T> rejectPolicy){
        lock.lock();
        try {
            if (queue.size() == capacity){
                rejectPolicy.reject(this,t);
            }else {
                log.info("任务加入队列:{}",t);
                queue.addLast(t);
                emptyWaitSet.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}
