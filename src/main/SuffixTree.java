package main;

import java.util.ArrayList;

import compactSuffixTree.CompactSuffixTree;
import compactSuffixTree.SimpleSuffixTree;

public class SuffixTree {
	
	/**
	 * Lee los argumentos del programa y, en funcion de estos, resuelve el problema
	 * de string matching o el problema del substring, y muestra los resultados
	 * por pantalla.
	 */
	public static void main(String[] args) {				
		if (args.length == 2) {
			/* Si solo introduce patron y un texto, resuelve string matching */
			String patron = args[0];
			String texto = args[1];
			stringMatching(patron, texto);
		} else if (args.length > 2) {
			/* Si introduce patron y varios textos, resuelve problema del substring */
			String patron = args[0];
			ArrayList<String> textos = new ArrayList<String>();
			for (int i=1; i<args.length; i++) {
				textos.add(args[i]);
			}
			substringProblem(patron, textos);
		} else {
			System.out.println("Error: debe introducir minimo 2 argumentos (patron y texto/s).");
		}
	}
	
	/**
	 * Resuelve el problema del string matching. Dados un texto y un patron, obtiene todas
	 * las ocurrencias del patron en el texto y las muestra por pantalla, indicando la
	 * posicion en la que empieza el patron en cada ocurrencia (tal que la primera posicion
	 * del texto sea 1).
	 */
	private static void stringMatching(String patron, String texto) {
		System.out.println("Resolviendo String Matching...");
		System.out.println("Texto: " + texto);
		System.out.println("Patron: " + patron);
		
		/* Crea un arbol de sufijos compacto a partir del texto */
		CompactSuffixTree arbol = new CompactSuffixTree(new SimpleSuffixTree(texto));
		//String properties = "rankdir=LR; node[shape=box fillcolor=gray95 style=filled]\n";
		//System.out.println("digraph {\n" + properties + arbol.root + "}");
		
		/* Busca las ocurrencias del patron en el arbol empezando a buscar en la raiz */
		ArrayList<Integer> resultados = arbol.stringMatching(arbol.root, patron);
		
		/* Muestra los resultados por pantalla */
		System.out.println("\tOcurrencias encontradas: " + resultados.size());
		if (resultados.size() > 0) {
			System.out.println("\tPosicion de las ocurrencias: " + resultados.toString());
		}
	}
	
	/**
	 * Resuelve el problema del substring. Dados varios textos y un patron, obtiene todos
	 * los textos en los que aparece el patron y muestra por pantalla cuales son, indicando
	 * el numero de texto segun se han introducido (empezando por el 1).
	 */
	private static void substringProblem(String patron, ArrayList<String> textos) {
		System.out.println("Resolviendo Substring Problem...");
		
		/* Crea un arbol de sufijos compacto a partir de los textos concatenados */
		CompactSuffixTree arbol = new CompactSuffixTree(new SimpleSuffixTree(textos));
		//String properties = "rankdir=LR; node[shape=box fillcolor=gray95 style=filled]\n";
		//System.out.println("digraph {\n" + properties + arbol.root + "}");
		
		/* Busca en cuantos textos aparece el patron */
		ArrayList<Integer> resultados = arbol.substringProblem(arbol.root, patron);
		
		/* Muestra los resultados por pantalla */
		System.out.println("\tEl patron aparece en " + resultados.size() + " textos.");
		if (resultados.size() > 0) {
			System.out.println("\tEl patron aparece en los textos: " + resultados.toString());
		}
	}
}
