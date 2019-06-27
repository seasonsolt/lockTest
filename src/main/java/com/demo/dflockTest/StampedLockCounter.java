package com.demo.dflockTest;

import java.util.concurrent.locks.StampedLock;

public class StampedLockCounter implements Counter {
    private static int count;

    private final StampedLock s1 = new StampedLock();


    public Integer get() {
        //乐观锁操作
        long stamp = s1.tryOptimisticRead();
        //拷贝变量
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int currentC = count;
        //判断读期间是否有写操作
        if (!s1.validate(stamp)) {
            //
            stamp = s1.readLock();
            try {
                currentC = count;
            } finally {
                s1.unlockRead(stamp);
            }
        }
        return currentC;
    }

    public void count() {
        long stamp = s1.writeLock();
        try {
            count++;
            // System.out.println("count：" + count);
        } finally {
            s1.unlockWrite(stamp);
        }
    }


}
