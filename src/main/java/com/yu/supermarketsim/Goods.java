package com.yu.supermarketsim;

public class Goods {
	private static int number = 0;
	private final int id = number++;
	public int getId() {
		return id;
	}

	private GoodsType type;
	public GoodsType getType() {
		return type;
	}
	public void setType(GoodsType type) {
		this.type = type;
	}

	private long inTimestamp;
	private long outTimestamp;
	
	public long getInTimestamp() {
		return inTimestamp;
	}
	public synchronized void setInTimestamp(long inTimestamp) {
		this.inTimestamp = inTimestamp;
	}
	public long getOutTimestamp() {
		return outTimestamp;
	}
	public synchronized void setOutTimestamp(long outTimestamp) {
		this.outTimestamp = outTimestamp;
	}
	
	public Goods(GoodsType type, long inTs) {
		this.type = type;
		this.inTimestamp = inTs;
	}
	
	public long timeInStore() {
		return this.outTimestamp-this.inTimestamp;
	}
	
	public String toString() {
		return String.format("Goods[%d][%s]{in: %d, out: %d, in store: %d (ms)}", 
				this.id, this.type, this.inTimestamp, this.outTimestamp, 
				(this.outTimestamp-this.inTimestamp));
	}
}
