package com.demo.dflockTest;

/**
 * @ Author     ：thin.
 * @ Date       ：Created in 14:49 2019-06-26
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
public interface Counter<T> {
    public T get();
    public void count();
}
