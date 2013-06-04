import java.util.*;
import java.io.*;

/** This class act as an container(monitor) for the array's
 * @param wordCnt Number of words in the file array
 * @param OUTFILE Name of the outfile
 * @param br BufferedWriter-object which are used to write out the sorted array to the OUTFILE
 * @param timerEnd A timer used with timerStart to determine how long it took to sort the array
 * @param previousArray The previous array which was added. Used for merging
 */
class Container {

    private int wordCnt = new Oblig6().wordCnt;
    private final String OUTFILE = new Sort().output;
    private BufferedWriter br;
    private String[] words2;
    private long timerEnd;
    private String[] previousArray = null;

    public Container() {

    }

    /** This method ads array's to the container. The last one is stored in previousArray, all other are "dropped")
     * The array will grow larger and larger, depending on how many array's have been combined.
     * When the array is large enough
     * (the same size as wordCnt), we're done and we can write the solutions to specified file.
     */
    synchronized public void add(String[] array) {
	
	if(wordCnt == array.length) { /** Which means that the array is complete and all words are sorted*/
	    if(array[wordCnt-1] == null) { /** Check to see if last index in the sorted
					    * array == null as assignment specified.*/
		System.out.println("Last element in the array is 'null'. Program will now exit");
		System.exit(1);
	    }

	    try {
		br = new BufferedWriter(new FileWriter(OUTFILE));
		
		for(String s: array) { /** Iterate through the array*/
		    br.write(s + "\n"); /** Writes the solutions to file*/
		}
		
		br.close(); /** Close the file for writing*/
     
	        timerEnd = System.nanoTime();
		System.out.println("The program took " + (timerEnd - new Sort().timerStart)/ 1000000000.0
				   + " to sort all the words");
		System.exit(0); /** Exit the program*/
	    
	    }catch(Exception e) {
		System.out.println("ERROR");
	    }
	} // End of "if (wordCnt == array.length)"

	        
        if(previousArray != null)  { /** In other words; there are an array to combine with this array*/
	    new Threadsolver(array, previousArray).start();
	    previousArray = null;
	} else /** If it's only one array. Which means you have to wait till the next time add are called,
		*  so we can compare two arrays*/
	    previousArray = array;
    }
}
