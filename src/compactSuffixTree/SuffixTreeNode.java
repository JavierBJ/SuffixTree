package compactSuffixTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SuffixTreeNode {
	SuffixTreeEdge incomingEdge = null;
	int label = -1;
	ArrayList<Integer> textCount = new ArrayList<Integer>();
	ArrayList<SuffixTreeNode> children = null;
	int stringDepth;
	    
	public SuffixTreeNode(String incomingLabel,
	        int label, int numText, int previousDepth) {
	    children = new ArrayList<SuffixTreeNode>();
	    incomingEdge = new SuffixTreeEdge(incomingLabel, label);
	    this.label = label;
	    textCount.add(numText);
	    stringDepth = previousDepth + incomingLabel.length();
	}
	
	public SuffixTreeNode() {
	    children = new ArrayList<SuffixTreeNode>();
	    label = 0;
	}
	
	public void addSuffix(List<String> suffix, int pathIndex, int numText) {
	    SuffixTreeNode insertAt = this;
	    insertAt = search(this, suffix, pathIndex);
	    if (suffix.isEmpty()) {
	    	insertAt.textCount.add(numText);
	    } else {
	    	insert(insertAt, suffix, pathIndex, numText);
	    }
	}
	
	private SuffixTreeNode search(SuffixTreeNode startNode, List<String> suffix, int pathIndex) {
	    if (suffix.isEmpty()) {
	        throw new IllegalArgumentException(
	                "Empty suffix. Probably no valid simple suffix tree exists for the input.");
	    }
	    Collection<SuffixTreeNode> children = startNode.children;
	    for (SuffixTreeNode child : children) {
	        if (child.incomingEdge.label.equals(suffix.get(0))) {
	        	child.incomingEdge.branchIndex.add(pathIndex);
	            suffix.remove(0);
	            if (suffix.isEmpty()) {
	                return child;
	            }
	            return search(child, suffix, pathIndex);
	        }
	    }
	    return startNode;
	}
	
	private void insert(SuffixTreeNode insertAt, List<String> suffix,
	        int pathIndex, int numText) {
	    for (String x : suffix) {
	        SuffixTreeNode child = new SuffixTreeNode(x,
	             pathIndex, numText, insertAt.stringDepth);
	        insertAt.children.add(child);
	        insertAt = child;
	    }
	}
	
	public String toString() {
	    StringBuilder result = new StringBuilder();
	    String incomingLabel = this.stringDepth == 0 ? "" : this.incomingEdge.label + "//" + 
	    		this.incomingEdge.branchIndex + "//" + this.label + "//" + this.textCount;

	    result.append("[label=\"" + incomingLabel + "\"];\n");
	    for (SuffixTreeNode child : children) {
	        result.append(child.toString());
	    }
	    return result.toString();
	}

	public boolean isLeaf() {
	    return children.size() == 0;
	}
}
