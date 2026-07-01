package rabinkarp;

public class RK {
	
	//
	// Be sure to look at the write up for this assignment"Career Center at Washington University in St. Louis" <careers@wustl.edu>
	//  so that you get full credit by satisfying all
	//  of its requirements
	//
	
	char[] chars;
	int pointer=0;
	int h=0;
	int m;
	int power;

	/**
	 * Rabin-Karp string matching for a window of the specified size
	 * @param m size of the window
	 */
	public RK(int _m) {
       this.m=_m;
       System.out.println("New one!");
       if(_m==1) System.out.println("KAO!!!");
       chars=new char[m];
       for(int i=0;i<m;i++){
    	 chars[i]='&';   
       }
       power=pow(31,m);
	}
	

	/**
	 * Compute the rolling hash for the previous m-1 characters with d appended.
	 * @param d the next character in the target string
	 * @return
	 */
	public int nextCh(char d) {
		if(m==1) return getValue(d);
		char c=chars[pointer];
		chars[pointer]=d;
		h = (h*31 % 511 - (power % 511)*getValue(c) % 511 + getValue(d) % 511) % 511;
		while(h<0){
			h+=511;
		}
		pointer=(pointer+1) % m;
		System.out.println(h+" at "+pointer+"/"+m);
		return h;
	}
	
	private int pow(int base, int _power){
		int ans=1;
		for(int i=0;i<_power;i++){
			ans=ans*base % 511;
		}
		return ans;
	}
	
	private int getValue(char c){
		if(c=='&'){
			return 0;
		}else{
			return (int) c;
		}
		
	}
}	