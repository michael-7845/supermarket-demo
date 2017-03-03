package com.yu.supermarketsim;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestCustomer {
	
	@Test
	public void testRandom() {
		int[] goods = {0, 0, 0};
		for(int i=0; i<100; i++) {
			Customer c = Customer.random();
			goods[c.getType().ordinal()]++;
		}
		assertEquals(100, goods[0]+goods[1]+goods[2]);
	}

		
}
