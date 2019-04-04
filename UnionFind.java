package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnionFind {
	private int[] nodes;
	private int[] sizes;

	public UnionFind(int size) {
		nodes = new int[size];
		sizes = new int[size];

		for (int i = 0; i < sizes.length; i++) {
			nodes[i] = i;
			sizes[i] = 1;
		}
	}

	public void union(int nodeOne, int nodeTwo) {
		int nodeOneRoot = root(nodeOne);
		int nodeTwoRoot = root(nodeTwo);

		if (connected(nodeOneRoot, nodeTwoRoot)) {
			return;
		}
		if (sizes[nodeOneRoot] > sizes[nodeTwoRoot]) {
			nodes[nodeTwoRoot] = nodeOneRoot;
			sizes[nodeOneRoot] += sizes[nodeTwoRoot];
			sizes[nodeTwoRoot] = 1;
		} else {
			nodes[nodeOneRoot] = nodeTwoRoot;
			sizes[nodeTwoRoot] += sizes[nodeOneRoot];
			sizes[nodeOneRoot] = 1;
		}
	}

	public int root(int node) {
		while (nodes[node] != node) {
			node = nodes[node];
		}
		return node;
	}

	public boolean connected(int nodeOne, int nodeTwo) {
		return root(nodeOne) == root(nodeTwo);
	}

	public Set<Integer> getRoots(int threshold) {
		Set<Integer> roots = new HashSet<>();
		for (int i = 0; i < sizes.length; i++) {
			if (sizes[i] > threshold) {
				roots.add(i);
			}
		}
		return roots;
	}

	public List<Integer> getNodes(int node) {
		int root = root(node);
		List<Integer> connectedNodes = new ArrayList<>();
		for (int i = 0; i < nodes.length; i++) {
			if (root == root(i)) {
				connectedNodes.add(i);
			}
		}
		return connectedNodes;
	}

}