package com.yu.supermarketsim;

/**
Supermarket 实体
Cashier 实体
Customer 实体
Good 实体
流程如下
-      启动 Supermarket 
-      Supermarket 初始化3个 Cashier
-      Supermarket  针对Apple, Macbook, Cookie三种Good 每种初始化15个到商店库存
-      每隔1~3秒 产生一个 Customer 随机购买一个商品
-      每隔5~10秒 Cashier 处理一个 Customer 购买请求
-      重复以上过程直到所有商品售罄
-      统计出每个顾客平均等待时间
-      统计出每个商品平均售出时间
-      统计出从开始销售到售罄总共时间
-      统计出每个 Cashier 接待的顾客数量
要求：
-      请使用单元测试来确保结果正确
 */

public class App 
{
	public static Supermarket simulate() {
		Supermarket sm = new Supermarket();
		
		try {
			sm.init();
			sm.simulate();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sm.statistics();
		
		return sm;
	}
	
	public static Supermarket simulate(int storesize, int cashiersize, int cashierMin, 
			int cashierMax, int customerMin, int customerMax) {
		Config.MARKET_STORESIZE = storesize;
		Config.MARKET_CASHIERSIZE = cashiersize;
		
		Config.CASHIER_INTERVALMIN = cashierMin;
		Config.CASHIER_INTERVALMAX = cashierMax;
		
		Config.CUSTOMER_INTERVALMIN = customerMin;
		Config.CUSTOMER_INTERVALMAX = customerMax;
		
		return simulate();
	}
	
    public static void main( String[] args )
    {
//        simulate(5, 3, 500, 1000, 100, 300);
        simulate();
    }
}
