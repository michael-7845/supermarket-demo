package com.yu.supermarketsim;

public class Order {
	private static int number = 0;
	private final int id = number++;
	public int getId() {
		return id;
	}
	
	private Customer c;
	public Customer getC() {
		return c;
	}

	private Goods g;
	public Goods getG() {
		return g;
	}
	
	public Order(Customer c, Goods g) {
		this.c = c;
		this.g = g;
	}
	
	public String toString() {
		String s;
		if(g == null) {
			s = String.format("Order{Customer[%d]:Goods[%d]}", c.getId(), -1);
		} else {
			s = String.format("Order{Customer[%d]:Goods[%d]}", c.getId(), g.getId());
		}
		return s;
	}
}
