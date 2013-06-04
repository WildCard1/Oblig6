import java.util.*;
/** The Threadsolver-class which is used for creating new thread
 * @param c A container(monitor)-object which stores the sorted arrays in for further use
 * @param startPos Starting position(index) for this particular thread
 * @param endPos Ending position(index) for this particular thread
 * @param threadNr This threads nr
 * @param wordCnt Number of words in total
 * @param words The words to sort, in a array
 * @param solve A boolean who tells us if we should sort or merge the array
 * @param sortedArray An array which contains sorted words. Used for merging with sortedArray1
 * @param sortedArray1 An array which contains sorted words. Used for merging with sortedArray
 */
class Threadsolver extends Thread {
    protected boolean solve = true;
    private static Container c = new Container();
    private int startPos, endPos, threadNr, wordCnt;
    private String words[];
    private String[] sortedArray, sortedArray1;

    /** Constructor which assigns variables to object-variables. Used when wanting to solve*/ 
    public Threadsolver(int startPos, int endPos, int threadNr, int wordCnt, String words[]) {
	this.startPos = startPos;
	this.endPos = endPos;
	this.threadNr = threadNr;
	this.words = words;
	this.wordCnt = wordCnt;
	
    }

    /** Constructor which assigns the arrays to object-variables. Sets solve to false, 
	since this is used when we're merging*/
    public Threadsolver(String[] sortedArray, String[] sortedArray1) {
	this.sortedArray = sortedArray;
	this.sortedArray1 = sortedArray1;
	solve = false;
    }

    /** The method which are called when starting a thread.
	Is called through the start-method, inherited by Thread.*/
    public void run() {
	if(solve)
	    solve();
	else {
	    combine();
	}
    }

    /** The solving algorithm. Pretty straight-forward bubble-sort*/
    public void solve() {

	String[] tmp = new String[endPos-startPos]; /** Creates a temporarly new array, 
							which the sorted array is stored in*/
	int cnt = 0;
	
	for(int i = startPos; i < endPos; i++) { /** Run while there are still more indexes left*/
	    tmp[cnt] = words[i]; /** Assign all the needed words from words to the tmp-array*/
	    cnt++;
	}
	
	boolean swapped = true;

	while(swapped) {
	    swapped = false;
	    for(int i = 0; i < tmp.length-1; i++) { /** Run while there are still more indexes left to cover*/
		if(tmp[i].compareTo(tmp[i+1]) > 0) { /** If this value is greater than the next value*/
		    swapped = true;
		    String temp = tmp[i]; /** Store this value in a temp String-object*/
		    tmp[i] = tmp[i+1]; /** Put this index' value in the next index*/
		    tmp[i+1] = temp; /** The next index' value is set to what this index'
				      * value was before we switched it with index+1's value*/
		}
	    }	    
	}
	c.add(tmp); /** Add to the container*/
    }
    
    /** The combining is based on https://github.com/olehhe/INF1010-Oblig4/blob/master/Sort.java merger() 
     *  and help from lab-teachers(especially Stian on group 13).
     * (I included this because i don't wanna be suspected for cheating, and you've said I could use parts of 
     * code if I completely understand what it does, which I do. This I have proven with the commenting. 
     *  Also, I can't see that merging has so many different possibilities either)
     */
    public void combine() {
	        
	String[] combinedSorted = new String[sortedArray.length + sortedArray1.length]; 
											
	int cnt1 = 0; /** Counter for the first array(sortedArray)*/
	int cnt2 = 0; /** Counter for the second array(sortedArray1)*/
	
	for(int i = 0; i < combinedSorted.length; i++) { /** Run as long as there are more index
							  *left in the array which stores the solutions*/
            if(cnt1 < sortedArray.length) { /** If sortedArray's counter is less than the length of this array*/
                if(cnt2 < sortedArray1.length) { 
                    if(sortedArray[cnt1].compareTo(sortedArray1[cnt2]) < 0)  /** If sortedArray's value is
									      * less than sortedArray1's value*/
                        combinedSorted[i] = sortedArray[cnt1++]; /** Put sortedArray's 
								  *value in the array which stores the solutions,
								  and increment sortedArray's counter*/
		    else  /** If sortedArrays's value is greater than sortedArray1's value*/
			combinedSorted[i] = sortedArray1[cnt2++]; /** Put sortedArray1's value in the
								      array which stores the solutions
								      and increment sortedArray1's counter*/
		} else { /** If sortedArray1's counter is greater than the length of this array*/
		    combinedSorted[i] = sortedArray[cnt1++]; /** Put sortedArray's value in the 
								 array which stores the solution,
								 and increment sortedArray's counter*/
		}
	    } else {  /** If sortedArray's counter is greater than the length of this array*/
		combinedSorted[i] = sortedArray1[cnt2++];  /** Put sortedArray1's 
							       value in the array which stores the solution,
							       and increment sortedArray1's counter*/
	    }
	}
	c.add(combinedSorted); /** Add the combined array's, which are now stored in a third array,
				   to the container*/
    }
} // End of class Threadsolver

