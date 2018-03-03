/** Go to Gym Program

 * @author Dan Coleman R00151926
 * Semester 2 Project Year One
 * Comp1C-X
 * Last updated 28/04/17 13:19
 */
import java.io.*;

import java.util.Scanner;
public class FinalCode {
	final static Scanner kb = new Scanner(System.in);
	public static void main(String[] args) throws IOException {
	
		
		final int MAX_CLASS_NO = 12; //Maximum number of classes allowed		
		final int MAX_CLASS_TYPE = 3; //Maximum class types allowed for now
		final int MAX_INSTRUCTOR_ALLOWED = 4; //Maximum no of instructors allowed for now

		// Arrays-------------------------------------------------------
		String[] classCode = new String[MAX_CLASS_NO]; 					//Codes for each class (E.g YO for yoga)
		String[] days = new String [MAX_CLASS_NO];	   					//days of the week
		int[] startTimes = new int [MAX_CLASS_NO];						//Starting times of 7, 8 and 9
		String[] instructors = new String[MAX_CLASS_NO];				//instructors working at Go To Gym
		String[] instructorNames = new String[MAX_INSTRUCTOR_ALLOWED];	//Name of instructors specifically taken from Instructors.txt
		String[] className = new String[MAX_CLASS_TYPE];				//Name of classes specifically taken from .txt file
		String[] classNameCode = new String[MAX_CLASS_TYPE];			//Codes for classes, made in loadCodes.
		//--------------------------------------------------------------
		
		//Times Constants------------------------------
		final int SEVEN_CLOCK = 7;               //7 O'Clock
		final int EIGHT_CLOCK = 8;              //8 O'Clock
		final int NINE_CLOCK = 9;              // 9 O'Clock
		//--------------------------------------------
		
		//Pay Constants--------------------------------------------------------------------
		final int EARLY_INSTRUCTOR_FEE = 60; //Fee for an instructor on the 7 or 8pm shifts 
		final int LATE_INSTRUCTOR_FEE = 80; // Fee for an instructor on the 9pm shift
		final int BONUS = 10; //Bonus for each extra class over the threshold they have
		final int CLASSES_FOR_BONUS = 2; //Instructors get a bonus of 10 euro for every subsequent class thought
		//--------------------------------------------------------------------------------
		
		//Misc. Constants------------------------------------------------------------------
		final String TAB = "\t"; //Tab
		//final String DOUBLE_TAB = "\t\t"; Wasn't ever used
		//-------------------------------------------------------------------------------
		
		
		printTitle("Main Page");//Prints the title for the main page. 
		int classCounter = loadFiles(classCode, days, startTimes, instructors, MAX_CLASS_NO); //This will pass the contents of the text files into the various arrays passed in
		
		loadCodes(className, classNameCode); //loads arrays with the full name of each class and it's code from a txt file 
		loadNames(instructorNames); //loads array with instructor names
		

//Menu-----------------------------------------------------------------------------------------------------------------
		boolean exit = false; //Allows user to exit menu and terminate program

		while (exit == false) {
			System.out.println("1. Add a Class Session"); 
			System.out.println("2. Show Times all Classes"); 
			System.out.println("3. Show Instructor Payments Due"); 
			System.out.println("4. Print TimeTable for Instructor"); 	
			System.out.println("5. Show Ordered TimeTable with Codes"); 
			System.out.println("6. Exit"); 

			int choice = kb.nextInt();
			kb.nextLine();
			switch (choice) {

			case 1:
				printTitle("Add new class");
				addingNewClass(classCode, days, startTimes, instructors, MAX_CLASS_NO, classCounter,SEVEN_CLOCK,EIGHT_CLOCK,NINE_CLOCK, className, classNameCode, instructorNames);
				//This method allows the user to add a new class to the list.
				break;
			case 2:
				printTitle("Class Times");
				showClassTimes(classCode, days, startTimes, instructors, MAX_CLASS_NO);
				//This method shows the time for each class, along with it's Class type and instructor.
				break;
			case 3:
				printTitle("Payments Due");
				instructorPayments(instructors, startTimes, MAX_CLASS_NO, classCounter, EARLY_INSTRUCTOR_FEE, LATE_INSTRUCTOR_FEE, BONUS, NINE_CLOCK, CLASSES_FOR_BONUS, TAB); 
				//This method will allow the used to see the pay each instructor is due.
				break;
			case 4:
				printTitle("Print Timetable to File");
				timetable(days, instructors, startTimes, classCode, className, TAB, classCounter, classNameCode, instructorNames);
				//This method prints each instructors timetables into a file, it also prints it on the program.
				break;
			case 5:
				printTitle("Ordered Timetable");
				dayByDayTimetable(classCode, days, startTimes, MAX_CLASS_NO, classCounter, SEVEN_CLOCK, EIGHT_CLOCK, NINE_CLOCK, TAB, classNameCode);
				//This method prints out the timetable day by day.
					break;
			case 6:
				System.out.println("Exiting...");
				//Exits the program.
				exit = true;
				break;
			}
			System.out.println("\nPress Return to continue");
			kb.nextLine();
		}
		
	}

	
//End Of Menu-------------------------------------------------------------------------------------------------------------------


//PRINT TITLE---------------------------------------------------------------------------------------------------------------------
	/**
	 *Prints the title card, featuring users name. 
	 * @param name Name of the menu.
	 */
	private static void printTitle(String name) {
		System.out.println("The Goto Gym " + name );
		System.out.println("-------------------------\n");
	}
//END OF PRINT TITLE---------------------------------------------------------------------------------------------------------------	
	
	
	
	
//LOAD FILES	
	/**
	 *  Loads .txt files as arrays
	 @param codes
	 * 			These are the class codes, used to show if a class is PI (pilates), YO (yoga) or SP (spinning).
	 * @param day
	 * 			What day the classes are on.
	 * @param times
	 * 			What time the classes are on.
	 * @param instructors
	 * 			What instructor is teaching the class.
	 * @param CLASS_NO
	 * 			Max number of classes allowed.
	 *  @return classCounter
	 *  		counts number of classes in program. 
	 * @throws IOException
	 */
	private static int loadFiles(String[] codes, String[]day, int[]times, String[]instructor, int CLASS_NO) throws IOException {	
		File file1 = new File("ClassDetails.txt");  // This imports the text file ClassDetails.
		Scanner inputFile1 = new Scanner(file1); // Allows the Scanner to read ClassDetails.

		int classCounter = 0; //counter will be returned to the main letting us know the number of classes running in the week
		while (inputFile1.hasNext()) { //loops until ClassDetail.txt has no more info
			codes[classCounter] = inputFile1.nextLine(); //Adds first line in ClassDetail to classCodes array.
			day[classCounter] = inputFile1.nextLine();   //Adds the second line in ClassDetail to days array.
			times[classCounter] = inputFile1.nextInt();	//Adds the third line in ClassDetail to time array.
			inputFile1.nextLine(); //Clears buffer
			instructor[classCounter] = inputFile1.nextLine(); //Adds the fourth line in ClassDetail to instructors array.
			classCounter++; //Counts the block of four as one class. Then repeats the process, counting the 5th line as the first for the new class.
		}
		
		inputFile1.close();
		return classCounter;
		//System.out.println("classCounter = " + classCounter);   Was used for debugging to let me see that classCounter worked correctly.

	}
//END OF LOAD FILES --------------------------------------------------------------------------------------------------------------------	

//LOAD CODES-----------------------------------------------------------------------------------------------------------------------------
	/**
	 * loads the full class names from a txt file, puts that in an array,
	 *  then takes the first two chars from the class name and creates a class code
	 *   
	 * @param classesNames
	 * 		Each classes name, taken from txt file
	 * @param classesNamesCodes
	 * 		Code for each class, made from the first two chars
	 * @throws FileNotFoundException
	 */
	private static void loadCodes(String[] classesNames, String[] classesNamesCodes) throws FileNotFoundException {
		File file1 = new File("ClassTypes.txt");  // This imports the text file ClassDetails.
		Scanner inputFile = new Scanner(file1); // Allows the Scanner to read ClassDetails.
		
		String upperName = ""; //used later to turn name to ALL CAPS to make the code.
		char codeletter1 = ' '; // gets the first letter of the class code.
		char codeletter2 = ' '; // gets the second letter of the class code.
		int counter = 0; // finds how many different classes there are, and is then used in for loop
		
//loops until no more info is left, creating an array of class names, and sets counter to number of classes---------------------------
		while (inputFile.hasNext()) { 
			classesNames[counter] = inputFile.nextLine();
			
			counter++;
		}
//--------------------------------------------------------------------------------------------------------------------------------------		

//loops for the number of classes running, it turns the class name to all caps, then sets the first 2 letters to separate chars
//then sets the class code to the two characters added
		for(int i = 0; i < classesNames.length; i++){
			upperName = classesNames[i].toUpperCase();
			
			codeletter1 = upperName.charAt(0);
			codeletter2 = upperName.charAt(1);
			
			classesNamesCodes[i] = Character.toString(codeletter1) + Character.toString(codeletter2);
			//This wasn't covered in lecturers so I took Character.toString from the second answer at 
			//http://stackoverflow.com/questions/14444717/append-a-single-character-to-a-string-or-char-array-in-java and it worked.
			//I assume that was allowed. If not my bad.
			
		}

	inputFile.close(); //closes text file.
		
	}
//END OF LOAD CODES----------------------------------------------------------------------------------------------------------------------
	
//LOAD NAMES----------------------------------------------------------------------------------------------------------------------------
	/**
	 * Loads in the name of the instructors from instructor.txt as an array.
	 * @param instructorsNames
	 * 		Name of the instructors, does what it says on the tin.
	 * @throws FileNotFoundException
	 */
		private static void loadNames(String[] instructorsNames) throws FileNotFoundException {
			File file = new File("Instructors.txt");  // This imports the text file Instructors.
			Scanner inputFile = new Scanner(file); // Allows the Scanner to read the file.
			
			
			int counter = 0;// finds how many different instructors there are, and is then used in for loop
			
			//loops until no more info is left, creating an array of instructor names., and increase counter. 
			while (inputFile.hasNext()) { 
				instructorsNames[counter] = inputFile.nextLine();
				
				counter++;// Counter +1
				
			}
			//Closes file.
			inputFile.close();
			
		}
//END OF LOAD NAMES----------------------------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
//OPTION 1 ADD NEW CLASS---------------------------------------------------------------------------------------------------------------------	
/** Allows the user to add classes to the list of available classes, along with what day it is on, what time it is on,
 * and with which instructor 
 * @param codes
 * 			These are the class codes, used to show if a class is PI (pilates), YO (yoga) or SP (spinning).
 * @param day
 * 			What day the classes are on.
 * @param times
 * 			What time the classes are on.
 * @param instructor
 * 			What instructor is teaching the class.
 * @param CLASS_NO
 * 			Max number of classes allowed.
 * @param instructorsNames 
 * 		An array containing all instructor names from instructors .txt
 * @param classesNamesCodes 
 * 		An array containing all class code names. 
 * @param classesNames 
 * 		An array containing the name of all the classes available at the gym. 
 * @throws IOException
 */
	private static void addingNewClass(String[] codes, String[]day, int[]times, String[]instructor,
			int CLASS_NO, int classCount, int SEVEN, int EIGHT, int NINE, String[] classesNames, String[] classesNamesCodes, String[] instructorsNames) throws IOException {
		boolean error = false; //UNUSED, TO BE COMPLETE
		boolean valid = false; //USED TO CHECK THAT INPUT IN VALID DATA TYPE IN CORRECT RANGE
		
//CLASS CODE---------------------------------------------------------------------------------------------------------------------
		System.out.println("Enter Class Type: \n1. " + classesNames[0] + "\n2. " + classesNames[1] + "\n3. " + classesNames[2]);

		int userChoice; //User inputed data
		do {
			while (!kb.hasNextInt()) // validate data type is correct
			{
				kb.next(); // clear invalid input
				System.out.println("INVALID DATA TYPE"); //informs user
			}
			userChoice = kb.nextInt();
			kb.nextLine(); //clears buffer
			if (userChoice < 1 || userChoice > 3) // validate data is within range
				System.out.println("Please input a number within the range (1-3)"); // informs user input wasn't in range
			else
				valid = true; // allows valid data out of while loop
		} while (!valid || userChoice < 1 || userChoice > 3 ); // Stops inputting numbers out of range from moving on 
		
//if statement picks which class code to use
		if (userChoice == 1){
			codes[classCount] = classesNamesCodes[0]; // adds PI as class code
		}
		else if(userChoice == 2){
			codes[classCount] = classesNamesCodes[1]; // adds YO as class code
		}
		else if(userChoice == 3){
			codes[classCount] = classesNamesCodes[2]; // adds SP as class code
		}
		
//-------------------------------------------------------------------------------------------------------------------------------

//DAY OF CLASS ---------------------------------------------------------------------------------------------------------------------		
		System.out.println("Enter the day of the class: \n 1. Monday \n 2. Tuesday \n 3. Wedensday"); //asks user for next info
			do {
				while (!kb.hasNextInt()) // validate data type is correct
				{
					kb.next(); // clear invalid input
					System.out.println("INVALID DATA TYPE"); //informs user
				}
				userChoice = kb.nextInt();
				kb.nextLine(); //clears buffer
				if (userChoice < 1 || userChoice > 3) // validate data is within range
					System.out.println("Please input a number within the range (1-3)"); // informs user input wasn't in range
				else
					valid = true; // allows valid data out of while loop
			} while (!valid || userChoice < 1 || userChoice > 3);
			

			
//Each choice is a different day, user inputs the one the new class is
		if(userChoice == 1){
			day[classCount] = "Mon"; //Class is on Monday
			}
		else if(userChoice == 2){
			day[classCount] = "Tues"; //Class is on Tuesday
		}
		else if(userChoice == 3){
			day[classCount] = "Wed"; //Class is on Wednesday
		}
//--------------------------------------------------------------------------------------------------------------------------------
		
//CLASS TIME---------------------------------------------------------------------------------------------------------------------			
	System.out.println("Choose the time of the class: \n 1. 7 O' Clock \n 2. 8 O' Clock \n 3. 9 O' Clock");
		do {
			while (!kb.hasNextInt()) // validate data type is correct
			{
				kb.next(); // clear invalid input
				System.out.println("INVALID DATA TYPE"); //informs user
			}
			userChoice = kb.nextInt();
			kb.nextLine(); //clears buffer
			if (userChoice < 1 || userChoice > 3) // validate data is within range
				System.out.println("Please input a number within the range (1-3)"); // informs user input wasn't in range
			else
				valid = true; // allows valid data out of while loop
		} while (!valid || userChoice < 1 || userChoice > 3);


// Each case is a different class time, user inputs the one the new class is
		if(userChoice == 1){
			times[classCount] = SEVEN; //Class is at 7 o'clock
		}
		else if(userChoice == 2){
			times[classCount] = EIGHT; //Class is at 8 o'clock
		}
		else if(userChoice == 3){
			times[classCount] = NINE; //Class is at 9 o'clock
		}
		else if (userChoice == 4){
			System.out.println("Whoops, this shouldn't be possible, contact the programmmer!");
		}
//---------------------------------------------------------------------------------------------------------------------
		
//INSTRUCTOR---------------------------------------------------------------------------------------------------------------------	
	System.out.println("Choose a teacher: \n1. " + instructorsNames[0] + "\n2. " + instructorsNames[1] + "\n3. " + instructorsNames[2] + "\n4. " + instructorsNames[3]);

		do {
			while (!kb.hasNextInt()) // validate data type is correct
			{
				kb.next(); // clear invalid input
				System.out.println("INVALID DATA TYPE"); //informs user
			}
			userChoice = kb.nextInt();
			kb.nextLine(); //clears buffer
			if (userChoice < 1 || userChoice > 4) // validate data is within range
				System.out.println("Please input a number within the range (1-4)"); // informs user input wasn't in range
			else
				valid = true; // allows valid data out of while loop
		} while (!valid || userChoice < 1 || userChoice > 4);
		
		
		
		
		if (userChoice == 1){
			instructor[classCount] = instructorsNames[0]; //Amanda is class instructor
		}
		else if (userChoice == 2){
			instructor[classCount] = instructorsNames[1]; //Gerry is class instructor
		}
		else if (userChoice == 3){
			instructor[classCount] = instructorsNames[2]; //Aidan is class instructor
		}
		else if (userChoice == 4){
			instructor[classCount] = instructorsNames[3]; //Frank is class instructor
		}
		else if (userChoice == 5){
			System.out.println("Whoops, this shouldn't be possible, contact the programmmer!");
		}
//---------------------------------------------------------------------------------------------------------------------		
		System.out.println("Added " + codes[classCount] + " on " + day[classCount] + " at " + times[classCount] + "pm, with " + instructor[classCount]);
		//Alerts user of exactly what class they added
		classCount++; //Increases classCounter by 1
		
		if(error == true){ //Validation TO BE COMPLETED
			System.out.println("This instructor already has a class at this time");
		}
		
//Allows us to edit ClassDetail.txt with our new info----
		FileWriter fw =  new FileWriter("ClassDetails.txt"); 
		PrintWriter outputFile = new PrintWriter(fw);
//-------------------------------------------------------------------------------
 //Prints output into .txt file----------------------------------------------
        for(int i = 0; i < classCount; i++){
        	outputFile.println(codes[i]);
        	outputFile.println(day[i]);
        	outputFile.println(times[i]);
        	outputFile.println(instructor[i]);
        }
 //-----------------------------------------------------------------------------
        
//Closes all files still open---
        outputFile.close();
        fw.close();
	}
//--------------------------------	

//END OF OPTION 1---------------------------------------------------------------------------------------------------------------------
	

	
	
	
	
	
	
//OPTION 2-----------------------------------------------------------------------------------------------------------------------
	/**
	 *This code reads the ClassDetail.txt file and prints out the classes available. 
	 * @param codes
	 * 		Prints out the Code for the type of class it is. 
	 * @param day
	 * 		The day a class is on.
	 * @param times
	 * 		The time a class is on.
	 * @param instructor
	 * 		The name of instructor running the class.
	 * @param CLASS_NO
	 * 		Used to loop the correct amount of times.
	 * @return classCounter 
	 * 		number of classes running that week.
	 * @throws IOException 
	 */
	private static int showClassTimes(String[] codes, String[]day, int[]times, String[]instructor, int CLASS_NO) throws IOException {
		
		int classCounter = loadFiles(codes, day, times, instructor, CLASS_NO); //This will pass the contents of the text files into the various arrays passed in
		//System.out.println("classCounter = " + classCounter); Used for debug, checking that class counter worked
		for(int i = 0; i < classCounter; i++){ //Loops for the number of non-null classes
			System.out.println(codes[i] + " " + day[i] + " at " + times[i] + "pm, with "  + instructor[i]); //Prints out all info needed
		}
		return classCounter; //Prints out number of classes in use. 
	}
//END OF OPTION 2------------------------------------------------------------------------------------------------------------------------------

	
	
	
	
	
//OPTION 3-------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method gets the number of classes each instructor teaches, finds if it's early or late class pay for each class,
	 * if they get a bonus for teaching 2 or more classes and then adds it together, it also finds the total wages the gym must
	 * pay to workers.
	 * 
	 * @param instructor
	 * 		Name of each instructor working there.
	 * @param times
	 * 		Time of each class running
	 * @param CLASS_NO
	 * 		Maximun number of classes allowed
	 * @param classCounter3
	 * 		Number of classes actually in use
	 * @param EARLY_FEE
	 * 		Money given to earlier classes (€60)
	 * @param LATE_FEE
	 * 		Money given to later classes (€80)
	 * @param BONUS
	 * 		Money given as a bonus to instructors with more than 2 classes
	 * @param NINE
	 * 		The number 9, I've been told that programmers tend to make numbers used often as variables for whatever reason
	 * 		so I thought it would be good practice to try it with the numbers used for class times. 
	 * @param BONUS_CLASS_NO
	 * 		Once the number of classes thought is greater than this bonuses occur.
	 * @param TAB
	 * 		\t, you told us this was good practice
	 * @throws FileNotFoundException
	 * 		Stops program from killing itself if it can't find the file. 
	 */
	private static void instructorPayments(String[] instructor, int[] times, int CLASS_NO, int classCounter3,
			int EARLY_FEE, int LATE_FEE, int BONUS, int NINE,
			int BONUS_CLASS_NO, String TAB ) throws FileNotFoundException {
		
			double[] instructorsPay = new double[CLASS_NO]; //Array with each instructors pay, set to the size of max classes allowed
			double totalWages = 0;	//Counts total wages of all the instructors added
			String[] instructorNames = new String[CLASS_NO]; //An array the size of the max num of classes allowed that stores the names of the four instructors
			int instructorLoop = 0; //Counter for while loop
			
			
		
		File file = new File("Instructors.txt"); //Reading from the text file Instructors.txt which contains all instructor names
		Scanner inputFiles = new Scanner(file); //Allows us to edit the text file
		
		
		while (inputFiles.hasNext()) { //While the file has another line this will go
			instructorNames[instructorLoop] = inputFiles.nextLine(); //adds each of the instructors names to an array
			
			instructorLoop++; //Each time the loop loops this will increase by 1 telling us the number of instructors
			//System.out.println("Loop incriment + 1 Loop is at: " + instructorLoop); used for debug to check instructorLoop worked. 
		}

		inputFiles.close(); //The file is closed		
		System.out.println("Name"+TAB+"Classes"+TAB+"Pay (Euros)\n-----"+TAB+"-------"+TAB+"-----------"); //Prints out titles.


//ADDING TOTAL WAGES--------------------------------------------------------------------------------------------------------------
		int classesTaught =0 ; //Counts how many classes per instructor

		for (int x = 0; x < instructorLoop; x++) { //This loops for the number of instructors.
				classesTaught = 0; //On each loop this is reset to zero to count number of classes taught by each instructor
				
			for (int i = 0; i < classCounter3; i++) { //This loops for the number of classes by a given instructor

				if (instructorNames[x].equalsIgnoreCase(instructor[i]) == true) {
//If the instructor name at position x is equal to instructor at position i then classesTaught increases by one as it's a class by that instructor.
					classesTaught++;
					
					
//IF ELSE--------------------------------------------------------------------------------------------------------------
					if (times[i] == NINE) { //If class is at 9, the late fee of 80 is used as the pay
						totalWages = totalWages + LATE_FEE;				  //Adds it to total Wages
						instructorsPay[x] = instructorsPay[x] + LATE_FEE; //Adds it to instructors pay
							} 
					
					//Else if the time is equal to 7 or 8 then job falls in here and the wages increase by 60
					else {
						totalWages = totalWages + EARLY_FEE;
						instructorsPay[x] = instructorsPay[x] + EARLY_FEE;
					}
				}
//END of if else--------------------------------------------------------------------------------------------------------------
				

				if (classesTaught > BONUS_CLASS_NO) { //If number of classes being taught is greater than 2
					totalWages = totalWages + BONUS; //The total wages increases by 10 for each class
					instructorsPay[x] = instructorsPay[x] + BONUS; //The instructors pay increases by 10 for each class
				}


			}// End of second loop
			System.out.println(instructorNames[x] + TAB + classesTaught + TAB+ instructorsPay[x]); //Prints Instructors Name, Classes, and Pay
}//End of first for loop
			//Once the big loop is complete the total wages are printed
			System.out.println( "Total Wages for this week is " + totalWages + " Euros");
	}
//END OF OPTION 3 -----------------------------------------------------------------------------------------------------------------
	
	
	
//OPTION 4-----------------------------------------------------------------------------------------------------------------------	
/**
 * 	
 * @param day
 * 		The day a class is on.
 * @param instructor
 * 		Instructor running the class
 * @param classTimes
 * 		Time the class is on
 * @param codes
 * 		Class Code e.g YO is Yoga
 * @param CLASS_NAME
 * 		Full name for each class type
 * @param TAB4
 * 		/t
 * @param counterForClasses 
 * 		 tracks the number of classes that are running 
 * @throws IOException
 */
	private static void timetable(String[] day, String[] instructor, int[] classTimes,
				 String[] codes, String[] CLASS_NAME, String TAB4, int counterForClasses, String[] classesNamesCodes, String[] instructorsNames) throws IOException {
		 	//String test= "Amanda";
		
		 	@SuppressWarnings("unused")
			int numberOfClasses = 0; //Number of classes being taught by an instructor.
			String input = ""; //Stores the users input.
			String classTable = ""; //Stores the timetable.

			int classesRunning = 0; //Used to track the number of classes the instructor is running.
			@SuppressWarnings("unused")
			int userLoop = 1; //Used for a while loop
			
			
			boolean valid = false; //Used to stop user inputting incorrect data
		
			
			//for(int i = 0; i < 11; i++){		Was used for debugging
				//System.out.println(codes[i]);
				//System.out.println(day[i]);
				//System.out.println(classTimes[i]);
				//System.out.println(instructor[i]);
			//}
		
			//--------------------------------------------------------------------------------------------------------------
			while(valid == false) {
				
				do { // Asks the user to input a value and will continue to loop if the user inputs nothing
				System.out.println("Please input name of Instructor"); // Will ask the user for names
				input = kb.nextLine(); // Has the user input the text
				System.out.println();// Blank line to recreate look of sample output
				if (input.isEmpty() == true) //If input is empty asks user to input name.
					System.out.println("Empty Value, input name.");
			} while (input.isEmpty() == true); // Loops while input is empty
		
				//System.out.println(input); USED FOR DEBUGGING
			//If the name matches any of the instructors then valid is set to true and it breaks out
				if(input.equalsIgnoreCase(instructorsNames[0]) == true || input.equalsIgnoreCase(instructorsNames[1]) == true || 
					input.equalsIgnoreCase(instructorsNames[2]) == true || input.equalsIgnoreCase(instructorsNames[3]) == true){
					//System.out.println("test");  USED FOR DEBUGGING
					valid = true;
				}
				userLoop++; //User loop increases by one on each iteration
			
			//--------------------------------------------------------------------------------------------------------------

			//This loops for the no of classes running, it looks at instructor names
			//running a class and makes a timetable of classes for the instructor the user inputed.
			//System.out.println("test");
			for (int i = 0; i < counterForClasses; i++) { //Iterates for the number of classes

				//If the users input is an instructor name already known
				
				if (input.equalsIgnoreCase(instructor[i]) == true) {


																					
						if(codes[i].equalsIgnoreCase(classesNamesCodes[0]) == true){
						//prints "Pilates	 (day class is running) at (time it runs)"

						classTable = classTable + CLASS_NAME[0] + TAB4 + day[i]
								+ " at " + classTimes[i] + "\n";
						classesRunning++; //ups counter on running classes by one
						}

						else if(codes[i].equalsIgnoreCase(classesNamesCodes[1]) == true){
						//prints "Yoga	 (day class is running) at (time it runs)"
						
						classTable = classTable + CLASS_NAME[1] + TAB4 + day[i]
								+ " at " + classTimes[i] + "\n";
						classesRunning++; //ups counter on running classes by one
						}

						else if(codes[i].equalsIgnoreCase(classesNamesCodes[2]) == true){
							//prints "Spin	 (day class is running) at (time it runs)"
						classTable = classTable + CLASS_NAME[2] + TAB4 + day[i]
								+ " at "  + classTimes[i] + "\n";
						classesRunning++; //ups counter on running classes by one
						}
					

				
			}
			}
			
			
			//If the instructor has no classes this run.
			if (classesRunning == 0) {
				System.out.println(input + ".txt created"); //Prints out the name of file created
				System.out.println("Writing to file...");//Prints out that that it's writing the file as was done in the sample

				//Lets us write text to a file called (inputted name)s timetable
				FileWriter fw = new FileWriter(input + "sTimetable.txt");

				PrintWriter outputFile = new PrintWriter(fw);

				System.out.println("No Classes this week"); //It will print out saying that the instructor has no classes running this week
				outputFile.println(classTable);

				//Closing the files
				outputFile.close();
				fw.close();
				
			}
			//--------------------------------------------------------------------------------------------------------------
			
			//Other wise it will successfully write the timetable here
			else {
				System.out.println(input + ".txt created"); //Prints out the name and text informing the user timetable is made
				System.out.println("Writing to file...");//Prints out that that it's writing the file

				//Lets us write text to a file called (inputted name)s timetable
				FileWriter fw = new FileWriter(input + "sTimetable.txt");

				PrintWriter outputFile = new PrintWriter(fw);

				//Writes info to both file and console.
				System.out.println(classTable);
				outputFile.println(classTable);

				//closes files
				outputFile.close();
				fw.close();
			}
		}
	}

//END OF OPTION 4-------------------------------------------------------------------------------------------------------------------	





//OPTION 5--------------------------------------------------------------------------------------------------------------------------
/**
 * 
 * @param codes
 * 		Class codes
 * @param days
 * 		day of class
 * @param times
 * 		time of class
 * @param CLASS_NO
 * 		Max no of classes the program allows
 * @param classCounter
 * 		Counts number of classes being taught
 * @param SEVEN
 * 		number 7
 * @param EIGHT
 *		number 8
 * @param NINE
 *		number 9
 * @param TAB
 * 		\t
 * @throws FileNotFoundException
 * stops program killing itself if file not found
 */
private static void dayByDayTimetable(String[] codes, String[] days, int[] times, int CLASS_NO,
int classCounter, int SEVEN, int EIGHT, int NINE, String TAB, String[] classesNamesCodes) throws FileNotFoundException{
	
String Mon = "Mon"; //It's just monday
String Tues = "Tues"; //It's just tuesday
String Wed = "Wed"; //It's just wednesday
	
	
	String[] daysClasses = {"", "", "","","","","" }; //Used to store tables. 
	
	//These loops check for class days, times and codes to write out a timetable ignoring the additional "nulls"
	//FOR LOOP--------------------------------------------------------------------------------------------------------------
	for (int i = 0; i < classCounter; i++) { //Loops for the number of classes there are


		//Gets Monday classes	
		if (Mon.equalsIgnoreCase(days[i]) == true) {
			//classes at 7.
			if (times[i] == SEVEN) {
				
				//stops null appearing in file
				if (codes[i].equalsIgnoreCase(classesNamesCodes[0]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[1]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[2]) == true) { 
					daysClasses[0] = daysClasses[0] + codes[i] + " "; //Adds to the table

				}
			}

			//classes at 8.
			else if (times[i] == EIGHT) {
				
				//stops null apearing in file
				if (codes[i].equalsIgnoreCase(classesNamesCodes[0]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[1]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[2]) == true) {
					daysClasses[1] = daysClasses[1] + codes[i] + " "; //Adds to the table
				}
			}
			
			//classes at 9.
			else if (times[i] == NINE) {
				
				//Stops null appearing in file
				if (codes[i].equalsIgnoreCase(classesNamesCodes[0]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[1]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[2]) == true) {
					daysClasses[2] = daysClasses[2] + codes[i] + " "; //Adds to the table
				}
			}
		}

	}
	
	//Monday table
	//--------------------------------------------------------------------------------------------------------------
	System.out.println("Monday" + TAB); //Prints out the day
	System.out.println(TAB + SEVEN + TAB + daysClasses[0]); //Prints 7 o'clock classes
	System.out.println(TAB + EIGHT + TAB + daysClasses[1]);//Prints 8 o'clock classes
	System.out.println(TAB + NINE + TAB + daysClasses[2]);//Prints 9 o'clock classes
	//--------------------------------------------------------------------------------------------------------------
	
	//Resets array
	for (int i = 0; i < 3; i++) {

		daysClasses[i] = "";
	}
	

	//FOR LOOP--------------------------------------------------------------------------------------------------------------
	for (int i = 0; i < classCounter; i++) {
		
		//gets Tuesday classes
		if (Tues.equalsIgnoreCase(days[i]) == true) {
			
			//Classes at 7
			if (times[i] == SEVEN) { 
				
				//Stops null appearing in file
				if (codes[i].equalsIgnoreCase(classesNamesCodes[0]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[1]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[2]) == true) {
					daysClasses[0] = daysClasses[0] + codes[i] + " "; //Adds to the table
				}
			}
			
			//Classes at 8
			else if (times[i] == EIGHT) {
				
				//Stops null appearing in file
				if (codes[i].equalsIgnoreCase(classesNamesCodes[0]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[1]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[2]) == true) {
					daysClasses[1] = daysClasses[1] + codes[i] + " "; //Adds to the table
				}
			}
			
			//Classes at 9
			else if (times[i] == NINE) {
				
				//Stops null appearing in file
				if (codes[i].equalsIgnoreCase(classesNamesCodes[0]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[1]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[2]) == true) {
					daysClasses[2] = daysClasses[2] + codes[i] + " "; //Adds to the table
				}
			}
		}

	}
	
	//Tuesday table
	//--------------------------------------------------------------------------------------------------------------
	System.out.println("Tuesday"); //Prints out the day
	System.out.println(TAB+ SEVEN+ TAB + daysClasses[0]); //Prints 7 o'clock classes
	System.out.println(TAB+ EIGHT+ TAB + daysClasses[1]);//Prints 8 o'clock classes 
	System.out.println(TAB+ NINE+ TAB + daysClasses[2]);//Prints 9 o'clock classes
	//--------------------------------------------------------------------------------------------------------------
	
	//Resets array
	for (int i = 0; i < 3; i++) {

		daysClasses[i] = "";
	}

	
	
	//FOR LOOP--------------------------------------------------------------------------------------------------------------
	for (int i = 0; i < classCounter; i++) {
		
		//gets wednesday classes
		if (Wed.equalsIgnoreCase(days[i]) == true) {
			
			//classes at 7.
			if (times[i] == SEVEN) {
				
				//Stops null appearing in file
				if (codes[i].equalsIgnoreCase(classesNamesCodes[0]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[1]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[2]) == true) {
					daysClasses[0] = daysClasses[0] + codes[i] + " ";//Adds to the table
				}
			}
			
			//classes at 8.
			else if (times[i] == EIGHT) {
				
				//Stops null appearing in file
				if (codes[i].equalsIgnoreCase(classesNamesCodes[0]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[1]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[2]) == true) {
					daysClasses[1] = daysClasses[1] + codes[i] + " ";//Adds to the table
				}
			}

			//classes at 9.
			else if (times[i] == NINE) {
				
				//Stops null appearing in file
				if (codes[i].equalsIgnoreCase(classesNamesCodes[0]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[1]) == true
						|| codes[i].equalsIgnoreCase(classesNamesCodes[2]) == true) {
					daysClasses[2] = daysClasses[2] + codes[i] + " "; //Adds to the table
				}
			}
		}

	}
	//Prints table for wednesday
	//--------------------------------------------------------------------------------------------------------------
	System.out.println("Wednesday");//Prints out the day
	System.out.println(TAB+ SEVEN+ TAB + daysClasses[0]);//Prints 7 o'clock classes
	System.out.println(TAB+ EIGHT+ TAB + daysClasses[1]);//Prints 8 o'clock classes
	System.out.println(TAB+ NINE+ TAB + daysClasses[2]);//Prints 9 o'clock classes
	//--------------------------------------------------------------------------------------------------------------
//END OF OPTION 5---------------------------------------------------------------------------------------------------	
	}
}