package compactSuffixTree;

import java.util.ArrayList;

/**
 * 
 * @author Javier Beltran Jorba
 * @author Jorge Cancer Gil
 * 
 * Clase que representa una arista del arbol. Una arista esta formada
 * por una etiqueta textual y por una lista que indica todos los
 * sufijos que pasan por esa arista.
 *
 */
public class SuffixTreeEdge {
	String label = null;
	ArrayList<Integer> branchIndex = new ArrayList<Integer>();

	public SuffixTreeEdge(String label, int branchIndex) {
	    this.label = label;
	    this.branchIndex.add(branchIndex);
	}
}
