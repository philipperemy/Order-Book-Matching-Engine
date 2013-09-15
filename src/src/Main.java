package src;

import src.OrderBookEngine;
import src.RandomDouble;
import src.RandomInt;

public class Main {

	static RandomDouble randomDouble = new RandomDouble();
	static RandomInt randomInt = new RandomInt();

	static {
		randomDouble.initialize();
		randomInt.initialize( );
	}

	public static void show() {

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

	public static void g() throws InterruptedException {
		OrderBookEngine engine = new OrderBookEngine();
		long n1 = System.currentTimeMillis();
		int ITERATIONS = 10000000;
		for (int i = 0; i < ITERATIONS; i++) {

			if(i % 100000 == 0) {
				System.out.println(i + " orders sent");
			}
			
			if (randomDouble.nextDouble() > 50) {
				double price = randomDouble.nextDouble();
				int qty = randomInt.nextInt();
				engine.receiveOrder(price, qty, true);
			} else {
				double price = randomDouble.nextDouble();
				int qty = randomInt.nextInt();
				engine.receiveOrder(price, qty, false);
			}

		}

		long elapsedTimeMillis = System.currentTimeMillis() - n1;
		long elapsedTimeMicros = elapsedTimeMillis * 1000;
		
		System.out.println(((double)elapsedTimeMicros) / ITERATIONS + " us on average");
		engine.reset();

	}
	/**
	 * -Xmx4096m -Xms4096m -server -verbose:gc -XX:+PrintGCTimeStamps -XX:+PrintGCDetails -XX:+UseParNewGC -XX:+UseFastAccessorMethods
	 */
	public static void main(String[] args) throws InterruptedException {
		g();
	}

}
