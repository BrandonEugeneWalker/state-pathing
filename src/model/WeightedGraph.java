package model;

import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Extends the Graph Class to accommodate weighted edges
 * 
 * @author CS3152 Fall2018
 *
 * @param <T>
 */
public class WeightedGraph<T extends Comparable<T>> extends Graph<T> {

	private Map<T, Map<T, Double>> weights;

	public WeightedGraph() {
		super();
		weights = new TreeMap<T, Map<T, Double>>();
	}

	public void setWeight(T node0, T node1, Double w) {

		if (!this.containsNode(node0))
			addNode(node0);
		if (!this.containsNode(node1))
			addNode(node1);
		if (!adjacent(node0, node1))
			addEdge(node0, node1);

		if (!weights.containsKey(node0))
			weights.put(node0, new TreeMap<T, Double>());

		if (!weights.containsKey(node1))
			weights.put(node1, new TreeMap<T, Double>());

		if (!weights.get(node0).containsKey(node1)) {
			weights.get(node0).put(node1, w);
			weights.get(node1).put(node0, w);
		} else {
			weights.get(node0).replace(node1, w);
			weights.get(node1).replace(node0, w);
		}

	}

	/**
	 * Adds an edge to the node.
	 * 
	 * @param node0 the first node
	 * @param node1 the second node
	 * @param w     the weight
	 */
	public void addEdge(T node0, T node1, Double w) {

		setWeight(node0, node1, w);
	}

	/**
	 * Gets and returns the weight between two nodes.
	 * 
	 * @param node0 the first node
	 * @param node1 the second node
	 * @return the weight of the two nodes
	 */
	public Double getWeight(T node0, T node1) {

		if (!weights.containsKey(node0) || !weights.get(node0).containsKey(node1))
			return -1.0;
		return weights.get(node0).get(node1);

	}

	/**
	 * Implements the dijkstra search function.
	 * 
	 * @param start the start
	 * @return a map containing the tree data
	 */
	public Map<T, T> dijkstra(T start) {
		Map<T, Double> dist = new TreeMap<T, Double>();
		Map<T, T> prev = new TreeMap<T, T>();
		Set<T> unprocessed = new TreeSet<T>(this.nodeSet());

		for (T node : this.nodeSet()) {
			dist.put(node, Double.POSITIVE_INFINITY);

		}

		dist.replace(start, 0.0);

		while (!unprocessed.isEmpty()) {
			T u = getMin(unprocessed, dist);
			unprocessed.remove(u);
			for (T v : this.getNeighbors(u)) {
				if (!unprocessed.contains(v)) {
					continue;
				}
				Double value = dist.get(u) + getWeight(u, v);
				if (value < dist.get(v)) {
					dist.replace(v, value);
					if (prev.containsKey(v)) {
						prev.replace(v, u);
					} else {
						prev.put(v, u);
					}
				}
			}

		}
		return prev;
	}

	private T getMin(Set<T> S, Map<T, Double> dist) {
		T min = null;
		if (S.size() == 0) {
			return min;
		}
		for (T element : S) {
			if (min == null) {
				min = element;
				continue;
			}
			if (dist.get(element) < dist.get(min)) {
				min = element;
			}
		}
		return min;
	}

}
