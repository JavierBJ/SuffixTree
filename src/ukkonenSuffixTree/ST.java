package ukkonenSuffixTree;

import java.io.*;

public class ST {

    public static void main(String ... args) throws Exception{
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new FileWriter("st.dot"));
        String line = in.readLine();
        ImprovedSuffixTree st = new ImprovedSuffixTree(line.length(), line);
        st.printTree(out);
        out.close();
    }
}