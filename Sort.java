/**@author Henrik Hansen(henrihan) <henrik.hansen@student.jus.uio.no>
 * This class just starts the program, and this is we're the main-thread is started aswell.
 * @param threadCnt Number of threads in total
 * @param input Input-file
 * @param output Output-file
 * @param timerStart a timer which tells us in real-time when the program started. 
 * Used for figuring out how long the program takes to run
 */

class Sort {
    
    private static int threadCnt = 0;
    protected static String input, output = "";
    private static Oblig6 init;
    protected static long timerStart;
    
    public static void main(String[]args) {
	timerStart = System.nanoTime();

	if(args.length != 3) { 
	    errorMsg();
	}

	welcomeMsg();
	threadCnt = Integer.parseInt(args[0]);
	input = args[1];
	output = args[2];
	init = new Oblig6(threadCnt, input, output);
	init.readFile();
	init.startThreads();
	
    }
    
    private static void errorMsg() {
	System.out.println("\nThis program requires you to use three arguments:");
	System.out.println("1. How many threads there should be to do the sorting");
	System.out.println("2. The name of the infile");
	System.out.println("3. The name of the outfile");
	System.out.println("\nProgram will now exit. Have a nice day");
	System.exit(1);
    }
    
    private static void welcomeMsg() {
	System.out.println("\n---------------------------------------------------------------------------\n");
	System.out.println("Welcome to Henrik's word-sorter. The program uses 'Bubble Sort' as sorting algorithm.");	System.out.println("It has been tested with names.txt, sowpods.txt");
	System.out.println("and /usr/share/dict/words file(on linux-systems)");
	System.out.println("Be aware that it might take some time if you choose to sort with few threads combined with many words");
	System.out.println("Have a nice day");
	System.out.println("\n---------------------------------------------------------------------------\n");
    }
}
