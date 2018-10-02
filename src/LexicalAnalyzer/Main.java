package LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;

public class Main{

    /*Global Declarations*/
    /*Constants*/
    private static final int LEXLEN = 100;
    private static final int LINELENGTH = 70;

    /*Variables*/
    private static String charClass;
    private static String token;
    private static String nextToken;

    private static char[] lexeme = new char[LEXLEN];
    private static char nextChar;

    /*Entrance Method*/
    public static void main(String[] args) throws IOException {
        try {
            File f = new File("src/LexicalAnalyzer/lexInput.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                lexeme = splitLine(readLine);
                for (int i =0; i< lexeme.length; i++) {
                    System.out.print(lexeme[i]);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*Reads Line into character array*/
    private static char[] splitLine(String line)
    {
        char[] rtn = new char[100];
        line.getChars(0, line.length(), rtn,0);
        return rtn;
    }
}
