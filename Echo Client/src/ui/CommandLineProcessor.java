package ui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import communication.Communicator;
public class CommandLineProcessor {
	
	private static String[] input;
	private static String adress;
	private static String port;

	public static void readInput() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean quit = false;
		while (!quit) {
			System.out.print("EchoClient> ");
			String inputLine = br.readLine();
			input = inputLine.split(" ");
			analyseInput();
		}
	}
	
	private static void analyseInput() {
		String command = input[0];
		switch (command) {
			case "connect": connectToServer();
			        		break;
			case "disconnect": disconnect();
							break;
			case "send": String msg = send();
					//TODO;;
							break;	    
			case "logLevel": logLevel();
							break;
			case "help": help();
							break;
			case "quit": quit();
							break;
			default: sendError();
		}
	}
	
	private static void connectToServer() {
		String ipAdress = input[1];
		int port = Integer.valueOf(input[2]);
		Communicator communicator;
		try {
			communicator = new Communicator(ipAdress, port);
			System.out.println(Arrays.toString(communicator.receive()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void disconnect(){
		//TODO;;
		System.out.println("Connection terminated: " + adress + "/" + port);
	};
	
	private static String send(){
		String msg = "";
		for (int i=1; i<input.length; i++) {
			msg = msg + input[i];
		}
		return msg;
	};
	
	private static void logLevel(){
		switch (input[1]) {
		case "ALL": break;
		case "DEBUG": break;
		case "INFO": break;
		case "WARN": break;
		case "ERROR": break;
		case "FATAL": break;
		case "OFF": break;
		}
		//TODO;;
		System.out.println("Current log4j log level: " + input[1]);
	};
	
	private static void help(){
		
	};
	
	private static void quit(){
		//TODO;;
		System.out.println("Aplication exit!");
	};
	
	private static void sendError(){
		System.out.println("Error! Unknown command!");
		help();
	};
	
}
