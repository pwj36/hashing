import java.util.LinkedList;
import java.util.Scanner;
import java.util.ArrayList;
public class SeparateChaining{
	public static void main(String[] args) {
		System.out.println("===============================================================");
		System.out.println("                SEPARATE CHAINING SIMULATION");
		System.out.println("===============================================================");

		Scanner userInput = new Scanner(System.in);

		// User input for table size
		System.out.print("Enter the table size: ");
		int tableSize = userInput.nextInt();

		System.out.println();
		System.out.println("Formula Used:");
		System.out.println("h(k) = key % table size");

		System.out.println();
		System.out.printf("Table size = %d", tableSize);
		System.out.println();
		System.out.println("Substituting table size value into formula...");
		System.out.printf("h(k) = key %% %d", tableSize);
		System.out.println();
		System.out.println();

		// Create an array, where each array position will have a linked list
		ArrayList<LinkedList<String>> hashTable = new ArrayList<>(tableSize);
		for (int i = 0; i < tableSize; i++) {
			hashTable.add(new LinkedList<String>());
		}

		int noOfElements = 0;

		//Create array to store all keys entered by user
		ArrayList<String> keys = new ArrayList<>();

		boolean insert = false;
		boolean delete = false;
		boolean find = false;

		while(true) {
			//User input for hashing choice
			System.out.println();
			System.out.println("===============================================================");
			System.out.println("Options: ");
			System.out.println("[1] Insert Keys");
			System.out.println("[2] Delete Keys");
			System.out.println("[3] Find Keys");
			System.out.println("Enter any other integer/ characters to quit program.");
			System.out.print("Enter your choice: ");
			String userChoice = userInput.next();

			if(userChoice.equals("1")) {
				insert = true;
			}
			else if (userChoice.equals("2")) {
				delete = true;
			}

			else if (userChoice.equals("3")) {
				find = true;
			}
			else {
				System.out.println("Non-integer value entered. Program is closing...");
				break;
			}


			// Loop to let users enter values into the hash table
			while(insert == true) {
				System.out.print("Enter a key to insert [Enter Q/q to exit]: ");
				if(userInput.hasNext()) {
					String keyValue = userInput.next();

					if (keyValue.equals("Q")||keyValue.equals("q")) {
						insert = false;
						break;
					}

					//add user input into the array (to be printed to user)
					keys.add(keyValue);

					insertKey(keyValue, tableSize, hashTable);

					//Total number of elements added is increased by 1
					noOfElements = noOfElements + 1;

				}

				//print out all the keys in the array.
				System.out.println();
				System.out.println("All Successfuly Keys Entered: ");
				for (int i = 0; i < noOfElements; i++) {
					System.out.print(keys.get(i) + " ");
				}
				System.out.println();

				//print out the hash table
				System.out.println();
				System.out.println("Final hash table: ");
				//outer loop to loop through array
				for (int i = 0; i < tableSize; i++) {
					System.out.printf("Position %d: ", i);
					//inner loop to loop through linked list
					for (String key : hashTable.get(i)) {
						System.out.printf("%s --> ", key);    		
					}
					System.out.println("null");	
				}

			}

			while(delete==true) {
				System.out.print("Enter a key to delete [Enter Q/q to exit]: ");
				if(userInput.hasNext()) {
					String deleteKey = userInput.next();

					if (deleteKey.equals("Q")||deleteKey.equals("q")) {
						delete = false;
						break;
					}

					deleteKey(deleteKey,tableSize, hashTable);
				}

				System.out.println("\nUpdated hash table after deletion:");
				//print out the hash table
				System.out.println();
				System.out.println("Final hash table: ");
				//outer loop to loop through array
				for (int i = 0; i < tableSize; i++) {
					System.out.printf("Position %d: ", i);
					//inner loop to loop through linked list
					for (String key : hashTable.get(i)) {
						System.out.printf("%s --> ", key);    		
					}
					System.out.println("null");	
				}
			}

			while(find==true) {
				System.out.print("Enter a key to find [Enter Q/q to exit]: ");
				if(userInput.hasNext()) {
					String findKey = userInput.next();

					if (findKey.equals("Q")||findKey.equals("q")) {
						find = false;
						break;
					}

					findKey(findKey,tableSize, hashTable);
				}
			}



		}

		System.out.println();
		System.out.println("===============================================================");
		System.out.println("     THANK YOU FOR USING THE SEPARATE CHAINING SIMULATION");
		System.out.println("===============================================================");
		userInput.close();
	}


	private static void insertKey(String keyValue, int tableSize, ArrayList<LinkedList<String>> hashTable) {
		//print out generated hash code
		System.out.printf("The generated hash code for key %s is: %d",keyValue, keyValue.hashCode());
		System.out.println();
		//determine hash function 
		int hashFunction = keyValue.hashCode() % tableSize;
		System.out.printf("h(%s) = %s %% %d", keyValue.hashCode(), keyValue.hashCode(), tableSize);
		System.out.println();

		if (hashFunction < 0) {
			System.out.println("Positon calculated: "+hashFunction);
			System.out.println("Position generated is a negative value! Generating positive value for position....");
			hashFunction = Math.abs(hashFunction);
		}
		//use the hash function to determine the function;
		int position = hashFunction;


		System.out.printf("Position of key %s = %d%n%n", keyValue, position);

		//insert value into array slot
		hashTable.get(position).add(keyValue);

		//if there is already values in the linked list, display collision occurred, placing into linked list message.
		if (hashTable.get(position).size() > 1) {
			System.out.println("A collision has occurred!");
			System.out.println("The key would be entered into the linked list of posiiton "+position);
			System.out.println();
		}

	}

	private static void deleteKey(String deleteKey, int tableSize, ArrayList<LinkedList<String>>  hashTable) {
		for (int position = 0; position < tableSize; position++) {
			LinkedList<String> list = hashTable.get(position);
			if (list.contains(deleteKey)) {
				list.remove(deleteKey);
				System.out.println("The key has been successfully deleted!");
				System.out.printf("The key %s has been deleted from position %d%n%n", deleteKey, position);
				return;
			}
		}
		//if the key that the user wants to delete is not in the hash table
		System.out.println("Unable to delete key!");
		System.out.printf("The key %s could not be found in the hash table! %n%n", deleteKey);
	}

	private static void findKey(String findKey, int tableSize, ArrayList<LinkedList<String>>  hashTable) {
		for (int position = 0; position < tableSize; position++) {
			LinkedList<String> list = hashTable.get(position);
			if (list.contains(findKey)) {
				System.out.printf("The key %s has been found in position %d%n%n", findKey, position);
				return;
			}
		}
		System.out.printf("The key %s could not be found in the hash table! %n%n", findKey);
	}

}