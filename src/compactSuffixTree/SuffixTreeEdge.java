package compactSuffixTree;

import java.util.ArrayList;

public class SuffixTreeEdge {
	String label = null;
	ArrayList<Integer> branchIndex = new ArrayList<Integer>();

	public SuffixTreeEdge(String label, int branchIndex) {
	    this.label = label;
	    this.branchIndex.add(branchIndex);
	}
}
