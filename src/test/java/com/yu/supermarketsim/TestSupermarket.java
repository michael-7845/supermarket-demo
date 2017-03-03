package com.yu.supermarketsim;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;  
import static org.hamcrest.Matchers.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class TestSupermarket {
	
	@Test
	public void testSoldOut() {
		Supermarket sm = new Supermarket();
		sm.store = new ConcurrentHashMap<GoodsType, BlockingQueue<Goods>>();
		BlockingQueue<Goods> apples = mock(BlockingQueue.class);
		sm.store.put(GoodsType.Apple, apples);
		BlockingQueue<Goods> macbooks = mock(BlockingQueue.class);
		sm.store.put(GoodsType.Macbook, apples);
		BlockingQueue<Goods> cookies = mock(BlockingQueue.class);
		sm.store.put(GoodsType.Cookie, apples);
		
		when(apples.isEmpty()).thenReturn(false);  
		when(macbooks.isEmpty()).thenReturn(true);
		when(cookies.isEmpty()).thenReturn(true); 
		assertFalse(sm.isSoldOut());
		
		when(macbooks.isEmpty()).thenReturn(false);  
		assertFalse(sm.isSoldOut());
		
		when(cookies.isEmpty()).thenReturn(false); 
		assertFalse(sm.isSoldOut());
		
		when(apples.isEmpty()).thenReturn(true);  
		when(macbooks.isEmpty()).thenReturn(true);
		when(cookies.isEmpty()).thenReturn(true); 
		assertTrue(sm.isSoldOut());
	}
	
	@Test
	public void testInit() {
		Supermarket sm = new Supermarket(); 
		try {
			sm.init();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(0, sm.line.size());
		assertEquals(3, sm.cashiers.size());
		for(GoodsType t: GoodsType.values()) {
			assertEquals(15, sm.store.get(t).size());
		}
	}
}
