package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.SuffixTree;

public class Tests {
	
	public static void main(String[] args) {
		
		File gen = new File(args[0]);
		try {
			ArrayList<String> a = readFastaSubstring(gen,3);
			for (String s : a) {
				System.out.println("Texto: "+s);
			}
			SuffixTree.substringProblem("GAT", a);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
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

}
