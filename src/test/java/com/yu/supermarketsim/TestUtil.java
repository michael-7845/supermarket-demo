package com.yu.supermarketsim;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;  
import static org.hamcrest.Matchers.*;

public class TestUtil {
	
	@Test
	public void testRandomInRange() {
		for(int i=0; i<100; i++) {
			assertThat(Util.random(1, 15), allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(15)));
		}
	}
	
}
