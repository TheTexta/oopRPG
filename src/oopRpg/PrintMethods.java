package oopRpg;

import java.util.ArrayList;

public final class PrintMethods {

	public static void printLoading() throws InterruptedException {
		for (int i = 0; i < 45; i++) {
			System.out.print(". ");
			//Thread.sleep(25);
		}
		System.out.print("\n");

	}

	// Prints a message to console with a 20 ms delay between every character
	public static void delayPrint(String msg) throws InterruptedException {
		for (int i = 0; i < msg.length(); i++) {
			System.out.print(msg.charAt(i));
			//Thread.sleep(20);
		}
	}

	// Print a message with a specified delay (ms) between every character
	public static void delayPrint(String msg, int delay) throws InterruptedException {
		for (int i = 0; i < msg.length(); i++) {
			System.out.print(msg.charAt(i));
			//Thread.sleep(delay);
		}
	}

	// Same as delayPrint but goes down a line at the end
	public static void delayPrintln(String msg) throws InterruptedException {
		delayPrint(msg);
		System.out.print("\n");
	}

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

	public static void invalidChoice(String msg) {
		System.out.print("Invalid Choice. Please input " + msg + ": ");
	}

	public static void invalidChoice() {
		System.out.print("Invalid Choice. Please try again: ");
	}

	private static String genPadding(int totalLength, String input, boolean front) {
		int inputLength = input.length();
		float padding = (float) ((totalLength - inputLength) * 0.5);

		int paddingInt;
		// Check for DC places
		if (padding % 1.0 > 0) {
			if (front) {
				paddingInt = (int) Math.floor(padding);
			} else {
				paddingInt = (int) Math.ceil(padding);
			}
		} else {

			paddingInt = (int) padding;
		}

		String paddingSpaces = genString(paddingInt, " ");

		return paddingSpaces;
	}

	private static String genString(int num, String letter) {
		String spaces = new String();
		for (int i = 0; i < num; i++) {
			spaces += letter;
		}
		return spaces;
	}

	public static void printArray(String[][] txt, int padding) throws InterruptedException {
		String devider = genString((txt[0].length * padding) + txt[0].length + 1, "-");
		String toPrint = devider;

		for (int r = 0; r < txt.length; r++) {
			toPrint += "\n|";
			for (int i = 0; i < txt[r].length; i++) {
				toPrint += genPadding(padding, txt[r][i], false) + txt[r][i]
						+ genPadding(padding, txt[r][i], true) + "|";
			}

		}
		toPrint += "\n" + devider;
		delayPrint(toPrint.split("\n"), 30);
	}

}
