package com.demo.dflockTest;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RTTCounter implements Counter {
    private static int count;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Integer get() {

        lock.readLock().lock();
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
            lock.readLock().unlock();
        }
    }

    public void count() {
        lock.writeLock().lock();
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
            lock.writeLock().unlock();
        }
    }
}
