/*LexicalAnalyzer.java -a lexical analyzer system for simple arithmetic expressions */
import java.io.*;
import java.lang.*;
public class LexicalAnalyzer{
/* Global declarations */
/* Variables */
   private static String charClass;
   private static char[] lexeme = new char[98];
   private static char nextChar;
   private static int lexLen;
   private static String nextToken;
   private static File infp;
   private static String line;
   private static int cIndex = 0;
/* Character classes */
   private static final String LETTER = "LETTER";
   private static final String DIGIT = "DIGIT";
   private static final String UNKNOWN = "";
   /* Token codes */
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
   /******************************************************/
   /* main driver */
   public static void main(String[] args) throws IOException {
      try {
       /* Open the input data file and process its contents */
         System.out.println("\n\nCameron Smith, CSCI4200-DA, Fall 2018, Lexical Analyzer");
         printNewLine();
         if((infp = new File("C:/Users/camer/OneDrive/Documents/LexicalAnalyzer/lexInput.txt")) == null)
            System.out.println("Error - cannot open file 'lexInput.txt' \n");
         else {
            BufferedReader b = new BufferedReader(new FileReader(infp));
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
/****************************************************/
/* lookup -a function to lookup operators and parenthesesand return the token */
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

/*****************************************************/
/* addChar -a function to add nextChar to lexeme */
   private static void addChar(){
      if (lexLen <= 98){
         lexeme[lexLen++] = nextChar;
         lexeme[lexLen] = 0;
      }
      else{
         System.out.println("Error - lexeme is too long \n");
      }
   }
/****************************************************/
/* getChar -a function to get the next character of 
   input and determine its character class */
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
/****************************************************/
/* uses boolean to checks if Alpha, Digit, or whitespace */
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
   private static boolean isspace(char ch){
      if ((ch == ' ') || (ch =='\n') || (ch == '\t')){
         return true;
      }
      else{
         return false;
      }
   }
/****************************************************/
/* getNonBlank-a function to call getChar until 
   itreturns a non-whitespace character */
   private static void getNonBlank() {
      while (isspace(nextChar))
      {getChar();}
   }
/*****************************************************/
/* lex-a simple lexical analyzer for arithmetic 
   expressions */
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
      /* Parse integer literals */
         case DIGIT:
            addChar();
            getChar();
            while (charClass == DIGIT){
               addChar();
               getChar();
            }
            nextToken = INT_LIT;
            break;
      /* Parentheses and operators */
         case UNKNOWN:
            lookupToken(nextChar);
            getChar();
            break;
      /* EOF */
         case EOL:
            nextToken = EOL;
            lexeme[0] = 'E';
            lexeme[1] = 'O';
            lexeme[2] = 'L';
            lexeme[3] = 0;
            break;
      }
      outputLine();
      lexeme = new char[80];
      return 0;
   }
/* End of switch */
   private static void outputLine(){
      String output = "Next token is: " + nextToken;
      output += align(output);
      output += "Next lexeme is ";
      for (int t = 0; t < lexeme.length && lexeme[t] != 0; t++) {
         output += lexeme[t];
      }
      System.out.println(output);
   }
/* End of function lex */
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
      lexeme = new char[98];
   }
   /*Begin Views*/
   //Just prints a line of * with length equal to LINELENGTH
   private static void printNewLine(){
      String ofTheJedi ="";
      for (int i = 0;i < 80; i++)
         ofTheJedi += "*";
      System.out.println(ofTheJedi + "\n");
   }
   private static String align(String line){
      String ofTheJedi = "";
      while ((line.length() + ofTheJedi.length()) <= 80/2){
         ofTheJedi += " ";
      }
      return ofTheJedi;
   }
}
