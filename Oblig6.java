import java.util.*;
import java.io.*;
/** This class has three functions; reading the input-file, putting all the words in an array,
 * and start the initial threads
 * @param threadCnt Number of threads to use to sort
 * @param wordCnt Total number of words(gathered from the input-file in the first line)
 * @param input The name of the input-file
 * @param output The name of the output-file
 * @param scan Scanner-object used to read from the input file
 * @param file File-object used for IO handling
 * @param words The array containing all the words which is to be sorted
 */
class Oblig6 {
    protected static int wordCnt;
    private int threadCnt; 
    private String input, output;
    private Scanner scan;
    private File file;
    private String[] words;

    /** Constructor. Saves threadCnt, input and output in aformentioned object-variables*/
    public Oblig6(int threadCnt, String input, String output) {
	this.threadCnt = threadCnt;
	this.input = input;
	this.output = output;		
	
    }
    /** Another constructor*/
    public Oblig6() {

    }
    
    /** Method for reading the input file and putting it in a array(words). Also sets wordCnt*/
    public void readFile() {
	file = new File(input);
	try {
	    scan = new Scanner(file);
	}catch(FileNotFoundException e) {
	    System.out.println("Please choose the right input file");
	    System.out.println("Program will now exit");
	    System.exit(1);
	}

	wordCnt = scan.nextInt();
	words = new String[wordCnt];
   
	for(int i = 0; scan.hasNext(); i++) {
	    if(i > wordCnt-1) {
		System.out.println("There are more words than specified in the input file!");
		System.out.println("Program will now exit. Have a nice day");
		System.exit(1);
	    }
	    words[i] = scan.next();

	}
    }
    
    /** Starting all the threads. The formula for startPos and endPos is needed in cases
     * where all arrays wont be the same size.
     * therefore some random arrays will have +/- 1 indexes, 
     * depending on how many threads are to be used and how many words there are
     */
    public void startThreads() {

	int threadNr = 0; /** Tells us which thread we're starting*/
	
	for(int i = 0; i < threadCnt; i++) {
	    int startPos = (threadNr * wordCnt) / threadCnt; /** Tells us which index is the 
								 starting position for this particular thread*/
	    int endPos =  ((threadNr+1) * wordCnt) / threadCnt; /** Tells us which index is the 
								    ending position for this particular thread*/
	    new Threadsolver(startPos, endPos, threadNr,wordCnt, words).start(); /** Start new thread(s)*/
	    threadNr++;
	}
    }
}
