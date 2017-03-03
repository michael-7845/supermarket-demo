package com.yu.supermarketsim;

import java.util.Random;

public class Customer {
	private static int number = 0;
	private final int id = number++;
	public int getId() {
		return id;
	}

	private final GoodsType type;
	public GoodsType getType() {
		return type;
	}

	private long startTimestamp;
	public long getStartTimestamp() {
		return startTimestamp;
	}
	public void setStartTimestamp(long startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	private long finishedTimestamp;
	public long getFinishedTimestamp() {
		return finishedTimestamp;
	}
	public synchronized void setFinishedTimestamp(long finishedTimestamp) {
		this.finishedTimestamp = finishedTimestamp;
	}
	
	private Goods goods; // if sell out, null
	public Goods getGoods() {
		return goods;
	}
	public synchronized void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	public Customer(GoodsType type) { this.type = type; }
	
	public Customer(GoodsType type, long startTimestamp) { 
		this(type);
		this.startTimestamp = startTimestamp;
	}
	
	public String toString() {
		int goodsId;
		if(this.goods == null) {
			goodsId = -1;
		} else {
			goodsId = this.goods.getId();
		}
		return String.format("Customer[%d]{want: %s}{got: %s}{enter: %d, leave: %d, wait time: %d (ms)}", 
				this.id, this.type, goodsId, this.startTimestamp, this.finishedTimestamp,
				(this.finishedTimestamp-this.startTimestamp));
	}
	
	public static Random rand = new Random();
	public static Customer random() {
		GoodsType[] values = GoodsType.values();
		GoodsType t = values[rand.nextInt(values.length)];
		return new Customer(t, System.currentTimeMillis());
	}
}
