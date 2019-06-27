package com.demo.dflockTest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCounter implements Counter {
    private static int count;

    private final Lock lock = new ReentrantLock();

    @Override
    public Integer get() {

        lock.lock();
        try {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return count;
            // System.out.println("count：" + count);
        } finally {
            lock.unlock();
        }
    }

    /**
     * count值+1
     *
     * @return
     * @throws InterruptedException
     */
    @Override
    public void count() {
        lock.lock();
        try {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            count++;
            // System.out.println("count：" + count);
        } finally {
            lock.unlock();
        }
    }

}
