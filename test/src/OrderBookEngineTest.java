package src;

import org.junit.Assert;
import org.junit.Test;


public class OrderBookEngineTest {

	@Test
	public void test1() {
		
		OrderBookEngine engine = new OrderBookEngine();
		engine.addBidOrder(9.9, 1000);
		engine.addBidOrder(9.8, 3000);
		engine.addBidOrder(9.7, 5000);
		
		engine.addAskOffer(10.1, 3000);
		engine.addAskOffer(10.2, 3000);
		engine.addAskOffer(10.3, 10000);
		
		Assert.assertEquals(3, engine.getAskLevel());
		Assert.assertEquals(3, engine.getBidLevel());
		
		Assert.assertEquals(9000, engine.getBidQuantity());
		Assert.assertEquals(16000, engine.getAskQuantity());
		
		engine.printOrderBook();
		
		engine.receiveOrder(10.5, 17000, true);

		Assert.assertEquals(0, engine.getAskLevel());
		Assert.assertEquals(4, engine.getBidLevel());
		
		Assert.assertEquals(10000, engine.getBidQuantity());
		Assert.assertEquals(0, engine.getAskQuantity());
		
		engine.receiveOrder(9.8, 60000, false);
		
		Assert.assertEquals(1, engine.getAskLevel());
		Assert.assertEquals(1, engine.getBidLevel());
		
		Assert.assertEquals(5000, engine.getBidQuantity());
		Assert.assertEquals(55000, engine.getAskQuantity());
		
		engine.receiveOrder(9.8, 55000, true);
		
		Assert.assertEquals(0, engine.getAskLevel());
		Assert.assertEquals(1, engine.getBidLevel());
		
		Assert.assertEquals(5000, engine.getBidQuantity());
		Assert.assertEquals(0, engine.getAskQuantity());
		
		engine.receiveOrder(1, 5000, false);
		
		Assert.assertEquals(0, engine.getAskLevel());
		Assert.assertEquals(0, engine.getBidLevel());
		
		Assert.assertEquals(0, engine.getBidQuantity());
		Assert.assertEquals(0, engine.getAskQuantity());
		
	}

}
