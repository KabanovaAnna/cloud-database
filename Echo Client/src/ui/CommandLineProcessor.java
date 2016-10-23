package ui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.*;

import communication.Communicator;
import communication.StringMarshaller;

public class CommandLineProcessor {
	
	private static String[] input;
	private static Communicator communicator;
	private static Logger logger;
	private static boolean quit;
	
	private static final int MAX_MESSAGE_SIZE = 128000;
	private static final String LINE_START = "EchoClient> ";

	public static void readInput() {
		initializeLogger();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (!quit) {
			readInputLine(br);
		}
	}
	
	private static void initializeLogger() {
		LoggerInitializer loggerInitializer = new LoggerInitializer(CommandLineProcessor.class);
		logger = loggerInitializer.initialize(Level.ALL);
	}
	
	private static void readInputLine(BufferedReader br) {
		System.out.print(LINE_START);
		try {
			String inputLine = br.readLine();
			input = inputLine.split(" ");
			analyseInput();
		} catch (IOException e) {
			logger.error("Error caused by wrong console input");
		}
	}
	
	private static void analyseInput() {
		String command = input[0];
		switch (command) {
			case "connect": connectToServer();
			        		break;
			case "disconnect": disconnect();
							break;
			case "send": sendMessage();
							break;	    
			case "logLevel": logLevel();
							break;
			case "help": help();
							break;
			case "quit": quit();
							break;
			default: errorMessage();
		}
	}
	
	private static void connectToServer() {
		if (input.length > 2) {
			String address = input[1];
			int port = Integer.valueOf(input[2]);
			try {
				communicator = new Communicator(address, port);
				StringMarshaller stringMarshaller = new StringMarshaller();
				System.out.println(stringMarshaller.unmarshal(communicator.receive()));
			} catch (IOException e) {
				System.out.println(LINE_START + "Connection could not be established");
			}
		} else {
			errorMessage();
		}
	}
	
	private static void disconnect(){
		if (communicator == null) System.out.println(LINE_START + "Could not disconnect from server. "
				+ "Make sure that connection was established.");
		else {
			try {
				communicator.disconnectFromServer();
				System.out.println(LINE_START + "Connection terminated: " + communicator.getAddress()
						+ "/" + communicator.getPort());
			} catch (IOException e) {
				logger.error("Could not disconect from server");
			} 
		}
	};
	
	private static void sendMessage() {
		if (communicator != null) {
			String message = convertInputToMessage(input);
			if (isMessageSizeValid(message)) {
				StringMarshaller stringMarshaller = new StringMarshaller();
				String recievedMessage = "";
				try {
					communicator.send(stringMarshaller.marshal(message));
					recievedMessage = stringMarshaller.unmarshal(communicator.receive());
					System.out.println(LINE_START + recievedMessage); 
				} catch (IOException e) {
					System.out.println(LINE_START + "Error. Not connected.");
				}
			}
		} else {
			System.out.println(LINE_START + "Error. Not connected.");
		}
	};
	
	private static String convertInputToMessage(String[] stringArray) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 1; i < stringArray.length; i++) {
			stringBuilder.append(stringArray[i] + " ");
		}
		return stringBuilder.toString();
	}
	
	private static boolean isMessageSizeValid(String message) {
		return message.getBytes().length <= MAX_MESSAGE_SIZE;
	}
	
	private static void logLevel(){
		if (input.length >= 2) {
			String logLevel = input[1];
			logger.setLevel(Level.toLevel(logLevel));
			System.out.println(LINE_START + "Current logging level: " + logger.getLevel());
		} else {
			errorMessage();
		}
	};
	
	private static void help() {
		System.out.println(LINE_START + "This message should describe how to help!");
	};
	
	private static void quit() {
		try {
			if (communicator != null) communicator.disconnectFromServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(LINE_START + "Application exit. Bye ;)");
		quit = true;
	};
	
	private static void errorMessage(){
		System.out.println(LINE_START + "Error. Unknown command.");
		help();
	};
	
}
