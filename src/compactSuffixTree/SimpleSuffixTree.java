package compactSuffixTree;

import java.util.ArrayList;
import java.util.List;

public class SimpleSuffixTree extends AbstractSuffixTree {
	
	/**
	 * Constructor para string matching
	 */
	public SimpleSuffixTree(String text) {
		/* Incluye caracter final $ si no esta presente*/
		super(text);
		createTree();
	}
	
	/**
	 * Constructor para substring problem
	 */
	public SimpleSuffixTree(ArrayList<String> texts) {
		/* Incluye caracter final $ si no esta presente */
		super(texts);
		createTree();
	}

	/**
	 * Crea un arbol de sufijos simple a partir del texto.
	 */
	private void createTree() {
		/* Crea un arbol de sufijos simple */
		super.root = new SuffixTreeNode();
	    String treeText = super.text;
	    int numText = 1;
	    
	    for (int i = 0; i < treeText.length(); i++) {
	    	/* Increases the count of the text when $ is found */
	    	if (treeText.charAt(i)=='$') {
	    		numText++;
	    	}
	    	
	    	/* Crea una nueva rama para el sufijo i..n */
	        List<String> suffixList = new ArrayList<String>();
	        for (int k = i; k < treeText.length(); k++) {
	            suffixList.add(treeText.charAt(k) + "");
	        }
	        
	        /* Incluye el nuevo sufijo en el arbol */
	        super.root.addSuffix(suffixList, i+1, numText);
	    }
	}
}
