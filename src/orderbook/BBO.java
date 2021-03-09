package orderbook;

public class BBO {

    public double bid_price = 0.0;
    public double bid_quantity = 0.0;
    public double ask_price = 0.0;
    public double ask_quantity = 0.0;

    @Override
    public String toString() {
        return "BBO{" +
                "bid_price=" + bid_price +
                ", bid_quantity=" + bid_quantity +
                ", ask_price=" + ask_price +
                ", ask_quantity=" + ask_quantity +
                '}';
    }
}