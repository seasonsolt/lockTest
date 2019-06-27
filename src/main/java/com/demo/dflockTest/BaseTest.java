package com.demo.dflockTest;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * @ Author     ：thin.
 * @ Date       ：Created in 14:47 2019-06-26
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
@BenchmarkMode(Mode.AverageTime)// 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.MICROSECONDS)// 输出结果的时间粒度为微秒
@State(Scope.Thread) // 每个测试线程一个实例
public class BaseTest {

    private static int readThreadNum = 10;
    private static int writeThreadNum = 10;

    private static int maxValue = 100;

    public static int doTest( Counter lockTest, int readThreadNum, int writeThreadNum, int maxValue){
        long startTime = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(readThreadNum + writeThreadNum);
        for (int i = 0; i < writeThreadNum; i++) {
            new Thread(() -> {
                for (int cur = 0; cur < maxValue; cur++) {
                    lockTest.count();
                }
                latch.countDown();
            }).start();
        }

        for (int i = 0; i < readThreadNum; i++) {
            new Thread(() -> {
                for (int cur = 0; cur < maxValue; cur++) {
                    lockTest.get();
                }
                latch.countDown();
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        System.out.println(lockTest.getClass().getName() + "执行时长:" + (endTime - startTime) + ", count:" + lockTest.get());

        return 0;

    }

    @Benchmark
    public void testSync(){
        SyncCounter lock = new SyncCounter();
        doTest(lock, readThreadNum, writeThreadNum, maxValue);
    }

    @Benchmark
    public void testLock(){
        LockCounter lock = new LockCounter();
        doTest(lock, readThreadNum, writeThreadNum, maxValue);
    }
    @Benchmark
    public void testLongAdder(){
        LongAdderCounter lock = new LongAdderCounter();
        doTest(lock, readThreadNum, writeThreadNum, maxValue);
    }
    @Benchmark
    public void testRtt(){
        RTTCounter lock = new RTTCounter();
        doTest(lock, readThreadNum, writeThreadNum, maxValue);
    }
    @Benchmark
    public void testStamped(){
        StampedLockCounter lock = new StampedLockCounter();
        doTest(lock, readThreadNum, writeThreadNum, maxValue);
    }

    public static void main(String args[]){

        Options opt = new OptionsBuilder()
                .include(BaseTest.class.getSimpleName())
                .forks(1)
                .warmupIterations(3)
                .measurementIterations(3)
                .build();

        try {
            new Runner(opt).run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
