package com.yu.supermarketsim;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;

public class TestCustomerGenerator {

	@Test
	public void testGenerate() {
		Config.CUSTOMER_INTERVALMIN = 300;
		Config.CUSTOMER_INTERVALMAX = 300;
		BlockingQueue<Customer> line = new LinkedBlockingQueue<Customer>();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Thread(new CustomerGenerator(line)));
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exec.shutdown();
		assertEquals(line.size(), 4);
	}
}
