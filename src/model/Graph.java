
package model;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Stack;

/**
 * Basic Graph class where nodes are objects of generic type T which implements
 * Comparable<T> The set of edges is stored in a field of type Map(T,List<T>)
 * 
 * @author CS 3152
 * @version Fall 2018
 *
 */
public class Graph<T extends Comparable<T>> {

	private Map<T, List<T>> edges;

	/**
	 * Creates a new empty Graph<T> object must add nodes and edges using the
	 * interface
	 * 
	 * @preconditions none
	 * @postconditions order() == 0
	 */
	public Graph() {
		this.edges = new TreeMap<T, List<T>>();
	}

	/**
	 * Returns the number of nodes in the graph
	 * 
	 * @return this.edges.KeySet().size()
	 */
	public int order() {
		return this.edges.keySet().size();
	}

	/**
	 * Return the set of nodes
	 * 
	 * @return this.edges.KeySet()
	 */
	public Set<T> nodeSet() {
		return this.edges.keySet();
	}

	/**
	 * Adds a new node to the Graph Throws an exception if the node is already in
	 * the graph
	 * 
	 * @param node
	 * @preconditions node != null
	 * @postconditions order() = order()@prev + 1
	 */
	public void addNode(T node) {
		if (edges.containsKey(node)) {
			throw new IllegalArgumentException("Can not add duplicate node to graph");
		}
		edges.put(node, new ArrayList<T>());
	}

	/**
	 * Returns true if the node is in the graph and false otherwise
	 * 
	 * @param node
	 * @preconditions node != null
	 * @postconditions none
	 * @return this.edges.keySet().contains(node)
	 */
	public boolean containsNode(T node) {
		return this.edges.keySet().contains(node);
	}

	/**
	 * Adds an edge to the graph If either nodes are not already in the graph they
	 * are added
	 * 
	 * @param node0
	 * @param node1
	 */
	public void addEdge(T node0, T node1) {
		if (!containsNode(node0)) {
			addNode(node0);
		}
		if (!containsNode(node1)) {
			addNode(node1);
		}

		if (getNeighbors(node0).contains(node1)) {
			throw new IllegalArgumentException("Can't add duplicate edges");
		}
		this.edges.get(node0).add(node1);
		this.edges.get(node1).add(node0);
	}

	/**
	 * Returns true if the two nodes share an edge
	 * 
	 * @param node0
	 * @param node1
	 * @return
	 */
	public boolean adjacent(T node0, T node1) {
		if (!containsNode(node0) || !containsNode(node1)) {
			throw new IllegalArgumentException("Nodes must be in graph to" + " check for adjacency");
		}
		return edges.get(node0).contains(node1);
	}

	/**
	 * Returns the degree of a node
	 * 
	 * @param node
	 * @return the degree
	 */
	public int degree(T node) {
		if (!containsNode(node)) {
			throw new IllegalArgumentException("Node must be in graph to" + "compute degree");
		}
		return edges.get(node).size();
	}

	/**
	 * Returns the list of nodes adjacent to the input node
	 * 
	 * @param node
	 * @return the list of neighbors of the input node
	 */
	public List<T> getNeighbors(T node) {
		if (!containsNode(node)) {
			throw new IllegalArgumentException("Node must be in graph to" + "provide neighbors");
		}
		Collections.sort(edges.get(node));
		return edges.get(node);
	}

	/**
	 * Returns the list of vertices with maximal degree
	 */
	public List<T> maximalDegreeList() {
		int maximum = 0;
		for (T node : this.nodeSet()) {
			int degree = this.degree(node);
			if (degree > maximum) {
				maximum = degree;
			}
		}
		List<T> maxList = new ArrayList<T>();
		for (T node : this.nodeSet()) {
			if (this.degree(node) == maximum) {
				maxList.add(node);
			}
		}
		return maxList;
	}

	/**
	 * Returns true if the graph is connected and false otherwise
	 * 
	 * @return
	 */
	public boolean isConnected() {

		List<T> l = this.maximalDegreeList();
		T node = l.get(0);

		return this.breadthFirstSearch(node).size() == this.order();
	}

	/**
	 * Returns the BFT as a list starting at the given node
	 * 
	 * @param node
	 * @return
	 */
	public List<T> breadthFirstSearch(T start) {

		Queue<T> q = new LinkedList<T>();
		List<T> list = new ArrayList<T>();
		Set<T> visited = new TreeSet<T>();

		if (!this.containsNode(start)) {
			throw new IllegalArgumentException("BFS error: node not in graph");
		}

		visited.add(start);
		q.add(start);
		while (!q.isEmpty()) {
			T node = q.remove();
			list.add(node);
			for (T neighbor : this.getNeighbors(node)) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					q.add(neighbor);
				}
			}
		}
		return list;
	}

	/**
	 * Returns the DFS starting at the given node
	 * 
	 * @param node
	 * @return
	 */
	public List<T> depthFirstSearch(T start) {

		Stack<T> s = new Stack<T>();
		List<T> list = new ArrayList<T>();
		Set<T> visited = new TreeSet<T>();

		if (!this.containsNode(start)) {
			throw new IllegalArgumentException("DFS error: node not in graph");
		}

		s.push(start);
		while (!s.isEmpty()) {
			T v = s.pop();
			if (!visited.contains(v)) {
				visited.add(v);
				list.add(v);
				for (T neighbor : this.getNeighbors(v)) {
					if (!visited.contains(neighbor)) {
						s.push(neighbor);
					}
				}
			}
		}
		return list;
	}
}
