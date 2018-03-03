import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CallInTxt {
	final static Scanner kb = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		@SuppressWarnings("unused")
		int noOfClasses = loadDataFromFiles();
		String[] classCode = {"", "", "", "", "", "", "", "", "", "", "", "", ""};
		System.out.println(classCode[0]);
	}

	// just to get you started
	// need to create arrays to match ClassDetails.txt file
	// and add populate the arrays with data from that file.
	// below it just reads the file and prints it out.
	private static int loadDataFromFiles() throws FileNotFoundException {
		File file = new File("ClassDetails.txt");
		Scanner inputFile = new Scanner(file);

		int classCounter = 0;
		while (inputFile.hasNext()) {
			String classCode = inputFile.nextLine();
			String days = inputFile.nextLine();
			int startTimes = inputFile.nextInt();
			inputFile.nextLine();
			String instructors = inputFile.nextLine();
			System.out.println("classCode = " + classCode);
			System.out.println("days = " + days);
			System.out.println("startTimes = " + startTimes);
			System.out.println("instructors = " + instructors);
			System.out.println("");

			classCounter++;
		}
		System.out.println("classCounter = " + classCounter);
		inputFile.close();
		return classCounter;
	}
}
