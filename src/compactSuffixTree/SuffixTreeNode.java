package compactSuffixTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @author Javier Beltran Jorba
 * @author Jorge Cancer Gil
 * 
 * Clase que representa un nodo del arbol. Un nodo tiene asociada su
 * arista superior (o ninguna si es la raiz), junto con informacion
 * sobre los textos en los que se encuentra un sufijo que atraviese 
 * dicho nodo, o la longitud del string resultante desde la raiz hasta
 * este nodo.
 *
 */
public class SuffixTreeNode {
	
	/* Arista procedente del nodo padre hacia este */
	SuffixTreeEdge incomingEdge = null;
	int label = -1;
	ArrayList<Integer> textCount = new ArrayList<Integer>();
	ArrayList<SuffixTreeNode> children = null;
	int stringDepth;
	
	/**
	 * Crea un nodo del arbol.
	 */
	public SuffixTreeNode(String incomingLabel,
	        int label, int numText, int previousDepth) {
	    children = new ArrayList<SuffixTreeNode>();
	    incomingEdge = new SuffixTreeEdge(incomingLabel, label);
	    this.label = label;
	    textCount.add(numText);
	    stringDepth = previousDepth + incomingLabel.length();
	}
	
	/**
	 * Crea un nodo del arbol como raiz.
	 */
	public SuffixTreeNode() {
	    children = new ArrayList<SuffixTreeNode>();
	    label = 0;
	}
	
	/**
	 * Crea un nuevo sufijo como rama del arbol cuya raiz es este nodo.
	 */
	public void addSuffix(List<String> suffix, int pathIndex, int numText) {
	    SuffixTreeNode insertAt = this;
	    insertAt = search(this, suffix, pathIndex);
	    if (suffix.isEmpty()) {
	    	insertAt.textCount.add(numText);
	    } else {
	    	insert(insertAt, suffix, pathIndex, numText);
	    }
	}
	
	/**
	 * Metodo auxiliar para addSuffix(). Busca en que nodo del arbol debe empezar
	 * a desarrollar la nueva rama. Para ello, recorre el arbol buscando
	 * coincidencias ya existentes.
	 */
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
	
	/**
	 * Inserta un nodo en una posicion del arbol.
	 */
	private void insert(SuffixTreeNode insertAt, List<String> suffix,
	        int pathIndex, int numText) {
	    for (String x : suffix) {
	        SuffixTreeNode child = new SuffixTreeNode(x,
	             pathIndex, numText, insertAt.stringDepth);
	        insertAt.children.add(child);
	        insertAt = child;
	    }
	}
	
	/**
	 * Representacion textual del arbol.
	 */
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

	/**
	 * Si el arbol no tiene hijos, devuelve true.
	 */
	public boolean isLeaf() {
	    return children.size() == 0;
	}
}
