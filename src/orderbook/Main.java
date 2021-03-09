package orderbook;

public class Main {

	static RandomDouble randomDouble = new RandomDouble();
	static RandomInt randomInt = new RandomInt();

	static {
		randomDouble.initialize();
		randomInt.initialize( );
	}

	public static void show() {

		OrderBookEngine engine = new OrderBookEngine();
		engine.addBidRestingOrder(9.9, 1000);
		engine.addBidRestingOrder(9.8, 3000);
		engine.addBidRestingOrder(9.7, 5000);

		engine.addAskRestingOffer(10.1, 3000);
		engine.addAskRestingOffer(10.2, 3000);
		engine.addAskRestingOffer(10.3, 10000);

		engine.printOrderBook();

		engine.onOrder(10.5, 17000, OrderBookEngine.Side.BUY);

		engine.printOrderBook();

		engine.onOrder(9.8, 60000, OrderBookEngine.Side.SELL);

		engine.printOrderBook();

		engine.onOrder(9.8, 55000, OrderBookEngine.Side.BUY);

		engine.printOrderBook();

		engine.onOrder(1, 5000, OrderBookEngine.Side.SELL);

		engine.printOrderBook();

	}

	public static void stress() {
		OrderBookEngine engine = new OrderBookEngine();
		long n1 = System.currentTimeMillis();
		int ITERATIONS = 10000000;
		System.out.println("Running for " + ITERATIONS + " iterations.");
		for (int i = 0; i < ITERATIONS; i++) {

			if(i % 100000 == 0) {
				System.out.println(i + " orders sent");
			}
			
			if (randomDouble.nextDouble() > 50) {
				double price = randomDouble.nextDouble();
				int qty = randomInt.nextInt();
				engine.onOrder(price, qty, OrderBookEngine.Side.BUY);
			} else {
				double price = randomDouble.nextDouble();
				int qty = randomInt.nextInt();
				engine.onOrder(price, qty, OrderBookEngine.Side.SELL);
			}

		}

		long elapsedTimeMillis = System.currentTimeMillis() - n1;
		long elapsedTimeMicros = elapsedTimeMillis * 1000;
		
		System.out.println(((double)elapsedTimeMicros) / ITERATIONS + " us on average.");
		engine.reset();

	}
	/**
	 * -Xmx4096m -Xms4096m -server
	 */
	public static void main(String[] args) {
		stress();
	}

}
