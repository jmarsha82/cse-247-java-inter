	
public class Duration {
		
	public final int startTime, endTime;
		
	public Duration (int staT, int endT) {
		this.startTime = staT;
		this.endTime   = endT;
	}
		
	public String toString() {
		return "Duration " + startTime + " - " + endTime;
	}

}