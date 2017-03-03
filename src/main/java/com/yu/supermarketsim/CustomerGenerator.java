package com.yu.supermarketsim;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class CustomerGenerator implements Runnable {
//	public static int INTERVALMIN = Config.CUSTOMER_INTERVALMIN;
//	public static int INTERVALMAX = Config.CUSTOMER_INTERVALMAX;
	
	private BlockingQueue<Customer> line;
	public CustomerGenerator(BlockingQueue<Customer> line) {
		this.line = line;
	}
	
	public void run() {
		while(!Thread.interrupted()) {
			try {
				Customer c = Customer.random();
				this.line.put(c);
				System.out.println(String.format("Customer[%d] is queueing.", c.getId()));
				TimeUnit.MILLISECONDS.sleep(
						Util.random(Config.CUSTOMER_INTERVALMIN, Config.CUSTOMER_INTERVALMAX));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}