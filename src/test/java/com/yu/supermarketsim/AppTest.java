package com.yu.supermarketsim;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.util.EnumMap;
import java.util.Queue;

public class AppTest 
{
	@Test
	public void testSim() {
		Supermarket sm = App.simulate(5, 3, 500, 1000, 100, 300);
		
		assertTrue(sm.isSoldOut());
		
		Queue<Order> orders = sm.orders;
//		EnumMap<GoodsType, Integer> soldGoods = new EnumMap<GoodsType, Integer>(GoodsType.class);
		int[] soldGoods = {0, 0, 0};
		int customers = 0;
		for(Order o: orders) {
			Goods g = o.getG();
			if(g == null) continue;
			soldGoods[g.getType().ordinal()]++;
			customers++;
		}
		assertEquals(5, soldGoods[GoodsType.Apple.ordinal()]);
		assertEquals(5, soldGoods[GoodsType.Macbook.ordinal()]);
		assertEquals(5, soldGoods[GoodsType.Cookie.ordinal()]);
		assertEquals(5*3, customers);
	}
}
