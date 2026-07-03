import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import heaps.Decreaser;
import heaps.MinHeap;
import myextension.solved.*;
import myextension.solved.graphs.*;

public class ShortestPaths {
	private final static Integer inf = Integer.MAX_VALUE;
	private HashMap<Vertex, Decreaser<VertexAndTime>> map;
	private HashMap<Vertex, Edge> toEdge;
	private Map<Edge, Duration> timeTable;
	private Vertex startVertex;
	private final DirectedGraph g;
	
	
	//
	// constructor
	//
	public ShortestPaths(DirectedGraph g, Vertex startVertex, Map<Edge,Duration> timeTable) {

		this.map         = new HashMap<Vertex, Decreaser<VertexAndTime>>();
		this.toEdge      = new HashMap<Vertex, Edge>();
		this.timeTable     = timeTable;
		this.startVertex = startVertex;
		this.g           = g;
	}
	
	//
	// this method does all the real work
	//
	public void run() {
		MinHeap<VertexAndTime> pq = new MinHeap<VertexAndTime>(100);
		for (Vertex v : g.vertices()) {
			toEdge.put(v, null);
			VertexAndTime a = new VertexAndTime(v, inf);
			Decreaser<VertexAndTime> d = pq.insert(a);
			map.put(v, d);
		}

		Decreaser<VertexAndTime> startVertTime = map.get(startVertex);
		startVertTime.decrease(startVertTime.getValue().sameVertexNewTime(0));

		// FIXME
		while (!pq.isEmpty()) {
			VertexAndTime vad=pq.extractMin();
			Vertex v=vad.getVertex();
			Iterable<Edge> es=v.edgesFrom();
			for(Edge e: es){
			  Duration dur = timeTable.get(e);	
			  Decreaser<VertexAndTime> d=map.get(e.to);
			  int sta=dur.startTime;
			  int original=d.getValue().getTime();
			  int currentTime=vad.getTime();
			  if(sta>=currentTime){
				int potential=dur.endTime;
				if(potential<original){
					d.decrease(d.getValue().sameVertexNewTime(potential));
					toEdge.put(d.getValue().getVertex(), e);
				}
			  }	
			}
			
		}
		
	}

	//We don't need returnPath() anymore when calling returnValue(), 
	//because our application is different than the original one.
		
	
	/*
	public LinkedList<Edge> returnPath(Vertex endVertex) {
		LinkedList<Edge> path = new LinkedList<Edge>();

		//
		// FIXME
		//
		for(Vertex to=endVertex; to!=startVertex; to=toEdge.get(to).from){
			path.addFirst(toEdge.get(to));
		}

		return path;
	}
	*/

	public int returnValue(Vertex endVertex) {
		//LinkedList<Edge> path = returnPath(endVertex);
		//int pathValue = inf;
		//Edge e=path.getLast();
		//Vertex v=e.to;
		int pathValue=map.get(endVertex).getValue().getTime();
		return pathValue;
		
	}
}