package com.demo.dflockTest;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderCounter implements Counter {
	private static LongAdder count = new LongAdder();
		public Long get() {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return  count.sum();
		}

		public void count() {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count.increment();
		}

}
