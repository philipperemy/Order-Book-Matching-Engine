package orderbook;

import java.util.*;

public class OrderBookEngine {

    public enum Side {BUY, SELL}

    private Map<Double, Integer> bidOffers = new TreeMap<>(Comparator.reverseOrder());

    private Map<Double, Integer> askOffers = new TreeMap<>();


    public void printOrderBook() {
        System.out.println("___ ORDER BOOK ___");
        System.out.println("Asks:");
        List<Double> ask_prices = new ArrayList<>(askOffers.keySet());
        Collections.reverse(ask_prices);
        for (double ask_price : ask_prices) {
            System.out.println(askOffers.get(ask_price) + " @ " + ask_price);
        }

        List<Double> bid_prices = new ArrayList<>(bidOffers.keySet());
        System.out.println("Bids:");
        for (Double bid_price : bid_prices) {
            System.out.println(bidOffers.get(bid_price) + " @ " + bid_price);
        }
        System.out.println("_________________");
    }

    public void onOrder(double price, int quantity, Side side) {
        if (side == Side.BUY) {
            Set<Double> ask_prices = askOffers.keySet();
            List<Double> ask_prices_list = new ArrayList<>(ask_prices);
            for (double ask_price : ask_prices_list) {
                if (quantity > 0 && price >= ask_price) {
                    int ask_quantity = askOffers.get(ask_price);
                    if (quantity >= ask_quantity) {
                        quantity = quantity - ask_quantity;
                        removeAskOrder(ask_price, ask_quantity);
                    } else {
                        removeAskOrder(ask_price, quantity);
                        quantity = 0;
                    }
                    if (quantity == 0) {
                        break;
                    }
                }
            }
            if (quantity > 0) {
                addBidRestingOrder(price, quantity);
            }
        } else {
            Set<Double> bid_prices = bidOffers.keySet();
            List<Double> bid_prices_list = new ArrayList<>(bid_prices);
            for (double bid_price : bid_prices_list) {
                if (quantity > 0 && price <= bid_price) {
                    int bid_quantity = bidOffers.get(bid_price);
                    if (quantity >= bid_quantity) {
                        quantity = quantity - bid_quantity;
                        removeBidOrder(bid_price, bid_quantity);
                    } else {
                        removeBidOrder(bid_price, quantity);
                        quantity = 0;
                    }
                    if (quantity == 0) {
                        break;
                    }
                }

            }
            if (quantity > 0) {
                addAskRestingOffer(price, quantity);
            }
        }
    }


    synchronized void addBidRestingOrder(double price, int quantity) {
        bidOffers.put(price, quantity);
    }

    synchronized void removeBidOrder(double price, int quantity) {
        int lastQuantity = bidOffers.get(price);
        if (lastQuantity == quantity) {
            bidOffers.remove(price);
        } else {
            bidOffers.put(price, lastQuantity - quantity);
        }
    }

    synchronized void addAskRestingOffer(double price, int quantity) {
        askOffers.put(price, quantity);
    }

    public BBO getBBO() {
        BBO bbo = new BBO();
        double bid_price = 0.0;
        double bid_quantity = 0.0;
        double ask_price = 0.0;
        double ask_quantity = 0.0;
        for (double price : bidOffers.keySet()) {
            bbo.bid_price = price;
            bbo.bid_quantity = bidOffers.get(price);
            break;
        }
        for (double price : askOffers.keySet()) {
            bbo.ask_price = price;
            bbo.ask_quantity = askOffers.get(price);
            break;
        }
        return bbo;
    }

    public int getAskLevel() {
        return askOffers.size();
    }

    public int getBidLevel() {
        return bidOffers.size();
    }

    public int getBidQuantity(double bestPrice) {
        int bidQuantity = 0;
        for (double price : bidOffers.keySet()) {
            if (price > bestPrice) {
                bidQuantity += bidOffers.get(price);
            }
        }

        return bidQuantity;
    }

    public int getBidQuantity() {
        return getBidQuantity(Integer.MIN_VALUE);
    }

    public int getAskQuantity() {
        return getAskQuantity(Integer.MAX_VALUE);
    }

    public int getAskQuantity(double bestPrice) {
        int askQuantity = 0;
        for (double price : askOffers.keySet()) {
            if (price < bestPrice) {
                askQuantity += askOffers.get(price);
            }
        }
        return askQuantity;
    }

    synchronized void removeAskOrder(double price, int quantity) {
        int lastQuantity = askOffers.get(price);
        if (lastQuantity == quantity) {
            askOffers.remove(price);
        } else {
            askOffers.put(price, lastQuantity - quantity);
        }
    }

    public void reset() {
        System.out.println("size ask = " + askOffers.size());
        System.out.println("size bid = " + bidOffers.size());
        askOffers.clear();
        bidOffers.clear();
    }

}
