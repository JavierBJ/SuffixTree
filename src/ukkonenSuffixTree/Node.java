package ukkonenSuffixTree;

import java.util.TreeMap;

public class Node {
	
	final int infinity = Integer.MAX_VALUE;
    int start, end = infinity, link;
    public TreeMap<Character, Integer> next = new TreeMap<Character, Integer>();
    ImprovedSuffixTree tree;

    public Node(int start, int end, ImprovedSuffixTree tree) {
        this.start = start;
        this.end = end;
        this.tree = tree;
    }

    public int edgeLength() {
        return Math.min(end, tree.getPosition() + 1) - start;
    }
}
