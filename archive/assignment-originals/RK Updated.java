package rabinkarp;

public class RK {

	//
	// Be sure to look at the write up for this assignment
	//  so that you get full credit by satisfying all
	//  of its requirements
	//


	char[] targetString;
	int pointer = 0;
	int h = 0;
	int instanceM;
	int instancePower;

	/**
	 * Rabin-Karp string matching for a window of the specified size
	 * @param m size of the window
	 */
	public RK(int m) {
		this.instanceM = m;
		//System.out.println("New one!");
		//if(_m==1) System.out.println("KAO!!!");
		targetString = new char[instanceM];
		for(int i = 0;i < instanceM;i++){
			targetString[i] = '-';   
		}
		instancePower = pow(31,instanceM);
	}


	/**
	 * Compute the rolling hash for the previous m-1 characters with d appended.
	 * @param d the next character in the target string
	 * @return
	 */
	public int nextCh(char d) {
		if(instanceM == 1) {
			return getValue(d);
		}
		else{
			char c = targetString[pointer];
			targetString[pointer] = d;
			//h = (h×31 – 31^m×c + d) mod 511
			h = (h*31 % 511 - (instancePower % 511)*getValue(c) % 511 + getValue(d) % 511) % 511;
			while(h<0){
				h += 511;
			}
			pointer = (pointer+1) % instanceM;
			//System.out.println(h+" at "+pointer+"/"+m);
			return h;
		}
	}

	private int pow(int base, int power){
		int ans = 1;
		for(int i = 0;i < power;i++){
			ans = ans*base % 511;
		}
		return ans;
	}

	private int getValue(char c){
		if(c == '-'){
			return 0;
		}
		else{
			return (int) c;
		}

	}
}
