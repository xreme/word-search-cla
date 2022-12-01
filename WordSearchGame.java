import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
//this class does all the heavy lifting and processing

public class WordSearchGame {
    
    private Page page;
    
    public WordSearchGame(){
        int tableSize = 10;
        //make a new pageTable for the letters
        char[][] pageTable = new char[tableSize][tableSize];
       
        // initially fill in the page Table with ?
        for (char[] r: pageTable){
            Arrays.fill(r, '?');
        }
        
        //try to generate a new page 
        try{
           //generate random words
            ArrayList<String> RandomWords = generateWords();

           //genrate a new table witht he random words for the new page
           pageTable = generatePageTable(pageTable, RandomWords);
           
           //set the page with the generated table
           page  = new Page(RandomWords, pageTable);
        }
        catch(IOException exception){
            System.out.print(exception.getMessage());
            System.exit(1);
        }

        printTable(page.getTable());
        System.out.println("WORDS: "+ page.getWords());
    }
    //return a formatted string of the current word search table
    public String getTableString(){
        String TableString = "";
        char[][] pageTable = page.getTable();        

        for(char[] i : pageTable){
            for(char j : i){
                TableString += j+"  ";
            }
            TableString += "\n"; 
        }
        return TableString;
    }
    
    // return a list of words in the board
    public ArrayList<String> getWords(){
        return page.getWords();
    }
    //return a llist of words that need to be found
    public ArrayList<String> getMissingWords(){
        return page.getWordsToFind();
    }
    //return  a list of words that have been found
    public ArrayList<String> getWordsFound(){
        return page.getWordsFound();
    }
    public Page getCurrentPage(){
        return page;
    }

    /**
     * This method will generate random words
     * @return array list of generated words
     */
    private ArrayList<String> generateWords() throws IOException{
        ArrayList<String> wordlList = new ArrayList<String>();
        //read the file and grab 5 random words
        File inputFile = new File("WordList.txt");
        Scanner input = new Scanner(inputFile);
        
        //generate 5 random numbers
        ArrayList<Integer> randomNums = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++){
            randomNums.add(ThreadLocalRandom.current().nextInt(0,5757));
        }
        //sort the array so the words will be collected in order
        Collections.sort(randomNums);
        for(int i = 0; i <= randomNums.get(randomNums.size()-1); i++){
            String word = input.nextLine();
            if(randomNums.contains(i)){
                wordlList.add(word);
                devMsg("WORD LIST-Added word: "+ word);
            }
        }
        input.close();
        return wordlList;
    }
    
    /**
     * 
     * @param pageTable
     * @param wordList
     * @return 2D array
     */
    private char[][] generatePageTable(char[][] pageTable, ArrayList<String> wordList){
        
        //add each individual word to the table
        for(int i = 0; i < wordList.size(); i++ ){
           boolean success = false;
           while (!success){
            success = addWord(pageTable, wordList.get(i));
            if (!success){
                devMsg("Unable to place " + wordList.get(i) );
            } 
           }
        }

        //fill in the gaps with randome letters
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Random l = new Random();

        for(int r=0; r < pageTable.length ; r++){
            for(int c = 0; c < pageTable[r].length; c++){
                if (pageTable[r][c] == '?'){ 
                    //fill in the gaps with random letters +++++++++++++
                    pageTable[r][c] = alphabet.charAt(l.nextInt(alphabet.length()));
                }
            }
        }
        return pageTable;
    }

    public void printGame(){
        System.out.print(" ");
        for(int i = 0; i < page.getTable()[0].length; i++){System.out.print("  " + i);}
        System.out.println("");
        for (int t = 0; t < page.getTable().length; t++){
            System.out.print(t);
            for (char c: page.getTable()[t]){
                System.out.print("  " + c);
            }
            System.out.print("\n");
        }
        for(int i = 0; i < page.getTable().length*3 + 2; i++){
            System.out.print("-");
        }
        System.out.println("\n WORDS TO FIND: \n"+page.getWordsToFind());
    }


    public void printTable(char[][] table){
        System.out.print(" ");
        for(int i = 0; i < table[0].length; i++){System.out.print("  " + i);}
        System.out.println("");
        for (int t = 0; t < table.length; t++){
            System.out.print(t);
            for (char c: table[t]){
                System.out.print("  " + c);
            }
            System.out.print("\n");
        }
        System.out.println("--------------------------------");
    }

    public String getTableFormat(char [][] table){
        String formTable = "\n ";
        for(int i = 0; i < table[0].length; i++){formTable+=("  " + i);}
        formTable += "\n";
        for (int t = 0; t < table.length; t++){
            formTable+=(t);
            for (char c: table[t]){
                formTable += ("  " + c);
            }
            formTable += ("\n");
        }
        formTable += ("--------------------------------");

        return formTable;
    }

    //if enabled will print out generation information
    public void devMsg(String msg){
        boolean enableDevMsg = false;

        if (enableDevMsg){
            System.out.println(msg);
        }
    }

    private Boolean addWord(char[][] table, String word){
        Random rand = new Random();
        //declare a variable for the driection
        ArrayList<String> d = new ArrayList<String>();
        //declare the different directions of which words can be placed
        d.add("UP");
        d.add("DOWN");
        d.add("LEFT");
        d.add("RIGHT");
        d.add("UPR");
        d.add("UPL");
        d.add("DOWNR");
        d.add("DOWNL");
        
        //generate random position on board
        int x = rand.nextInt(table.length);
        int y = rand.nextInt(table.length);
        devMsg("*** Placing Word: " + word + "***");
        devMsg("Random Coords: [" + x +" "+ y+ "]");
        
        //randomly test different orientations of the word on the baord
        Collections.shuffle(d);
        int slopeX = 0;
        int slopeY = 0;

        for(int i = 0; i < d.size(); i++) {
            String direction = d.get(i);
            switch (direction){
                case "UP":
                    //if the word will not fit vertially,break
                    if((y+1)-word.length() < 0){
                        devMsg(direction + ": extends word outside boundries");
                        continue;
                    }
                    //set the branch direction
                    slopeY = -1; 
                    

                break;

                case "DOWN":
                    //if the word will not fit branching down, break
                    if((table.length-y)-word.length()<0){
                        devMsg(direction + ": extends word outside boundries");
                        continue;
                    }
                    //set the branching direction
                    slopeY = 1;
                break;

                case "LEFT":
                    //if the word will not fit branching left, break
                    if((x+1)-word.length() < 0){
                        devMsg(direction + ": extends word outside boundries");
                        continue;
                    }
                    // set the branching direction
                    slopeX = -1;
                break;

                case "RIGHT":
                    //if the word will not fit branching right, break
                    if((table.length-x)-word.length()<0){
                        devMsg(direction + ": extends word outside boundries");
                        continue;
                    }

                    slopeX = 1;
                    break;

                case "UPR":
                    //if the word will not fit branching right, break
                    if((table.length-x)-word.length()<0){
                        devMsg(direction + ": extends word outside boundries");
                        continue;
                    }
                    //if the word will not fit vertially,break
                    if((y+1)-word.length() < 0){
                        devMsg(direction + ": extends word outside boundries");
                        continue;
                    }
                    //set the branching directions
                    slopeY = -1;
                    slopeX = 1;
                break;

                case "UPL":
                    //if the word will not fit branching left, break
                    if((x+1)-word.length() < 0){
                        devMsg(direction + ": extends word outside boundries");
                        continue;
                    }
                    //if the word will not fit vertially,break
                    if((y+1)-word.length() < 0){
                        devMsg(direction + ": extends word outside boundries");
                        continue;
                    }

                    slopeY = -1;
                    slopeX = -1;
                break;
                case "DOWNR":
                    //if the word will not fit branching right, break
                    if((table.length-x)-word.length()<0){
                        devMsg(direction + ": extends word outside boundries");
                        continue;
                    }
                    //if the word will not fit branching down, break
                    if((table.length-y)-word.length()<0){
                        devMsg(direction + ": extends word outside boundries");
                        continue;
                    }
                    slopeY = 1;
                    slopeX = 1;
                break;
                case "DOWNL":
                    //if the word will not fit branching left, break
                    if((x+1)-word.length() < 0){
                        devMsg(direction + ": extends word outside boundries");
                        continue;
                    }
                    //if the word will not fit branching down, break
                    if((table.length-y)-word.length()<0){
                        devMsg(direction + ": extends word outside boundries");
                        continue;
                    }
                    slopeY = 1;
                    slopeX = -1;
                break;
            }
            devMsg("Chosen direction:" + direction);
            devMsg("Trying Placement...");
            return tryPlacement(table, word, slopeX, slopeY, x, y);
        }
        return false;
    }

    private boolean tryPlacement(char[][] table, String word,int slopeX, int slopeY, int x, int y){
        char[][] testTable = new char[table.length][table.length];
        
        //set up the variables
        y-= slopeY;
        x-= slopeX;
        boolean error = false;

        //copy the current table into a temporary test table
        for (int i = 0; i < table.length; i++){
            testTable[i] = table[i].clone();
        }

        //loop through and place word letter by letter
        for(int j = 0; j < word.length(); j++){
            char c = word.charAt(j);
            y += slopeY;
            x += slopeX;
            
            //make sure that word will not overwrite any already placed letters and place letter is not so
            if(testTable[y][x] == '?' || testTable[y][x] == c ){
                testTable[y][x] = c;
                devMsg("Letter placed: " +c);
                //System.out.println(getTableFormat(testTable));
            }
            //if a letter will be overwritten, break out of loop and activate error flag
            else{
                devMsg("*** ERROR: overwrite protection ***");
                error = true;
                break;
            }
        }

        //if there was no error copy the test table into the main table
        if (!error){
            devMsg("*** Word placed: " +  word+" ***");
            for (int i = 0; i < table.length; i++){
                table[i] = testTable[i].clone();
            }
            return true;
        }
        else{
           devMsg("Word not placed");
        }
        
        //devMsg("Test Board");
        //System.out.println(getTableFormat(testTable));
        //System.out.println("Acc Board");
        //printTable(table);
        return false;
    }
    
    public boolean guessWord(int x1, int y1, int x2, int y2){
        char[][] table = page.getTable();
        String guess = "";
        int magnitude = 0;
        int dy = 0;
        int dx = 0;
        
        //deterrmine the slope
         int slope[] = {(y2-y1), (x2 - x1)};
    
        devMsg("("+slope[1]+"/"+slope[0]+")");
        
        //input validation
        if(Math.abs(slope[0]) != Math.abs(slope[1]) && slope[1] != 0 && slope[0] != 0){
            System.out.println("INVALID PATH");
            return false;
        }
        
        if(slope[0] != 0){
            dy = slope[0]/Math.abs(slope[0]);
            magnitude = Math.abs(slope[0]);
        }
        if(slope[1] != 0){
            dx = slope[1]/Math.abs(slope[1]);
            magnitude = Math.abs(slope[1]);
        }

        for(int i = 0; i <= magnitude ; i++){
            guess += table[y1][x1];
        
            x1 += dx;
            y1 += dy;

        }
        if (page.getWords().contains(guess)){
            if(page.getWordsToFind().contains(guess)){
                System.out.println("\nWord Found:" + guess + "\n");
                System.out.println("--------------------------------");
                page.foundWord(guess);
            }
            else{
                System.out.println("\nThe word "+guess+" has already been found \n");
            }
            return true;
        }
        System.out.println("\n'"+guess +"' is not in the word list. \n");
        return false;
    }

}
