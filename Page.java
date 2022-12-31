import java.util.ArrayList;

public class Page {
    //this class handles all game and board instances
    private ArrayList<String> wordList;
    private ArrayList<String> wordsToFind;
    private ArrayList<String> wordsFound = new ArrayList<String>();
    private char[][] pageTable;

    public Page(ArrayList<String> wordlList, char[][] pageTable ){
        this.wordList = wordlList;
        this.pageTable = pageTable;
        this.wordsToFind = (ArrayList<String>) wordlList.clone();
    }
    public char[][] getTable(){
        return pageTable;
    }
    public ArrayList<String> getWords(){
        return wordList;
    }
    public void foundWord(String word){
        wordsToFind.remove(word);
        wordsFound.add(word);
    }
    public ArrayList<String> getWordsToFind(){
        return wordsToFind;
    }
    public ArrayList<String> getWordsFound(){
        return wordsFound;
    }

}

