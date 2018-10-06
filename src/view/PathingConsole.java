package view;

import java.util.Map;
import java.util.Scanner;

import controller.AdjacenciesProcessor;
import controller.DistancesProcessor;
import model.WeightedGraph;

/**
 * Controls the flow of the console application.
 * 
 * @author Brandon Walker
 * @version Oct 6, 2018
 *
 */
public class PathingConsole {

	private static final String GREETING = "Welcome to the State Pathing Application.";
	private static final String INPUT_FIRST_STATE = "Please input the starting state:";
	private static final String INPUT_SECOND_STATE = "Please input the destination state:";

	/**
	 * The entry point to the application. Asks the user for information and then
	 * processes it using other classes.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String adjacenciesPath = "src/StateAdjacencies.txt";
		String distancesPath = "src/StateCapitalDistances.txt";

		WeightedGraph<String> adjacenciesGraph = AdjacenciesProcessor.ProcessAdjacencies(adjacenciesPath);
		WeightedGraph<String> stateGraph = DistancesProcessor.ProcessWeights(distancesPath, adjacenciesGraph);

		System.out.println(GREETING);

		System.out.println(INPUT_FIRST_STATE);
		String firstStateNode = scan.nextLine();

		System.out.println(INPUT_SECOND_STATE);
		String secondStateNode = scan.nextLine();

		Map<String, String> pathResults = stateGraph.dijkstra(secondStateNode);

		System.out.println("The results are:");

		String currentNode = firstStateNode;
		while (!currentNode.equals(secondStateNode) && currentNode != null) {
			System.out.print(currentNode);
			System.out.print(" -> ");

			currentNode = pathResults.get(currentNode);
		}
		System.out.print(secondStateNode);

	}

}
