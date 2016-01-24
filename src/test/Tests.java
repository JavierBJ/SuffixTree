package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import main.SuffixTree;

public class Tests {
	
	static String[] alfabeto  = {"A","B","C","D","E","F","G","H","I","J","K","L","M"
			,"N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	static String[] alfabetoMinus  = {"a","b","c","d","e","f","g","h","i","j","k","l","m"
			,"n","ñ","o","p","q","r","s","t","u","v","w","x","y","z"};
	
	public static void main(String[] args) {
		
		String t1 = generarTexto(50);
		System.out.println(t1);
		String p1 = generarPatron(t1,5);
		System.out.println(p1);
		//long t = bateriaPruebasStringMatching(10,6);
		long t = bateriaPruebasSubstring(10,100,2);
		System.out.println(t);
		
		
		
		
/*		File gen = new File(args[0]);
		try {
			ArrayList<String> a = readFastaSubstring(gen,100);
			for (String s : a) {
				System.out.println("Texto: "+s);
			}
			long b =  System.currentTimeMillis();
			SuffixTree.substringProblem("GATTACA", a);
			long after =  System.currentTimeMillis();
			System.out.println(after-b);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/		
	}
	
	
	public static ArrayList<String> readFastaSubstring(File gen, int limit) throws FileNotFoundException{
		ArrayList<String> texts = new ArrayList<String>();
		Scanner f = new Scanner(gen);
		String currentText = "";
		int nTexts = 1;
		/*
		 * Avoid the fist line
		 */
		String linea = f.nextLine();
		while(f.hasNextLine()){
			linea = f.nextLine();
			if(!linea.substring(0,1).equals(">")){
				currentText += linea;
			} else {
				texts.add(currentText);
				nTexts++;
				currentText = "";
				if(nTexts>limit || (limit==-1)){
					break;
				}
			}
		}
		return texts;
	}
	
	public String readFastaStringMatching(File gen) throws FileNotFoundException{
		Scanner f = new Scanner(gen);
		String currentText = "";
		int line = 1;
		/*
		 * Avoid the fist line
		 */
		String linea = f.nextLine();
		while(f.hasNextLine()){		
			line++;
			linea = f.nextLine();
			if(!linea.substring(0,1).equals(">")){
				currentText += linea;
			} else {}
		}
		return currentText;
	}
	
	public static String generarTexto(int length){
		String salida = "";		
		for (int i = 0; i<length; i++){
			Random r = new Random();
			salida += alfabetoMinus[r.nextInt(alfabetoMinus.length)];
		}		
		return salida;		
	}
	
	public static String generarPatron(String texto, int lon){
		Random r = new Random();
		int indice = r.nextInt(texto.length()-lon);
		return texto.substring(indice, indice+lon);
	}
	
	public static long bateriaPruebasStringMatching(int lonTexto, int lonPatron){
		final int PRUEBAS = 10;
		long tiempoTotal = 0;
		for (int i = 0; i < PRUEBAS; i++) {
			String t = generarTexto(lonTexto);
			String p = generarPatron(t,lonPatron);
			long b =  System.currentTimeMillis();
			SuffixTree.stringMatching(p, t);
			long after =  System.currentTimeMillis();
			tiempoTotal += (after-b);			
		}
		return tiempoTotal/PRUEBAS;		
	}
	
	public static long bateriaPruebasSubstring(int numTextos, int lonTextos, int lonPatron){
		final int PRUEBAS = 10;
		long tiempoTotal = 0;
		Random r = new Random();
		for (int i = 0; i < PRUEBAS; i++) {
			ArrayList<String> texts = new ArrayList<String>();
			for(int j = 0; j < lonTextos; j++){
				texts.add(generarTexto(lonTextos));
			}
			String p = generarPatron(texts.get(r.nextInt(numTextos)),lonPatron);
			long b =  System.currentTimeMillis();
			SuffixTree.substringProblem(p, texts);
			long after =  System.currentTimeMillis();
			tiempoTotal += (after-b);			
		}
		return tiempoTotal/PRUEBAS;		
	}

}
