package com.yu.supermarketsim;

import java.util.Random;

public class Util {

	public static Random random = new Random(13);
	
	public static int random(int min, int max) {   
        int r = random.nextInt(max)%(max-min+1) + min;
        return r;
    }
	
	public static void demo() {
		for(int i=0; i<10; i++) {
			System.out.println(random(1000, 3000));
		}
	}
	
	public static void main(String[] args) {
		demo();
	}

}
