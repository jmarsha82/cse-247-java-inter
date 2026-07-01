package myextension.solved;

import myextension.solved.graphs.*;

/**
 * Aggregate a Vertex and its time from source.
 * Comparisons are based on distance.
 *
 & Cindy Le
 *
 */
public class VertexAndTime implements Comparable<VertexAndTime> {
	
	final private int time;
	final private Vertex vertex;
	
	public VertexAndTime(Vertex v, int t) {
		this.vertex = v;
		this.time = t;
	}

	/**
	 * Use this method when you want to reduce time to generate
	 *     a VertexAndDist object with the same vertex as this one,
	 *     but with the reduced time
	 * @param newdistance the reduced time
	 * @return
	 */
	public VertexAndTime sameVertexNewTime(int t) {
		return new VertexAndTime(vertex, t);
	}
	
	@Override
	public String toString() {
		return "(" + vertex + ", " + time + ")";
	}
	
	public int getTime() {
		return this.time;
	}
	
	public Vertex getVertex() {
		return this.vertex;
	}
	
	//
	// Comparison of this and any other VertexAndTime object
	//   is based only on time from source.  The MinHeap uses
	//   this information as the heap metric of interest.
	//

	@Override
	public int compareTo(VertexAndTime o) {
		Integer oTime     = o.time;
		Integer thisTime = this.time;
		return thisTime.compareTo(oTime);
	}
	
}