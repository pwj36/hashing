import java.util.Scanner;
public class DoubleHashing{
	public static void main(String[] args) {
		System.out.println("===============================================================");
		System.out.println("                  DOUBLE HASHING SIMULATION");
		System.out.println("===============================================================");

		Scanner userInput = new Scanner(System.in);

		// User input for table size
		System.out.print("Enter the table size: ");
		int tableSize = userInput.nextInt();

		//Display formula used
		System.out.println();
		System.out.println("Formulas Used:");
		System.out.println("h1(k) = key % table size");
		System.out.println("h2(k) = constant - (key value % constant)");
		System.out.println("Double Hash Function = (h1 + j * h2) % tableSize");

		System.out.println();
		System.out.printf("Table size = %d", tableSize);
		System.out.println();
		System.out.println("Substituting table size value into formula...");
		System.out.printf("h1(k) = key %% %d", tableSize);
		System.out.println();
		System.out.println();
		System.out.printf("Constant value = %d", generatePrimeNumber(tableSize));
		System.out.println();
		System.out.println("Substituting constant value into formula...");
		System.out.printf("h2(k) = %d - (key %% %d)", generatePrimeNumber(tableSize),generatePrimeNumber(tableSize) );
		System.out.println();


		// Array to store keys
		String[] keysEntered = new String[tableSize];

		// Array to store final positions of keys
		String[] hashTable = new String[tableSize]; 


		int noOfElements = 0;
		int count = 0;

		boolean insert = false;
		boolean delete = false;
		boolean find = false;

		//Loop as long as true
		while(true) {
			//User input for insertion/ deletion/ finding keys
			System.out.println();
			System.out.println("===============================================================");
			System.out.println("Options: ");
			System.out.println("[1] Insert Keys");
			System.out.println("[2] Delete Keys");
			System.out.println("[3] Find Keys");
			System.out.println("Enter any other integer/ characters to quit program.");
			System.out.print("Enter your choice: ");

			String userChoice = userInput.next();

			//Program's actions after user's choice
			//User wants to insert keys
			if(userChoice.equals("1")) {
				if (count>=tableSize) {
					System.out.println();
					System.out.println("Table is full! No more values can be inserted into the hash table");
					System.out.println();
				}
				insert = true;
			}

			//User wants to delete keys
			else if (userChoice.equals("2")) {
				delete = true;
			}

			//User wants to find keys
			else if (userChoice.equals("3")) {
				find = true;
			}

			//User enters invalid input
			else {
				System.out.println("Non-integer value entered. Program is closing...");
				break;
			}


			//If user selects insert and the hash table is not full yet.
			while(insert == true && count<tableSize) {
				System.out.println();
				//Ask users to enter a key to insert into the hash table.
				System.out.print("Enter a key to insert [Enter Q/q to exit]: ");
				if(userInput.hasNext()) {
					String keyValue = userInput.next();

					boolean isRepeated = false;
					//If user wants to quit the program
					if (keyValue.equals("Q")||keyValue.equals("q")) {
						insert = false;
						break;
					}

					//Goes through entire array to check with other key values if the current key value is repeated
					for (int count1 = 0; count1 < tableSize; count1++) {
						//If the key entered matches the previous values entered
						if (keysEntered[count1] != null && keysEntered[count1].equals(keyValue)) {
							isRepeated = true;
							System.out.println("The key value entered is repeated. Enter a different key value!");
							System.out.println();
						}
					}
					//If the value is repeated, it should not be added into the list
					if (isRepeated == true) {
						continue;
					}

					//Add user input into the array (to be printed to user)
					keysEntered[count] = keyValue;

					//Total number of elements added is increased by 1
					noOfElements = noOfElements + 1;

					//Determine position with doubleHash()
					int position = doubleHash(tableSize, keyValue, hashTable);

					//If returned position is -1, meaning no positions were found
					if (position == -1) {
						//do nothing
					}
					//Else, place key into calculated position
					else {
						hashTable[position] = keyValue;
						count = count + 1;
					}

				}


				// Print all key values entered
				System.out.println();
				System.out.println("All Successful Keys Entered: ");
				for (int i = 0; i < tableSize; i++) {
					if (keysEntered[i] == null) {
						System.out.print("");
					}
					else {
						System.out.print(keysEntered[i] + " ");
					}
				}
				System.out.println();
				// Print the final hash table
				System.out.println("\nFinal hash table:");
				for (int i = 0; i < tableSize; i++) {
					if (hashTable[i]== null) {
						System.out.println("Position " + i + ": null");
					}
					else {
						System.out.println("Position " + i + ": " + hashTable[i]);
					}
				}


			}

			//If user selects delete
			while(delete==true) {
				System.out.println();
				System.out.print("Enter a key to delete [Enter Q/q to exit]: ");
				if(userInput.hasNext()) {
					String deleteKey = userInput.next();

					if (deleteKey.equals("Q")||deleteKey.equals("q")) {
						delete = false;
						break;
					}

					//deleteKey() returns count value
					count = deleteKey(deleteKey,tableSize, hashTable, Integer.valueOf(count));
				}

				//Prints out final hash table
				System.out.println("\nFinal hash table:");
				for (int i = 0; i < tableSize; i++) {
					if (hashTable[i]== null) {
						System.out.println("Position " + i + ": null");
					}
					else {
						System.out.println("Position " + i + ": " + hashTable[i]);
					}
				}
			}

			//If user selects find
			while(find == true) {
				System.out.println();
				System.out.print("Enter a key to find [Enter Q/q to exit]: ");
				if(userInput.hasNext()) {
					String findKey = userInput.next();

					if (findKey.equals("Q")||findKey.equals("q")) {
						find = false;
						break;
					}

					findKey(findKey, tableSize, hashTable);
				}
			}


		}

		System.out.println();
		System.out.println("===============================================================");
		System.out.println("     THANK YOU FOR USING THE DOUBLE HASHING SIMULATION");
		System.out.println("===============================================================");
		userInput.close();
	}






	//Method to generate prime number for the second hash function.
	private static int generatePrimeNumber(int tableSize) {
		// To make a prime number smaller than table size:
		int primeNum = tableSize - 1;

		// Test if it is a prime number through the trial and division method
		while (primeNum >= 2) {
			boolean isPrimeNo = true;
			int squareRootPrimeNo = (int) Math.sqrt(primeNum);

			for (int i = 2; i <= squareRootPrimeNo; i++) {
				if (primeNum % i == 0) {
					isPrimeNo = false;
					break;
				}
			}

			// If after minus table size by 1, primeNum is a prime number, return it.
			if (isPrimeNo == true) {
				return primeNum;
			}

			primeNum = primeNum - 1;
		}
		// No prime number found smaller than tableSize, use 2 as prime number
		return 2; 
	}

	//double hash method
	private static int doubleHash(int tableSize, String keyValue, String[] hashTable) {
		//print out generated hash code
		System.out.printf("The generated hash code for key %s is: %d",keyValue, keyValue.hashCode());
		System.out.println();

		// First hash function:
		int h1 = keyValue.hashCode() % tableSize;
		System.out.printf("h1(%s) = %s %% %d%n", keyValue.hashCode(), keyValue.hashCode(), tableSize);
		System.out.println();

		if (h1 < 0) {
			System.out.println("Positon calculated: "+h1);
			System.out.println("Position generated is a negative value! Generating positive value for position....");
			h1 = Math.abs(h1);
		}

		// Generate prime number for second hash function
		int constant = generatePrimeNumber(tableSize);

		// Second hash function:
		int h2 = constant - (keyValue.hashCode() % constant);

		// Double hashing function:
		int position = h1;
		System.out.printf("h1(%s) = %s%n", keyValue.hashCode(), position);
		System.out.printf("Position of key %s = %s%n%n", keyValue, position);

		int j = 0;
		int iterationsAllowed = 0; 

		// The array is not empty, meaning a collision occurred. Find the final position using double hashing.
		// Establish iteration constraint to prevent infinite loop.
		while ((!(hashTable[position]== null)) && iterationsAllowed < tableSize) {
			System.out.println("A collision has occurred!");
			System.out.println();
			System.out.println("Calculating new position...");
			j++;
			System.out.printf("h2(%s) = %d - (%s %% %d)%n", keyValue.hashCode(), constant, keyValue.hashCode(), constant);
			System.out.printf("h2(%s) = %d%n", keyValue.hashCode(), h2);
			System.out.println("Using double hash function to calculate position...");
			System.out.printf("Position = (%d + %d * %d) %% %d %n", h1, j, h2, tableSize);

			//calculate new position
			position = (h1 + j * h2) % tableSize;
			System.out.printf("Position of key %s = %d%n%n", keyValue, position);
			iterationsAllowed = iterationsAllowed + 1;
		}

		//If there are no possible positions found even after looping the entire table:
		if (iterationsAllowed == tableSize) {
			System.out.println("No positions can be found for this key. The key will not be inserted in the hash table.");
			System.out.println();            
			return -1;
		} 
		//else if position is found, return position.
		else {
			return position;
		}

	}
	//method to delete the key
	private static int deleteKey(String deleteKey, int tableSize, String[] hashTable, Integer count) {
		//loops through hashTable array to search for the key
		for (int position = 0; position < tableSize; position++) {
			//if the position is occupied and the deleted key equals the key in that position
			if (hashTable[position] != null && hashTable[position].equals(deleteKey)) {
				//remove the key by making it null
				hashTable[position] = null; 
				//Display message that the key got deleted
				System.out.println("The key has been successfully deleted!");
				System.out.printf("The key %s has been deleted from position %d%n%n", deleteKey, position);
				count = count - 1;
				return count;
			}
		}
		//if the key that the user wants to delete is not in the hash table
		System.out.println("Unable to delete key!");
		System.out.printf("The key %s could not be found in the hash table! %n%n", deleteKey);
		return count;
	}

	//method to find the key
	private static void findKey(String findKey, int tableSize, String[] hashTable) {
		for (int position = 0; position < tableSize; position++) {
			//if the position is occupied and the key the user wants to find equals the key in that position
			if (hashTable[position] != null && hashTable[position].equals(findKey)) {
				System.out.printf("The key %s has been found in position %d%n%n", findKey, position);
				return;
			}
		}
		System.out.printf("The key %s could not be found in the hash table! %n%n", findKey);
	}

}