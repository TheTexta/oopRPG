package oopRpg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// TODO remember to comment out the thread.sleep
public final class PrintMethods {
	public final static int consoleWidth = 90;
	public final static int defaultDelay = 20;

	// Prints a loading message to console
	public static void printLoading() throws InterruptedException {
		for (int i = 0; i < consoleWidth / 2; i++) {
			System.out.print(". ");
			// Thread.sleep(25);
		}
		System.out.print("\n");

	}

	// Prints a itteration of damagebar
	public static double damageBar(int delay) {

		int critZone = consoleWidth / 8;
		int begCritZone = consoleWidth / 2 - critZone;
		int endCritZone = consoleWidth / 2 + critZone;

		int begHitZone = consoleWidth / 4;
		int endHitZone = consoleWidth - begHitZone;

		Scanner in = new Scanner(System.in);

		boolean interrupted = false;

		double damageMulti = 0.0;
		boolean increasing = true;
		int position = 0;
		while (!interrupted) {
			if (increasing)
				position++;
			else
				position--;

			if (position > consoleWidth-1) {
				position--;
				increasing = false;
			} else if (position < 0) {
				position++;
				increasing = true;
			}

			for (int i = 0; i < 30; i++) {
				System.out.print("\n");
			}
			for (int i = 0; i < consoleWidth; i++) {
				if (i > begHitZone && i < endHitZone)
					if (i > begCritZone && i < endCritZone)
						System.out.print("#");
					else
						System.out.print("=");
				else
					System.out.print("-");

			}
			System.out.print("\n");
			for (int i = 0; i < consoleWidth; i++) {
				if (position == i)
					System.out.print("^");
				else
					System.out.print("-");
			}
			// introduce a delay of 100 milliseconds and check for keyboard input
			try {
				Thread.sleep(delay);
				if (System.in.available() > 0) {
					// keyboard input detected, set interrupted flag and exit the loop
					interrupted = true;
					break;
				}
			} catch (InterruptedException | IOException e) {
				((Throwable) e).printStackTrace();
			}

		}
		if (interrupted) {
			if (position > begCritZone && position < endCritZone) {
				damageMulti = 2;
			} else if (position > begHitZone && position < endHitZone) {
				damageMulti = 1;
			} else {
				damageMulti = 0.5;
			}
		}
		return damageMulti;

	}

	// Prints a message to console with a 20 ms delay between every character
	public static void delayPrint(String msg) throws InterruptedException {
		for (int i = 0; i < msg.length(); i++) {
			System.out.print(msg.charAt(i));
			// Thread.sleep(defaultDelay);
		}
	}

	// Print a message with a specified delay (ms) between every character
	public static void delayPrint(String msg, int delay) throws InterruptedException {
		for (int i = 0; i < msg.length(); i++) {
			System.out.print(msg.charAt(i));
			// Thread.sleep(delay);
		}
	}

	// Same as delayPrint but goes down a line at the end
	public static void delayPrintln(String msg) throws InterruptedException {
		delayPrint(msg);
		System.out.print("\n");
	}

	// Same as delayPrint but goes down a line at the end and has a specified delay
	// (ms) between every character
	public static void delayPrintln(String msg, int delay) throws InterruptedException {
		delayPrint(msg, delay);
		System.out.print("\n");
	}

	public static void delayPrint(String[] msg, int delay) throws InterruptedException {
		for (String i : msg) {
			System.out.println(i);
			Thread.sleep(delay);
		}
	}

	public static void delayPrint(String[] msg) throws InterruptedException {
		delayPrint(msg, defaultDelay);
	}

	// Prints an error message indicating that the user input was invalid and
	// prompts the user to input a valid value
	public static void invalidChoice(String msg) {
		System.out.print("Invalid Choice. Please input " + msg + ": ");
	}

	// Prints a generic error message indicating that the user input was invalid and
	// prompts the user to try again
	public static void invalidChoice() {
		System.out.print("Invalid Choice. Please try again: ");
	}

	// This method generates a string of spaces to be used for padding.
	// Parameters:
	// - totalLength: the total length of the string after padding
	// - input: the string to be padded
	// - front: a boolean indicating whether the padding should be added to the
	// front (true) or the back (false) of the input string
	// Returns:
	// - a string of spaces with a length that will pad the input string to the
	// desired length
	private static String genPadding(int totalLength, String input, boolean front) {
		int inputLength = input.length();
		float padding = (float) ((totalLength - inputLength) * 0.5);

		int paddingInt;
		// Check for decimal places
		if (padding % 1.0 > 0) {
			if (front) {
				paddingInt = (int) Math.floor(padding);
			} else {
				paddingInt = (int) Math.ceil(padding);
			}
		} else {
			paddingInt = (int) padding;
		}

		// Generate string of spaces to be used for padding
		String paddingSpaces = genString(paddingInt, " ");

		return paddingSpaces;
	}

	// This method generates a string consisting of a given letter repeated a given
	// number of times.
	// Parameters:
	// - num: the number of times to repeat the letter
	// - letter: the letter to repeat
	// Returns:
	// - a string consisting of the letter repeated num times
	private static String genString(int num, String letter) {
		String spaces = new String();
		for (int i = 0; i < num; i++) {
			spaces += letter;
		}
		return spaces;
	}

	// This method prints a two-dimensional array of strings as a formatted table.
	// Parameters:
	// - txt: the two-dimensional array of strings to print
	// - padding: the amount of padding to add to each cell in the table
	// Throws:
	// - an Exception with the message "txt is null" if txt is an empty array
	public static void printArray(String[][] txt, int padding) throws Exception {
		if (txt.length == 0)
			throw new Exception("txt is null");

		// Generate divider string for table
		String devider = genString((txt[0].length * padding) + txt[0].length + 1, "-");
		String toPrint = devider;

		// Iterate over each row in the input array and print each cell in a formatted
		// table cell
		for (int r = 0; r < txt.length; r++) {
			toPrint += "\n|";
			for (int i = 0; i < txt[r].length; i++) {
				toPrint += genPadding(padding, txt[r][i], false) + txt[r][i] + genPadding(padding, txt[r][i], true)
						+ "|";
			}
		}

		// Add divider string to end of table and print with delay
		toPrint += "\n" + devider;
		delayPrint(toPrint.split("\n"), 30);
	}

	// This method prints an ArrayList of Item objects as a formatted table.
	// Parameters:
	// - toPrint: the ArrayList of Item objects to print
	// Throws:
	// - an Exception with the message "txt is null" if txt is an empty array
	public static void printArrayList(ArrayList<Item> toPrint) throws Exception {
		// Convert ArrayList to two-dimensional array of strings
		String[][] toPrintArray = new String[toPrint.size()][1];
		for (int i = 0; i < toPrint.size(); i++) {
			toPrintArray[i][0] = toPrint.get(i).getName();
		}

		// Call printArray method to print the converted array as a table
		printArray(toPrintArray, 84);
	}

}
