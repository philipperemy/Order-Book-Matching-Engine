## Order Book Matching Engine (Low Latency)

Latency to match an order with a thick order book: *~1us*.

No dependencies required (except Java).

### Example

```java
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
```

```
___ ORDER BOOK ___
Asks:
90 @ 100.1
50 @ 100.05
999 @ 100.0
Bids:
1 @ 99.97
100 @ 99.95
50 @ 99.9
50 @ 99.85
_________________
BBO{bid_price=99.97, bid_quantity=1.0, ask_price=100.0, ask_quantity=999.0}
```
