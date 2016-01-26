package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

import compactSuffixTree.CompactSuffixTree;
import compactSuffixTree.SimpleSuffixTree;

/**
 * 
 * @author Javier Beltran Jorba
 * @author Jorge Cancer Gil
 * 
 * Clase principal del programa. Lee los argumentos de la entrada estandar
 * y resuelve el string matching o el problema del substring.
 *
 */
public class SuffixTree {
	
	/**
	 * Lee los argumentos del programa y, en funcion de estos, resuelve el problema
	 * de string matching o el problema del substring, y muestra los resultados
	 * por pantalla.
	 */
	public static void main(String[] args) {				
		if (args.length == 3) {
			/* Si solo introduce patron y un texto, resuelve string matching */
			int alfabeto = Integer.parseInt(args[0]);
			String patron = args[1];
			String texto = args[2];
			stringMatching(patron, texto, alfabeto);
		} else if (args.length > 3) {
			/* Si introduce patron y varios textos, resuelve problema del substring */
			int alfabeto = Integer.parseInt(args[0]);
			String patron = args[1];
			ArrayList<String> textos = new ArrayList<String>();
			for (int i=2; i<args.length; i++) {
				textos.add(args[i]);
			}
			substringProblem(patron, textos, alfabeto);
		} else {
			System.out.println("Error: debe introducir minimo 3 argumentos (long. alfabeto, patron y texto/s).");
		}
	}
	
	/**
	 * Resuelve el problema del string matching. Dados un texto y un patron, obtiene todas
	 * las ocurrencias del patron en el texto y las muestra por pantalla, indicando la
	 * posicion en la que empieza el patron en cada ocurrencia (tal que la primera posicion
	 * del texto sea 1).
	 */
	public static void stringMatching(String patron, String texto, int alfabeto) {
		System.out.println("Resolviendo String Matching...");
		System.out.println("Texto: " + texto);
		System.out.println("Patron: " + patron);
		
		/* Crea un arbol de sufijos compacto a partir del texto */
		CompactSuffixTree arbol = new CompactSuffixTree(new SimpleSuffixTree(texto), alfabeto);
		
		/* Busca las ocurrencias del patron en el arbol empezando a buscar en la raiz */
		ArrayList<Integer> resultados = arbol.stringMatching(arbol.root, patron);
		Collections.sort(resultados);
		
		/* Muestra los resultados por pantalla */
		System.out.println("\tOcurrencias encontradas: " + resultados.size());
		if (resultados.size() > 0) {
			System.out.println("\tPosicion de las ocurrencias: " + resultados.toString());
		}
	}
	
	/**
	 * Resuelve el problema del string matching. Dados un texto y un patron, obtiene todas
	 * las ocurrencias del patron en el texto y las muestra por pantalla, indicando la
	 * posicion en la que empieza el patron en cada ocurrencia (tal que la primera posicion
	 * del texto sea 1).
	 */
	public static void stringMatchingTest(String patron, String texto, int alfabeto) {
		/* Crea un arbol de sufijos compacto a partir del texto */
		CompactSuffixTree arbol = new CompactSuffixTree(new SimpleSuffixTree(texto), alfabeto);
		
		/* Busca las ocurrencias del patron en el arbol empezando a buscar en la raiz */
		ArrayList<Integer> resultados = arbol.stringMatching(arbol.root, patron);
		Collections.sort(resultados);
	}
	
	/**
	 * Resuelve el problema del substring. Dados varios textos y un patron, obtiene todos
	 * los textos en los que aparece el patron y muestra por pantalla cuales son, indicando
	 * el numero de texto segun se han introducido (empezando por el 1).
	 */
	public static void substringProblem(String patron, ArrayList<String> textos, int alfabeto) {
		System.out.println("Resolviendo Substring Problem...");
		System.out.println("Patron: " + patron);
		for (int i = 0; i < textos.size(); i++) {
			System.out.println("Texto " + (i+1) + " : " + textos.get(i) );
		}
		
		/* Crea un arbol de sufijos compacto a partir de los textos concatenados */
		CompactSuffixTree arbol = new CompactSuffixTree(new SimpleSuffixTree(textos), alfabeto);
		
		/* Busca en cuantos textos aparece el patron */
		LinkedHashSet<Integer> resultados = arbol.substringProblem(arbol.root, patron);
		
		/* Muestra los resultados por pantalla */
		System.out.println("\tEl patron aparece en " + resultados.size() + " textos.");
		if (resultados.size() > 0) {
			System.out.println("\tEl patron aparece en los textos: " + resultados.toString());
		}
	}
	
	/**
	 * Resuelve el problema del substring. Dados varios textos y un patron, obtiene todos
	 * los textos en los que aparece el patron y muestra por pantalla cuales son, indicando
	 * el numero de texto segun se han introducido (empezando por el 1).
	 */
	public static void substringProblemTest(String patron, ArrayList<String> textos, int alfabeto) {
		/* Crea un arbol de sufijos compacto a partir de los textos concatenados */
		CompactSuffixTree arbol = new CompactSuffixTree(new SimpleSuffixTree(textos), alfabeto);
		
		/* Busca en cuantos textos aparece el patron */
		arbol.substringProblem(arbol.root, patron);
	}
}
