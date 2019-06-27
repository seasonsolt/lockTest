package com.demo.dflockTest;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime) // 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.MICROSECONDS) // 输出结果的时间粒度为微秒
@State(Scope.Thread) // 每个测试线程一个实例
public class SyncCounter implements Counter{
    private static int count;

    private static String lock = "lock";

    public Integer get() {
        synchronized (lock) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return count;
            //System.out.println("count：" + count);
        }
    }

    @Override
    public void count() {
        synchronized (lock) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            count++;
            //System.out.println("count：" + count);
        }
    }

}
