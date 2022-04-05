package com.example.javademo.m4_juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 2022/3/31
 */
public class TestAQS {


}

/**
 * 自定义锁
 */
class MyAQSLock implements Lock{

    class MySync extends AbstractQueuedSynchronizer{


        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            // state变量是volatile 有写屏障 可以保证前面的对其他线程可见
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition(){
            return new ConditionObject();
        }
    }

    private MySync mySync = new MySync();


    @Override
    public void lock() {
        mySync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        mySync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return mySync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return mySync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        mySync.release(1);
    }

    @Override
    public Condition newCondition() {
        return mySync.newCondition();
    }

}
