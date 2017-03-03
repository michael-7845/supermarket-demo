package com.yu.supermarketsim;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Supermarket {
	
//	public static int STORESIZE = Config.MARKET_STORESIZE;
//	public static int CASHIERSIZE = Config.MARKET_CASHIERSIZE;
	
	public BlockingQueue<Customer> line = new LinkedBlockingQueue<Customer>();
	public ConcurrentMap<GoodsType, BlockingQueue<Goods>> store;
	public BlockingQueue<Order> orders;
	public long startTimestamp, endTimestamp;
	public List<Cashier> cashiers = new ArrayList<Cashier>(Config.MARKET_CASHIERSIZE);
	
	public void init() throws InterruptedException {
		line = new LinkedBlockingQueue<Customer>();
		store = new ConcurrentHashMap<GoodsType, BlockingQueue<Goods>>();
		for(GoodsType t: GoodsType.values()) {
			BlockingQueue<Goods> goods = new LinkedBlockingQueue<Goods>();
			store.put(t, goods);
			for(int i=0; i<Config.MARKET_STORESIZE; i++) {
				goods.put(new Goods(t, System.currentTimeMillis()));
			}
		}
		orders = new LinkedBlockingQueue<Order>();
		for(int i = 0; i < Config.MARKET_CASHIERSIZE; i++)
			cashiers.add(new Cashier(this));
	}
	
	public void simulate() {
	    System.out.println("simulation start.");
	    
		ExecutorService daemonExec = Executors.newCachedThreadPool(new ThreadFactory() {
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});
		daemonExec.execute(new Thread(new CustomerGenerator(this.line)));
		
		ExecutorService exec = Executors.newCachedThreadPool();
	    ArrayList<Future<String>> results = new ArrayList<Future<String>>();
	    this.startTimestamp = System.currentTimeMillis();
	    for(int i = 0; i < Config.MARKET_CASHIERSIZE; i++)
	    	results.add(exec.submit(cashiers.get(i)));
	    for(Future<String> fs : results) {
	    	try {
	    		// get() blocks until completion:
	    		System.out.println(fs.get());
	    	} catch(InterruptedException e) {
	    		System.out.println("1:" + e);
	    		return;
	    	} catch(ExecutionException e) {
	    		System.out.println("2:" + e);
	    	} 
	    }
	    exec.shutdown();
	    daemonExec.shutdown();
	    this.endTimestamp = System.currentTimeMillis();
	    
	    System.out.println("simulation end.");
	}
	
	public void statistics() {
		System.out.println("================ STATISTICS ================");
		System.out.println("Start selling at " + this.startTimestamp);
		System.out.println("Sold out at " + this.endTimestamp);
		System.out.println("----------------- Customer -----------------");
		for(Order o: orders) {
			System.out.println(o.getC().toString());
		}
		System.out.println("------------------- Goods ------------------");
		for(Order o: orders) {
			Goods g = o.getG();
			if(g != null)
				System.out.println(g.toString());
		}
		System.out.println("------------------ Cashier -----------------");
		for(Cashier c: cashiers) {
			System.out.println(c);
		}
	}
	
	public synchronized boolean isSoldOut() {
		for(GoodsType type: this.store.keySet()) {
			if(!this.store.get(type).isEmpty())
				return false;
		}
		return true;
	}
	
	public static void main(String... args) {
		Supermarket sm = new Supermarket();
		try {
			sm.init();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(GoodsType t: GoodsType.values()) {
			BlockingQueue<Goods> goods = sm.store.get(t);
			for(Goods g: goods) {
				System.out.println(g.toString());
			}
		}
	}

}


