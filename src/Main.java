package src;

import java.text.DecimalFormat;

public class Main {

	public static void f()
	{
		OrderBookEngine engine = new OrderBookEngine();
		engine.addBidOrder(9.9, 1000);
		engine.addBidOrder(9.8, 3000);
		engine.addBidOrder(9.7, 5000);
		
		engine.addAskOffer(10.1, 3000);
		engine.addAskOffer(10.2, 3000);
		engine.addAskOffer(10.3, 10000);

		engine.receiveOrder(10.5, 17000, true);
		engine.receiveOrder(9.8, 60000, false);
		engine.receiveOrder(9.8, 55000, true);
		engine.receiveOrder(1, 5000, false);
	}
	
	
	public static void show()
	{

		OrderBookEngine engine = new OrderBookEngine();
		engine.addBidOrder(9.9, 1000);
		engine.addBidOrder(9.8, 3000);
		engine.addBidOrder(9.7, 5000);
		
		engine.addAskOffer(10.1, 3000);
		engine.addAskOffer(10.2, 3000);
		engine.addAskOffer(10.3, 10000);
		
		engine.printOrderBook();
		
		engine.receiveOrder(10.5, 17000, true);
		
		engine.printOrderBook();
		
		engine.receiveOrder(9.8, 60000, false);
		
		engine.printOrderBook();
		
		engine.receiveOrder(9.8, 55000, true);
		
		engine.printOrderBook();
		
		engine.receiveOrder(1, 5000, false);
		
		engine.printOrderBook();
		
		engine.printExecutedBook();
		
	}
	
	public static double getRandom()
	{
		DecimalFormat twoDForm = new DecimalFormat("#.##"); 
		return Double.valueOf(twoDForm.format(Math.random() * 100));
	}
	
	public static int getRandomInt()
	{
		return (int)(Math.random() * 10000);
	}
	
	public static void g()
	{
		OrderBookEngine engine = new OrderBookEngine();
		long n1 = System.currentTimeMillis();
		int ITERATIONS = 1000000;
		int ITERATIONS_100 = (int)ITERATIONS/100;
		int percentage = 0;
		for(int i=0; i<ITERATIONS; i++)
		{
			if(i%ITERATIONS_100 == 0)
			{
				System.out.println(percentage++ + "%");
			}
			if(getRandom()>50)
			{
				double price = getRandom();
				int qty = getRandomInt();
				//System.out.println("order BUY " + price + " @ " + qty);
				engine.receiveOrder(price, qty, true);
				//engine.printOrderBook();
				//engine.printExecutedBook();
			} 
			else
			{
				double price = getRandom();
				int qty = getRandomInt();
				//System.out.println("order SELL " + price + " @ " + qty);
				engine.receiveOrder(price, qty, false);
				//engine.printOrderBook();
				//engine.printExecutedBook();
			}
			
		}
		
		long n2 = System.currentTimeMillis();
		engine.printExecutedBook();
		engine.printOrderBook();
		System.out.println("executed orders : " + engine.getExecutedOrdersCount());
		System.out.println((n2-n1) + " ms");
		
	}
	
	public static void main(String[] args)
	{	
		g();
	}
	
	public static void f_perform()
	{
		long count = 0;
		long n1 = System.nanoTime();
		for(int i=0; i<10000000; i++)
		{
			f();
		}
		long n2 = System.nanoTime();
		count = n2-n1;
		count /= 10000000;
		System.out.println("count = " + count + " ns");
	}

}
