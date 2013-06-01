package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class OrderBookEngine {

	private Map<Double, Integer> bidOffers = new TreeMap<>();
	private Map<Double, Integer> askOffers = new TreeMap<>();
	private List<Pair<Double, Integer>> executedOrders = new ArrayList<>();
	
	public int getExecutedOrdersCount()
	{
		return executedOrders.size();
	}
	
	public void printExecutedBook()
	{
		System.out.println("");
		System.out.println("____ exec ____ ");
		for(Pair<Double, Integer> pair : executedOrders)
		{
			System.out.println(pair.getLeft() + " @ " + pair.getRight());
		}
		System.out.println("______________ ");
	}
	
	public void printOrderBook()
	{
		System.out.println("");
		Set<Double> bid_prices = bidOffers.keySet();
		List<Double> bid_prices_list = new ArrayList<>(bid_prices);
		Collections.reverse(bid_prices_list);
		System.out.println("____ bid ____ ");
		for(Double bid_price : bid_prices_list)
		{
			System.out.println(bid_price + " @ " + bidOffers.get(bid_price));
		}
		
		Set<Double> ask_prices = askOffers.keySet();
		System.out.println("____ ask ____ ");
		for(double ask_price : ask_prices)
		{
			System.out.println(ask_price + " @ " + askOffers.get(ask_price));
		}
		System.out.println("_____________ ");
		
						
	}
	
	public void receiveOrder(double price, int quantity, boolean buy)
	{
		if(buy)
		{
			//BUY
			Set<Double> ask_prices = askOffers.keySet();
			List<Double> ask_prices_list = new ArrayList<>(ask_prices);
			for(double ask_price : ask_prices_list)
			{
				if(quantity > 0 && price >= ask_price)
				{
					int ask_quantity = askOffers.get(ask_price);
					if(quantity >= ask_quantity)
					{
						quantity = quantity - ask_quantity;
						Pair<Double, Integer> pair = new Pair<Double, Integer>(ask_price, ask_quantity);
						executedOrders.add(pair);
						removeAskOrder(ask_price, ask_quantity);
					}
					else
					{
						//quantity < ask_quantity
						Pair<Double, Integer> pair = new Pair<Double, Integer>(ask_price, quantity);
						executedOrders.add(pair);
						removeAskOrder(ask_price, quantity);
						quantity = 0;
					}
				}
			}
			if(quantity > 0)
			{
				addBidOrder(price, quantity);
			}
			
		}
		else
		{
			Set<Double> bid_prices = bidOffers.keySet();
			List<Double> bid_prices_list = new ArrayList<>(bid_prices);
			Collections.reverse(bid_prices_list);
			for(double bid_price : bid_prices_list)
			{
				if(quantity > 0 && price <= bid_price)
				{
					int bid_quantity = bidOffers.get(bid_price);
					if(quantity >= bid_quantity)
					{
						quantity = quantity - bid_quantity;
						Pair<Double, Integer> pair = new Pair<Double, Integer>(bid_price, bid_quantity);
						executedOrders.add(pair);
						removeBidOrder(bid_price, bid_quantity);
					}
					else
					{
						//quantity < bid_quantity
						Pair<Double, Integer> pair = new Pair<Double, Integer>(bid_price, quantity);
						executedOrders.add(pair);
						removeBidOrder(bid_price, quantity);
						quantity = 0;
					}
				}
				
			}

			if(quantity > 0)
			{
				addAskOffer(price, quantity);
			}
		}
	}
	
	
	public synchronized void addBidOrder(double price, int quantity)
	{
		bidOffers.put(price, quantity);
	}
	
	public synchronized void removeBidOrder(double price, int quantity)
	{
		int lastQuantity = bidOffers.get(price);
		if(lastQuantity == quantity)
		{
			bidOffers.remove(price);
		}
		else
		{
			bidOffers.put(price, lastQuantity-quantity);
		}
	}
	
	public synchronized void addAskOffer(double price, int quantity)
	{
		askOffers.put(price, quantity);
	}
	
	public synchronized void removeAskOrder(double price, int quantity)
	{
		int lastQuantity = askOffers.get(price);
		if(lastQuantity == quantity)
		{
			askOffers.remove(price);
		}
		else
		{
			askOffers.put(price, lastQuantity-quantity);
		}
	}
	
}
