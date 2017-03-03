package com.yu.supermarketsim;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Cashier implements Callable<String> {
//	public static int INTERVALMIN = Config.CASHIER_INTERVALMIN;
//	public static int INTERVALMAX = Config.CASHIER_INTERVALMAX;
	
	private static int number = 0;
	private final int id = number++;
	
	private int customersServed = 0;
	public int getCustomersServed() {
		return customersServed;
	}

	private Supermarket sm;
	
	public Cashier(Supermarket sm) {
		this.sm = sm;
	}
	
	public void serve(Customer c) {
		GoodsType required = c.getType();
		Goods g = null;
//		synchronized(this.sm) {
		{
			BlockingQueue<Goods> goods = this.sm.store.get(required);
			if(!goods.isEmpty()) {
				try {
					g = goods.take();
					g.setOutTimestamp(System.currentTimeMillis());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		c.setFinishedTimestamp(System.currentTimeMillis());
		c.setGoods(g);
		try {
			Order o = new Order(c, g);
			this.sm.orders.put(o);
			System.out.println(String.format("Cashier[%d] finish %s", id, o));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customersServed++;
	}

	public String call() {
		while(!this.sm.isSoldOut()) {
			try {
				Customer c = this.sm.line.take();
				this.serve(c);
				TimeUnit.MILLISECONDS.sleep(
						Util.random(Config.CASHIER_INTERVALMIN, Config.CASHIER_INTERVALMAX));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return String.format("Cashier[%d] sold out!", this.id);
	}
	
	public String toString() {
		return String.format("Cashier[%d]{served: %d}", 
				this.id, this.customersServed);
	}

}
