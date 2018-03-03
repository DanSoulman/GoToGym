import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Array {
	final static Scanner kb = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		final int NO_OF_CLASSES = 5;
		@SuppressWarnings("unused")
		int noOfClasses = loadDataFromFiles(NO_OF_CLASSES);	}

	// just to get you started
	// need to create arrays to match ClassDetails.txt file
	// and add populate the arrays with data from that file.
	// below it just reads the file and prints it out.
	private static int loadDataFromFiles(int CLASSES) throws FileNotFoundException {
		String[] classCode = new String[CLASSES];
		String[] days = new String [CLASSES];
		int[] startTimes = new int [CLASSES];
		String[] instructors = new String[CLASSES];
		File file = new File("ClassDetails.txt");
		Scanner inputFile = new Scanner(file);
		int classCounter = 0;
        while (inputFile.hasNext()) {
            classCode[classCounter] = inputFile.nextLine();
            days[classCounter] = inputFile.nextLine();
            startTimes[classCounter] = inputFile.nextInt();
            inputFile.nextLine();
            instructors[classCounter] = inputFile.nextLine();
            classCounter++;
		}
		System.out.println("classCounter = " + classCounter);
		inputFile.close();
		for(int i = 0; i < CLASSES; i++){
			System.out.println(classCode[i] + " " + days[i] + " at " + startTimes[i] + "pm, with "  + instructors[i]);
		}
		return classCounter;
	}
	
}
