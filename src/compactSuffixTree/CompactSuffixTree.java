package compactSuffixTree;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * 
 * @author Javier Beltran Jorba
 * @author Jorge Cancer Gil
 * 
 * Implementacion del arbol de sufijos compacto, es decir, con sus ramas
 * compactadas y sus etiquetas expresadas como a..b (donde a y b son los
 * indices de inicio y final del substring en el texto) si la etiqueta es
 * suficientemente larga.
 *
 */
public class CompactSuffixTree extends AbstractSuffixTree {
	
	/**
	 * Crea un arbol de sufijos compacto a partir de un arbol de sufijos simple.
	 */
	public CompactSuffixTree(SimpleSuffixTree simpleSuffixTree, int alfabeto) {
	    super(simpleSuffixTree.text);
	    super.root = compact(simpleSuffixTree.root, alfabeto);
	}
	
	/**
	 * Compacta un arbol de sufijos simple.
	 */
	private SuffixTreeNode compact(SuffixTreeNode tree, int alfabeto) {
		SuffixTreeNode root = compactRec(tree);
		return compressLabels(root, alfabeto);
	}
	
	/**
	 * Metodo recursivo para compactar un arbol de sufijos simple.
	 */
	private SuffixTreeNode compactRec(SuffixTreeNode node) {
	    
	    for (SuffixTreeNode child : node.children) {
	    	/* Compacta las ramas que solo tengan un hijo */
	        while (child.children.size() == 1) {
	            SuffixTreeNode grandchild = child.children.iterator().next();
	            /* Une las etiquetas de los 2 nodos compactados */
	            child.incomingEdge.label += grandchild.incomingEdge.label;
	            child.stringDepth += grandchild.incomingEdge.label.length();
	            child.children = grandchild.children;
	            child.textCount = grandchild.textCount;
	        }
	        /* Compacta el siguiente nodo de forma recursiva */
	        child = compactRec(child);
	    }
	    return node;
	}
	
	/**
	 * Dado un arbol de sufijos compacto, comprime las etiquetas de las
	 * aristas de modo que se definan por el indice inicial y final del
	 * texto que contienen.
	 */
	private SuffixTreeNode compressLabels(SuffixTreeNode node, int alfabeto) {
		for (int i=0; i<node.children.size(); i++) {
			SuffixTreeNode child = node.children.get(i);
			
			/* Comprueba si la etiqueta es demasiado grande (length >= Log(n - |E|))*/
			if (child.incomingEdge.label.length() >= Math.log(super.text.length() - alfabeto)) {
				int oldLabelLength = child.incomingEdge.label.length();
				child.incomingEdge.label = 
						Integer.toString(child.incomingEdge.branchIndex.get(0) + node.stringDepth) + ",";
				child.incomingEdge.label += 
						Integer.toString(child.incomingEdge.branchIndex.get(0) + node.stringDepth + oldLabelLength -1);
			}
			child = compressLabels(child, alfabeto);
		}
		return node;
	}
	
	/**
	 * Resuelve el problema de string matching para un texto representado por un
	 * arbol cuya raiz es <node> y un patron <pattern>, devolviendo una lista con
	 * la posicion de cada aparicion del patron en el texto.
	 */
	public ArrayList<Integer> stringMatching(SuffixTreeNode node, String pattern) {
		/* Comprueba cada hijo del nodo */
		ArrayList<SuffixTreeNode> children = node.children;
		for (int i=0; i<children.size(); i++) {
			/*
			 * Obtiene la etiqueta del nodo que estamos explorando. Si es una
			 * etiqueta acortada (min,max), la traduce por la real.
			 */
			String childLabel = children.get(i).incomingEdge.label;
			if (children.get(i).incomingEdge.label.contains(",")) {
				String[] fromTo = children.get(i).incomingEdge.label.split(",");
				int from = Integer.parseInt(fromTo[0]);
				int to = Integer.parseInt(fromTo[1]);
				childLabel = super.text.substring(from-1, to);
			}
			
			if (childLabel.startsWith(pattern)) {
				/* Si un nodo hijo comienza por nuestro patron, tenemos la solucion */
				return findAppearances(children.get(i), new ArrayList<Integer>());
			} else if (pattern.startsWith(childLabel)) {
				/* Si el patron empieza por un nodo hijo, seguimos mirando el resto del patron en ese nodo */
				int index = childLabel.length();
				return stringMatching(children.get(i), pattern.substring(index, pattern.length()));
			}
		}
		/* Si no hay coincidencia, devuelve una lista vacia */
		return new ArrayList<Integer>();
	}
	
	/**
	 * Hace busqueda en profundidad en el subarbol cuya raiz es suffixTreeNode,
	 * y devuelve el label de todos sus nodos hoja para saber las posiciones
	 * en las que aparece un patron.
	 */
	private ArrayList<Integer> findAppearances(SuffixTreeNode suffixTreeNode, ArrayList<Integer> list) {
		if (suffixTreeNode.isLeaf()) {
			/* Si es una hoja, devuelve al padre el valor */
			ArrayList<Integer> element = new ArrayList<Integer>();
			element.add(suffixTreeNode.label);
			return element;
		} else {
			/* Si no es una hoja, junta el valor de todos sus hijos en una lista */
			for (SuffixTreeNode child : suffixTreeNode.children) {
				list.addAll(findAppearances(child, new ArrayList<Integer>()));
			}
			return list;
		}
	}

	/**
	 * Resuelve el problema del substring para un conjunto de textos representados
	 * por un arbol formado concatenando todos los textos, cuya raiz es <node>, y
	 * para un patron <pattern>. Devuelve un listado con el numero identificador
	 * de cada texto en el que aparece el patron (sin repetidos si aparece varias veces).
	 */
	public LinkedHashSet<Integer> substringProblem(SuffixTreeNode node, String pattern) {
		/* Comprueba cada hijo del nodo */
		ArrayList<SuffixTreeNode> children = node.children;
		for (int i=0; i<children.size(); i++) {
			/*
			 * Obtiene la etiqueta del nodo que estamos explorando. Si es una
			 * etiqueta acortada (min,max), la traduce por la real.
			 */
			String childLabel = children.get(i).incomingEdge.label;
			if (children.get(i).incomingEdge.label.contains(",")) {
				String[] fromTo = children.get(i).incomingEdge.label.split(",");
				int from = Integer.parseInt(fromTo[0]);
				int to = Integer.parseInt(fromTo[1]);
				childLabel = super.text.substring(from-1, to);
			}
			
			if (childLabel.startsWith(pattern)) {
				/* Si un nodo hijo comienza por nuestro patron, tenemos la solucion */
				return findTextCoincidences(children.get(i), new LinkedHashSet<Integer>());
			} else if (pattern.startsWith(childLabel)) {
				/* Si el patron empieza por un nodo hijo, seguimos mirando el resto del patron en ese nodo */
				int index = childLabel.length();
				return substringProblem(children.get(i), pattern.substring(index, pattern.length()));
			}
		}
		/* Si no hay coincidencia, devuelve una lista vacia */
		return new LinkedHashSet<Integer>();
	}
	
	/**
	 * Hace busqueda en profundidad en el subarbol cuya raiz es suffixTreeNode,
	 * y devuelve el label de todos sus nodos hoja para saber las posiciones
	 * en las que aparece un patron.
	 */
	private LinkedHashSet<Integer> findTextCoincidences(SuffixTreeNode suffixTreeNode, LinkedHashSet<Integer> list) {
		if (suffixTreeNode.isLeaf()) {
			/* Si es una hoja, devuelve al padre el valor */
			LinkedHashSet<Integer> element = new LinkedHashSet<Integer>();
			element.addAll(suffixTreeNode.textCount);
			return element;
		} else {
			/* Si no es una hoja, junta el valor de todos sus hijos en una lista */
			for (SuffixTreeNode child : suffixTreeNode.children) {
				list.addAll(findTextCoincidences(child, new LinkedHashSet<Integer>()));
			}
			return list;
		}
	}
}
