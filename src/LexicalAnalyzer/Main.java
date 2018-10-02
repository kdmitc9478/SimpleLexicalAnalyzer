package LexicalAnalyzer;

import java.io.*;
import java.lang.*;

public class Main{

    /*Global Declarations*/
    /*Constants*/
    private static final int LEXLEN = 100;
    private static final int LINELENGTH = 80;
    private static final String FILEPATH = "src/LexicalAnalyzer/lexInput.txt";

    /*Variables*/
    private static String charClass;
    private static String nextToken;
    private static String line;

    private static File f;

    private static int cIndex = 0;
    private static int lexLen;

    private static char[] lexeme = new char[LEXLEN];
    private static char nextChar;

    //Character Classes
    private static final String LETTER = "LETTER";
    private static final String DIGIT = "DIGIT";
    private static final String UNKNOWN = "";

    //Token Codes
    private static final String INT_LIT = "INT_LIT";
    private static final String IDENT = "IDENT";
    private static final String ASSIGN_OP = "ASSIGN_OP";
    private static final String ADD_OP = "ADD_OP";
    private static final String SUB_OP = "SUB_OP";
    private static final String MULT_OP = "MULT_OP";
    private static final String DIV_OP = "DIV_OP";
    private static final String LEFT_PAREN = "LEFT_PAREN";
    private static final String RIGHT_PAREN = "RIGHT_PAREN";
    private static final String EOL = "END_OF_LINE";
    private static final String EOF = "END_OF_FILE";





    /*Entrance Method
    * Reads file and runs the analyzer*/
    public static void main(String[] args) throws IOException {
        try {
            System.out.println("\n\nKevin Mitchell, CSCI4200-DA, Fall 2018, Lexical Analyzer");
            printNewLine();
            //Check File
            if((f = new File(FILEPATH)) == null)
                System.out.println("Error - cannot open file " + FILEPATH);
            else {
                BufferedReader b = new BufferedReader(new FileReader(f));
                //Begin looping Lines
                while ((line = b.readLine()) != null) {
                    System.out.println("Input is: " + line);
                    resetLine();
                    getChar();
                    do {
                        lex();
                    } while (nextToken != EOL);
                }
                nextToken = EOF;
                lexeme[0] = 'E';
                lexeme[1] = 'O';
                lexeme[2] = 'F';
                lexeme[3] = 0;
                outputLine();
                System.out.println("Lexical analysis of the program is complete!");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }




    /*Begin Controllers*/

    //Manages output and lookup dispatching
    private static int lex(){
        lexLen=0;
        getNonBlank();
        switch (charClass){
            case LETTER:
                addChar();
                getChar();
                while(charClass == LETTER || charClass == DIGIT){
                    addChar();
                    getChar();
                }
                nextToken = IDENT;
                break;
            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT){
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;
            case UNKNOWN:
                lookupToken(nextChar);
                getChar();
                break;
            case EOL:
                nextToken = EOL;
                lexeme[0] = 'E';
                lexeme[1] = 'O';
                lexeme[2] = 'L';
                lexeme[3] = 0;
                break;
        }
        outputLine();
        lexeme = new char[LINELENGTH];
        return 0;
    }




    /*Begin Models*/

    //adds next character to lexeme
    private static void addChar(){
        if (lexLen <= 98){
            lexeme[lexLen++]=nextChar;
            lexeme[lexLen]=0;
        }
        else{
            System.out.println("Error - lexeme is too long");
        }
    }
    //grabs next character and assigns character class
    private static void getChar(){
        if (checkExist()){
            if (isAlpha(nextChar)){
                charClass = LETTER;
            }
            else if (isDigit(nextChar)){
                charClass = DIGIT;
            }
            else{
                charClass = UNKNOWN;
            }
        }
        else {
            charClass = EOL;
        }
    }
    //Returns the index of the next non-blank character in array
    private static void getNonBlank() {
        while (isWhitespace(nextChar))
        {getChar();}
    }

    //Fetches and assigns token identifier
    private static String lookupToken(char ch){
        switch (ch) {
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            case '=':
                addChar();
                nextToken = ASSIGN_OP;
                break;
            default:
                addChar();
                nextToken = EOL;
        }
        return nextToken;
    }

    //checks if Alpha, Digit, or whitespace respectively
    private static boolean isAlpha(char ch){
        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
            return true;
        }
        else{
            return false;
        }
    }
    private static boolean isDigit(char ch){
        if (ch >= '0' && ch <= '9'){
            return true;
        }
        else {
            return false;
        }
    }
    private static boolean isWhitespace(char ch){
        if ((ch == ' ') || (ch =='\n') || (ch == '\t')){
            return true;
        }
        else{
            return false;
        }
    }

    //Checks the existence of the character at the next index.
    private static boolean checkExist() throws java.lang.StringIndexOutOfBoundsException{
        try {
            nextChar = line.charAt(cIndex++);
            return true;
        }
        catch(java.lang.StringIndexOutOfBoundsException e){
            return false;
        }
    }

    //Used to reset line variables in between reads
    private static void resetLine(){
        cIndex = 0;
        lexeme = new char[LEXLEN];
    }




    /*Begin Views*/

    //Just prints a line of * with length equal to LINELENGTH
    private static void printNewLine(){
        String rtn ="";
        for (int i=0;i<LINELENGTH; i++)
            rtn += "*";
        System.out.println(rtn + "\n");
    }

    //outputs a formatted line
   private static void outputLine(){
        if (nextToken == EOL){
            /* Uncomment this block to add an end of line Token
            String output = "Next token is: " + nextToken;
            output += padRight(output);
            output += "Next lexeme is ";
            for (char t :
                    lexeme) {
                output += t;
            }
            System.out.println(output);*/
            printNewLine();
        }


        else {
            String output = "Next token is: " + nextToken;
            output += padRight(output);
            output += "Next lexeme is ";
            for (char t :
                    lexeme) {
                output += t;
            }
            System.out.println(output);
        }
    }

    //provides padding to the outputed lines
    private static String padRight(String line){
        String rtn = "";
        while ((line.length()+rtn.length()) <= LINELENGTH/2){
            rtn+=" ";
        }
        return rtn;
    }
}
