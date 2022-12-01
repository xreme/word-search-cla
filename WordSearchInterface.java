
import java.io.IOException;
import java.util.Scanner;



public class WordSearchInterface{
//this class handles the user input
    public static void main (String[] args){

        System.out.println("--------------------------------"
        +"\nWELCOME: Enter 'Help' for help\n--------------------------------");
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
				System.out.print("\n>");
				continue;
			}
			//QUIT THE THE PROGRAM
            else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT")){
                return;
            }
            //PRINTS THE CURRENT GAME BAORD AND WORDS
            else if (action.equalsIgnoreCase("Print")||action.equalsIgnoreCase("P")){
                wordSearch.printGame();
            }
            //PRINTS THE WORDS IN THE WORD SEARCH
            else if (action.equalsIgnoreCase("PrintWords")){
                System.out.println(wordSearch.getWords());
            }
            // PRINT THE WORDS SEARCH PAGE
            else if (action.equalsIgnoreCase("PrintPage")){
                System.out.println(wordSearch.getTableString());
            }
            //
            else if (action.equalsIgnoreCase("WORD") || action.equalsIgnoreCase("W")){
                System.out.println("ALL WORDS: "+ wordSearch.getWords()+"\n");
                System.out.println("MISSING WORDS:" + wordSearch.getMissingWords()+"\n");
                System.out.println("WORDS FOUND: " + wordSearch.getWordsFound()+"\n");
            }
            //OPEN HELP DIALOGUE
            else if (action.equalsIgnoreCase("HELP")){
                System.out.println("Quit(q): quit \nhe"+ 
                "\nFind(f): Enables find mode, prompts user for word coordinates \n" +
                "\nPrint(p): print out the board" +
                "\nPrintWords: print out the words" +
                "\nPrintPage: print out the page" +
                "\nWord: print out all words, missing words and words found"
                );
            }
            //MAKE A GUESS
            else if(action.equalsIgnoreCase("FIND") || action.equalsIgnoreCase("F")){
                // *******make some error cathing here, make sure that the input coords make sense! *******
                System.out.println("FIND WORD \n");
                System.out.println("Coordinates of First Letter:");
                System.out.print("X: ");
                int x1 = Integer.parseInt(uInput.nextLine());
                System.out.print("Y: ");
                int y1 =  Integer.parseInt(uInput.nextLine());
                
                System.out.println("\n Coordinates of Last Letter:");
                System.out.print("X: ");
                int x2 = Integer.parseInt(uInput.nextLine());
                System.out.print("Y: ");
                int y2 =  Integer.parseInt(uInput.nextLine());

                wordSearch.guessWord(x1, y1, x2, y2 );

                wordSearch.printGame();
            }
            //USER TUTORIAL
            else if (action.equalsIgnoreCase("TUTORIAL")){
                System.out.print("This freature has not been implemented yet...");;
            }
            System.out.print("\n>");
        }
    }
}
