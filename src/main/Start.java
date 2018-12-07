package main;

import java.util.Scanner;

import constants.Constants;
import games.ConnectFour;

public class Start {
	
	public static void main(String[] args) {
		System.out.println("Welcome!");
		
		String tInput = "";
		Scanner tScanner = new Scanner(System.in);
		
		while(!tInput.equals("exit")) {
			System.out.println("The following commands are available:\n"
					+ "\"cf\" - starts the game \"Connect Four\"\n"
					+ "\"exit\" - terminates the program\n\n");
			System.out.print("> ");
			
			tInput = tScanner.nextLine();
			
			switch(tInput) {
				case "cf": 		ConnectFour tConnectFour = new ConnectFour();
								tConnectFour.startConnectFour(tScanner);
								break;
				case "exit": 	System.out.println("Goodbye!");
								tScanner.close();
								break;
				default: 		System.out.println(Constants.OUTPUT_InputNotValid);
								break;
			}
		}
	}
}


