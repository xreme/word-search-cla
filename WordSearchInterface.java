import java.io.IOException;
import java.util.Scanner;
import javax.lang.model.util.ElementScanner14;

public class WordSearchInterface{
//this class handles the user input
    public static void main (String[] args){
        System.out.print("\033[H\033[2J");

        //create the wordsearch game
        WordSearchGame wordSearch = new WordSearchGame();
        
        //create new Scanner
        Scanner uInput = new Scanner(System.in);
        System.out.print(">");

        //process the input
        while (uInput.hasNextLine()){
            String action  = uInput.nextLine();
            if (action == null || action.equals("")) 
			{
                wordSearch.refresh("");
				//System.out.print("\n>");
				continue;
			}
            //OPEN HELP DIALOGUE
            else if (action.equalsIgnoreCase("HELP")){
                String HelpString = "\n---Commands---"+
                "\nQuit(q): Quit the game \n"+
                "\nReset(r): Reset the word search with new words\n"+ 
                "\nFind(f): Find a word, prompts user for the coordinates of \n         the first and last letter of a word\n" +
                "\nPrint(p): Display the current word search page and words\n" +
                "\nWords(w): Print out all the words in the wordsearch\n" +
                "\nWords+(w+): Print out all the words, missing words, and found words\n";
                
                wordSearch.refresh(HelpString);
            }
			//QUIT THE THE PROGRAM
            else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT")){
                return;
            }
            //Reset
            else if (action.equalsIgnoreCase("R")||action.equalsIgnoreCase("Reset")){
                wordSearch.clearScreen();
                System.out.print("ARE YOU SURE YOU WANT TO RESET?(Y/N)\n>");
                String confirm = uInput.nextLine();
                if (confirm.equalsIgnoreCase("Y")) {
                    wordSearch.clearScreen();
                    wordSearch = new WordSearchGame();
                    System.out.print(">");
                }
                else if (confirm.equalsIgnoreCase("N")){
                    wordSearch.refresh("\nReset Cancelled");
                }
                else{
                    wordSearch.refresh("\nInvalid Input");
                }
            }
            //PRINTS THE CURRENT GAME BAORD AND WORDS
            else if (action.equalsIgnoreCase("Print")||action.equalsIgnoreCase("P")){
                wordSearch.printGame();
            }
            //PRINTS THE WORDS IN THE WORD SEARCH
            else if (action.equalsIgnoreCase("WORDS")||action.equalsIgnoreCase("W")){
                wordSearch.refresh("\nALL WORDS: "+wordSearch.getWords().toString());
            }
            //PRINT ALL WORDS, MISSING WORDS, AND WORDS FOUND
            else if (action.equalsIgnoreCase("WORDS+") || action.equalsIgnoreCase("W+")){
                wordSearch.refresh("\nALL WORDS: \n"+ wordSearch.getWords()+"\n\nMISSING WORDS:\n" + wordSearch.getMissingWords()
                +"\n\nWORDS FOUND: \n" + wordSearch.getWordsFound()+"\n");
                
               /*  System.out.println("ALL WORDS: "+ wordSearch.getWords()+"\n");
                System.out.println("\nMISSING WORDS:" + wordSearch.getMissingWords()+"\n");
                System.out.println("\nWORDS FOUND: " + wordSearch.getWordsFound()+"\n"); */
            }
            //FIND A WORD
            else if(action.equalsIgnoreCase("FIND") || action.equalsIgnoreCase("F")){
               
                int x1,x2,y1,y2;
                wordSearch.refresh("\nFIND WORD \n");              
                try{
                    System.out.println(" Coordinates of First Letter:");
                    System.out.print("X: ");
                    x1 = Integer.parseInt(uInput.nextLine());
                    System.out.print("Y: ");
                    y1 =  Integer.parseInt(uInput.nextLine());
                    
                    System.out.println("\n Coordinates of Last Letter:");
                    System.out.print("X: ");
                    x2 = Integer.parseInt(uInput.nextLine());
                    System.out.print("Y: ");
                    y2 =  Integer.parseInt(uInput.nextLine());
                }
                catch(java.lang.NumberFormatException exception){
                    wordSearch.refresh("\nINVALID INPUT ERROR: Non-integer, \nPlease Input Integer(0-9)\n");
                    continue;
                }              
                try{
                    wordSearch.findWord(x1, y1, x2, y2);
                }
                catch (java.lang.ArrayIndexOutOfBoundsException exception){
                    wordSearch.refresh("\nINVALID INPUT ERROR: Guess is out of bounds");
                    continue;
                }
            }
            //USER TUTORIAL
            else if (action.equalsIgnoreCase("TUTORIAL")){
                System.out.print("This freature has not been implemented yet...");;
            }
            else{
                wordSearch.refresh("\n Invalid Input");
            }
            continue;
            //System.out.print("\n>");
        }
    }
}
