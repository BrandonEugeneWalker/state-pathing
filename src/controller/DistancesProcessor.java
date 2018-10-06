package controller;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import model.WeightedGraph;

/**
 * The DistanceProcessor opens and processes a file containing all of the information about edge weights.
 * @author Brandon Walker
 * @version Oct 6, 2018
 *
 */
public class DistancesProcessor {

	/**
	 * Processes the given file and returns its results.
	 * @param path the path to open
	 * @param workingGraph the processed file results
	 * @return returns a fully processed graph containing nodes, edges, and weights.
	 */
	public static WeightedGraph<String> ProcessWeights(String path, WeightedGraph<String> workingGraph) {
		WeightedGraph<String> returnGraph = workingGraph;
		Scanner source = null;
		
		try {
			source = new Scanner(new File(path));
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(0);
		}
		
		while (source.hasNext()) {
			String[] contents = source.nextLine().split(" ");
			
			String firstNode = contents[0];
			String secondNode = contents[1];
			double weight = Double.parseDouble(contents[2]);
			
			returnGraph.addEdge(firstNode, secondNode, weight);
			
		}
		
		return returnGraph;

	}

}
