import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class AddingNewClass {
static Scanner kb = new Scanner(System.in);

	public static void main(String[] args) throws IOException	 {

			int userChoice;
			boolean valid = false;
			@SuppressWarnings("unused")
			int textCounter;
			final int MAX_CLASS = 12;
			String[] classCode = new String[MAX_CLASS];
			String[] days = new String[MAX_CLASS];
			String[] instructors = new String[MAX_CLASS];
			int[] startTimes = new int[MAX_CLASS];
			boolean error = false;

			File file1 = new File("ClassDetails.txt");
			Scanner inputFile = new Scanner(file1);

			int classCounter = 0;
			while (inputFile.hasNext()) {
				classCode[classCounter] = inputFile.nextLine();
				days[classCounter] = inputFile.nextLine();
				startTimes[classCounter] = inputFile.nextInt();
				inputFile.nextLine();
				instructors[classCounter] = inputFile.nextLine();
				classCounter++;
			}
			
			inputFile.close();

			System.out.println("classCounter = " + classCounter);
			inputFile.close();

			System.out.println("Enter Class Type: \n1. Pilates\n2. Yoga\n3. Spin");

			do {
				while (!kb.hasNextInt()) // validate data type
				{
					kb.next(); // clear invalid input
					System.out.println("INVALID INPUT");
				}
				userChoice = kb.nextInt();
				kb.nextLine();
				if (userChoice < 1 || userChoice > 3) // validate range
					System.out.println("INVALID_INPUT");
				else
					valid = true;
			} while (!valid);
			switch (userChoice) {

			case 1:
				classCode[classCounter] = "PI";
				break;
			case 2:
				classCode[classCounter] = "YO";
				break;
			case 3:
				classCode[classCounter] = "SP";
				break;
			}

			System.out.println("Enter the day: \n1. Monday\n2. Tuesday\n3. Wedensday");

			do {
				while (!kb.hasNextInt()) 
				{
					kb.next(); 
					System.out.println("INVALID INPUT");
				}
				userChoice = kb.nextInt();
				kb.nextLine();
				if (userChoice < 1 || userChoice > 3) 
					System.out.println("INVALID_INPUT");
				else
					valid = true;
			} while (!valid);
			
			switch (userChoice) {

			case 1:
				days[classCounter] = "Mon";
				break;
			case 2:
				days[classCounter] = "Tues";
				break;
			case 3:
				days[classCounter] = "Wed";
				break;
			}
			
			System.out.println("Choose the time: \n1. 7 O' Clock \n2. 8 O' Clock\n3. 9 O' Clock");

			do {
				while (!kb.hasNextInt()) 
				{
					kb.next(); 
					System.out.println("INVALID INPUT");
				}
				userChoice = kb.nextInt();
				kb.nextLine();
				if (userChoice < 1 || userChoice > 3) 
					System.out.println("INVALID_INPUT");
				else
					valid = true;
			} while (!valid);
			
			switch (userChoice) {

			case 1:
				startTimes[classCounter] = 7;
				break;
			case 2:
				startTimes[classCounter] = 8;
				break;
			case 3:
				startTimes[classCounter] = 9;
				break;
			}
			System.out.println("Choose a teacher: \n1. Amanda\n2. Gerry\n3. Aidan\n4. Frank");

			do {
				while (!kb.hasNextInt()) 
				{
					kb.next(); 
					System.out.println("INVALID INPUT");
				}
				userChoice = kb.nextInt();
				kb.nextLine();
				if (userChoice < 1 || userChoice > 4) 
					System.out.println("INVALID_INPUT");
				else
					valid = true;
			} while (!valid);
			
			switch (userChoice) {

			case 1:
				instructors[classCounter] = "Amanda";
				break;
			case 2:
				instructors[classCounter] = "Gerry";
				break;
			case 3:
				instructors[classCounter] = "Aidan";
				break;
			case 4:
				instructors[classCounter] = "Frank";
				break;
			}
			
			System.out.println("Added " + classCode[classCounter] + " on " + days[classCounter] + " at " + startTimes[classCounter] + " O' Clock with " + instructors[classCounter]);
			classCounter++;
			
			if(error == true){
				System.out.println("This instructor already has a class at this time");
			}
			
			FileWriter fw =  new FileWriter("ClassDetails.txt");

	        PrintWriter outputFile = new PrintWriter(fw);
	        
	        for(int i = 0; i < classCounter; i++){
	        	outputFile.println(classCode[i]);
	        	outputFile.println(days[i]);
	        	outputFile.println(startTimes[i]);
	        	outputFile.println(instructors[i]);
	        }
	        outputFile.close();
	        fw.close();
		}
		
		

	}