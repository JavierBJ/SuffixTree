package ukkonenSuffixTree;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class ImprovedSuffixTree {

	public int infinity = Integer.MAX_VALUE;
	private Node[] nodes;
	private char[] text;

	private int root;
	private int position = -1;
	private int currentNode;
	private int needSuffixLink;
	private int remainder;

	private int activeNode;
	private int activeLength;
	private int activeEdge;

	public ImprovedSuffixTree(int length, String line) throws Exception {
		nodes = new Node[2 * length + 2];
		text = new char[length];
		root = activeNode = createNode(-1, -1);
		
		for (int i = 0; i < line.length(); i++) {
			 addChar(line.charAt(i));
		}
           
	}

	private void addSuffixLink(int node) {
		if (needSuffixLink > 0)
			nodes[needSuffixLink].link = node;
		needSuffixLink = node;
	}

	char active_edge() {
		return text[activeEdge];
	}

	boolean walkDown(int next) {
		if (activeLength >= nodes[next].edgeLength()) {
			activeEdge += nodes[next].edgeLength();
			activeLength -= nodes[next].edgeLength();
			activeNode = next;
			return true;
		}
		return false;
	}

	int createNode(int start, int end) {
		nodes[++currentNode] = new Node(start, end, this);
		return currentNode;
	}

	public void addChar(char c) throws Exception {
		text[++position] = c;
		needSuffixLink = -1;
		remainder++;
		while (remainder > 0) {
			if (activeLength == 0)
				activeEdge = position;
			if (!nodes[activeNode].next.containsKey(active_edge())) {
				int leaf = createNode(position, infinity);
				nodes[activeNode].next.put(active_edge(), leaf);
				addSuffixLink(activeNode); // rule 2
			} else {
				int next = nodes[activeNode].next.get(active_edge());
				if (walkDown(next))
					continue; // observation 2
				if (text[nodes[next].start + activeLength] == c) { // observation
																	// 1
					activeLength++;
					addSuffixLink(activeNode); // observation 3
					break;
				}
				int split = createNode(nodes[next].start, nodes[next].start + activeLength);
				nodes[activeNode].next.put(active_edge(), split);
				int leaf = createNode(position, infinity);
				nodes[split].next.put(c, leaf);
				nodes[next].start += activeLength;
				nodes[split].next.put(text[nodes[next].start], next);
				addSuffixLink(split); // rule 2
			}
			remainder--;

			if (activeNode == root && activeLength > 0) { // rule 1
				activeLength--;
				activeEdge = position - remainder + 1;
			} else
				activeNode = nodes[activeNode].link > 0 ? nodes[activeNode].link : root; // rule 3
		}
	}

	public int getPosition() {
		return position;
	}

	/*
	 * printing the Suffix Tree in a format understandable by graphviz. The
	 * output is written into st.dot file. In order to see the suffix tree as a
	 * PNG image, run the following command: dot -Tpng -O st.dot
	 */

	public void printTree(PrintWriter out) {
		out.println("digraph {");
		out.println("\trankdir = LR;");
		out.println("\tedge [arrowsize=0.4,fontsize=10]");
		out.println("\tnode1 [label=\"\",style=filled,fillcolor=lightgrey,shape=circle,width=.1,height=.1];");
		out.println("//------leaves------");
		printLeaves(out, root);
		out.println("//------internal nodes------");
		printInternalNodes(out, root);
		out.println("//------edges------");
		printEdges(out, root);
		out.println("//------suffix links------");
		printSLinks(out, root);
		out.println("}");
	}

	private void printLeaves(PrintWriter out, int x) {
		if (nodes[x].next.size() == 0)
			out.println("\tnode" + x + " [label=\"\",shape=point]");
		else {
			for (int child : nodes[x].next.values())
				printLeaves(out, child);
		}
	}

	private void printInternalNodes(PrintWriter out, int x) {
		if (x != root && nodes[x].next.size() > 0)
			out.println(
					"\tnode" + x + " [label=\"\",style=filled,fillcolor=lightgrey,shape=circle,width=.07,height=.07]");

		for (int child : nodes[x].next.values())
			printInternalNodes(out, child);
	}

	private void printEdges(PrintWriter out, int x) {
		for (int child : nodes[x].next.values()) {
			out.println("\tnode" + x + " -> node" + child + " [label=\"" + edgeString(child) + "\",weight=3]");
			printEdges(out, child);
		}
	}

	private void printSLinks(PrintWriter out, int x) {
		if (nodes[x].link > 0)
			out.println("\tnode" + x + " -> node" + nodes[x].link + " [label=\"\",weight=1,style=dotted]");
		for (int child : nodes[x].next.values())
			printSLinks(out, child);
	}
	
	private String edgeString(int node) {
		return new String(Arrays.copyOfRange(text, nodes[node].start, Math.min(position + 1, nodes[node].end)));
	}
	
	/**
	 * 
	 */
	public ArrayList<Integer> search(String pattern) {
		ArrayList<Integer> results = new ArrayList<Integer>();
		int current = 1;
		for (int i=0; i<pattern.length(); i++) {
			for (int child : nodes[current].next.values()) {
				int interval = nodes[current].end - nodes[current].start;
				String trozo = pattern.substring(i, i+interval);
				String found = text.toString().substring(nodes[current].start, nodes[current].end);
				if (trozo.equals(found)) {
					
				}
			}
		}
		return results;
	}
}