package controller;

import model.Graph;
import model.WeightedGraph;

import java.util.Map;
import java.util.Scanner;
import java.io.*;

public class TestGraphExamples {
	
	//TODO
	//REMOVE

	public static void main(String[] args) {

		WeightedGraph<Integer> g = new WeightedGraph<Integer>();

		for (int i = 0; i < 9; i++) {
			g.addNode(i);
		}

		g.addEdge(0, 1, 2.0);
		g.addEdge(0, 2, 4.0);
		g.addEdge(1, 2, 1.0);
		g.addEdge(1, 3, 3.0);
		g.addEdge(3, 4, 1.0);
		g.addEdge(2, 4, 5.0);
		g.addEdge(3, 7, 6.0);
		g.addEdge(7, 8, 2.0);
		g.addEdge(8, 6, 1.0);
		g.addEdge(6, 4, 4.0);
		g.addEdge(4, 5, 1.0);
		g.addEdge(5, 7, 3.0);
		g.addEdge(5, 8, 5.0);

		System.out.println(g.maximalDegreeList());
		System.out.println(g.breadthFirstSearch(6));
		System.out.println(g.isConnected());
		g.addNode(9);
		System.out.println(g.isConnected());
		System.out.println(g.depthFirstSearch(6));

		String filename = "StateAdjacencies.txt";
		Scanner source = null;

		Graph<String> usa = new Graph<String>();

		try {
			source = new Scanner(new File(filename));
		} catch (IOException err) {
			err.printStackTrace();
			System.exit(0);
		}
		while (source.hasNext()) {
			String[] line = source.nextLine().split(",");
			if (!usa.containsNode(line[0])) {
				usa.addNode(line[0]);
			}
			for (int i = 1; i < line.length; i++) {
				if (!usa.containsNode(line[i])) {
					usa.addNode(line[i]);
				}
				if (!usa.adjacent(line[0], line[i])) {
					usa.addEdge(line[0], line[i]);
				}
			}
		}
		source.close();
		System.out.println(usa.maximalDegreeList());
		System.out.println(usa.breadthFirstSearch("GA"));
		
		Map<Integer,Integer> prev = g.dijkstra(0);
		for(Integer node : prev.keySet()) {
			System.out.println(node + "->" + prev.get(node));
		}

	}

}
