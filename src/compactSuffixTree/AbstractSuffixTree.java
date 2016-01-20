package compactSuffixTree;

import java.util.ArrayList;

public class AbstractSuffixTree {
	
	public String text = null;
	public SuffixTreeNode root = null;
	public int inputAlphabetSize = -1;
	
	/**
	 * Antes de construir el arbol, incluye el simbolo $ al texto si
	 * no esta presente.
	 */
	AbstractSuffixTree(String text) {
	    if (text.length() > 0 && text.charAt(text.length() - 1) == '$') {
	        this.text = text;
	    } else {
	        this.text = text + "$";
	    }
	}
	
	/**
	 * Antes de construir el arbol, incluye el simbolo $ en cada texto
	 * si no esta presente, y concatena todos los textos.
	 */
	AbstractSuffixTree(ArrayList<String> texts) {
		text = "";
		
		/* Concatena el texto seguido de $ */
		for (String text : texts) {
			if (text.length() > 0 && text.charAt(text.length() - 1) == '$') {
		        this.text += text;
		    } else {
		        this.text += text + "$";
		    }
		}
	}
}
