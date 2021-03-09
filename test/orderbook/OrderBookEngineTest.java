package orderbook;

import org.junit.Assert;
import org.junit.Test;


public class OrderBookEngineTest {

    @Test
    public void test1() {

        OrderBookEngine engine = new OrderBookEngine();
        engine.addBidRestingOrder(9.9, 1000);
        engine.addBidRestingOrder(9.8, 3000);
        engine.addBidRestingOrder(9.7, 5000);

        engine.addAskRestingOffer(10.1, 3000);
        engine.addAskRestingOffer(10.2, 3000);
        engine.addAskRestingOffer(10.3, 10000);

        Assert.assertEquals(3, engine.getAskLevel());
        Assert.assertEquals(3, engine.getBidLevel());

        Assert.assertEquals(9000, engine.getBidQuantity());
        Assert.assertEquals(16000, engine.getAskQuantity());

        engine.printOrderBook();

        engine.onOrder(10.5, 17000, OrderBookEngine.Side.BUY);

        Assert.assertEquals(0, engine.getAskLevel());
        Assert.assertEquals(4, engine.getBidLevel());

        Assert.assertEquals(10000, engine.getBidQuantity());
        Assert.assertEquals(0, engine.getAskQuantity());

        engine.onOrder(9.8, 60000, OrderBookEngine.Side.SELL);

        Assert.assertEquals(1, engine.getAskLevel());
        Assert.assertEquals(1, engine.getBidLevel());

        Assert.assertEquals(5000, engine.getBidQuantity());
        Assert.assertEquals(55000, engine.getAskQuantity());

        engine.onOrder(9.8, 55000, OrderBookEngine.Side.BUY);

        Assert.assertEquals(0, engine.getAskLevel());
        Assert.assertEquals(1, engine.getBidLevel());

        Assert.assertEquals(5000, engine.getBidQuantity());
        Assert.assertEquals(0, engine.getAskQuantity());

        engine.onOrder(1, 5000, OrderBookEngine.Side.SELL);

        Assert.assertEquals(0, engine.getAskLevel());
        Assert.assertEquals(0, engine.getBidLevel());

        Assert.assertEquals(0, engine.getBidQuantity());
        Assert.assertEquals(0, engine.getAskQuantity());

    }

    @Test
    public void test2() {
        OrderBookEngine engine = new OrderBookEngine();
        // Bid
        // <99.95, 100>
        //<99.90, 50>
        //<99.85,50>
        engine.onOrder(99.95, 100, OrderBookEngine.Side.BUY);
        engine.onOrder(99.90, 50, OrderBookEngine.Side.BUY);
        engine.onOrder(99.85, 50, OrderBookEngine.Side.BUY);

        // Ask
        // <100.00, 1000>
        // <100.05, 50>
        // <100.10, 90>
        engine.onOrder(100.0, 1000, OrderBookEngine.Side.SELL);
        engine.onOrder(100.05, 50, OrderBookEngine.Side.SELL);
        engine.onOrder(100.10, 90, OrderBookEngine.Side.SELL);

        engine.printOrderBook();

        engine.onOrder(99.97, 1, OrderBookEngine.Side.BUY);
        engine.onOrder(100.0, 1, OrderBookEngine.Side.BUY);

        engine.printOrderBook();
        System.out.println(engine.getBBO());


    }

}
