package src;


public class RandomInt {

	final int MAX = 4096;
	private int[] randoms = new int[MAX];
	
	public void initialize() {
		for(int i=0; i<MAX; i++) {
			randoms[i] = getRandomInt(); 
		}
	}
	

	private int getRandomInt()
	{
		return (int)(Math.random() * 10000);
	}
	
	int counter = 0;
	
	public int nextInt() {
		if(counter == MAX-1) {
			counter = 0;
		}
		else {
			++counter;
		}
		
		return randoms[counter];
	}

}
