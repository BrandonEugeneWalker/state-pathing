package controller;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import model.WeightedGraph;

/**
 * This class opens and processes the file containing all of the state adjacencies.
 * @author Brandon Walker
 * @version Oct 6, 2018
 *
 */
public class AdjacenciesProcessor {
	
	/**
	 * Processes the given file into a graph.
	 * @param path the path
	 * @return a graph containing all of the nodes and edges in the file.
	 */
	public static WeightedGraph<String> ProcessAdjacencies(String path){
		WeightedGraph<String> returnGraph = new WeightedGraph<String>();
		Scanner source = null;
		
		try {
			source = new Scanner(new File(path));
		} catch (IOException err) {
			err.printStackTrace();
			System.exit(0);
		}
		while (source.hasNext()) {
			String[] line = source.nextLine().split(",");
			if (!returnGraph.containsNode(line[0])) {
				returnGraph.addNode(line[0]);
			}
			for (int i = 1; i < line.length; i++) {
				if (!returnGraph.containsNode(line[i])) {
					returnGraph.addNode(line[i]);
				}
				if (!returnGraph.adjacent(line[0], line[i])) {
					returnGraph.addEdge(line[0], line[i]);
				}
			}
		}
		source.close();
		
		return returnGraph;
	}

}
