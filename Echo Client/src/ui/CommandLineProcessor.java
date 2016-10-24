package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.*;

import communication.logic.Communicator;

public class CommandLineProcessor {

	private static String[] input;
	private static Communicator communicator;
	private static Logger logger;
	private static boolean quit;

	private static final String LINE_START = "EchoClient> ";
	private static final String HELP_MESSAGE = "Welcome to EchoClient!\r\n\r\n"
			+ "Commands:\r\nconnect <address> <port> - tries to establish a TCP"
			+ "- connection to the server based on the given server address and "
			+ "the port\r\ndisconnect - tries to disconnect from the connected "
			+ "server\r\nsend <message> - sends a text message to the echo server "
			+ "according to the communication protocol\r\nlogLevel<level> - sets "
			+ "the logger to the specified log level\r\nhelp - information about "
			+ "all possible commands\r\nquit - tears down the active connection "
			+ "to the server and exits the program execution\n";

	public static void readInput() {
		initializeLogger();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (!quit) {
			readInputLine(br);
		}
	}

	private static void initializeLogger() {
		logger = LoggerInitializer.initialize(CommandLineProcessor.class);
	}

	private static void readInputLine(BufferedReader br) {
		System.out.print(LINE_START);
		try {
			String inputLine = br.readLine();
			input = inputLine.split(" ");
			parseInput();
		} catch (IOException e) {
			logger.error("Error caused by wrong console input");
		}
	}

	private static void parseInput() {
		String command = input[0];
		switch (command) {
		case "connect":
			connectToServer();
			break;
		case "disconnect":
			disconnect();
			break;
		case "send":
			sendMessage();
			break;
		case "logLevel":
			logLevel();
			break;
		case "help":
			help();
			break;
		case "quit":
			quit();
			break;
		default:
			errorMessage();
		}
	}

	private static void connectToServer() {
		if (input.length > 2) {
			String address = input[1];
			String port = input[2];
			createCommunicator(address, port);
		} else {
			errorMessage();
		}
	}

	private static void createCommunicator(String address, String port) {
		communicator = new Communicator();
		if (communicator.establishConnection(address, port)) {
			System.out.println(LINE_START + communicator.receiveMessage());
		} else {
			System.out.println(LINE_START
					+ "Error. Connection was not established successfuly");
		}
	}

	private static void disconnect() {
		if (communicator != null && communicator.disconnect()) {
			System.out.println(LINE_START + "Connection terminated: "
					+ communicator.getAddress() + "/" + communicator.getPort());
		} else {
			System.out.println(LINE_START
					+ "Could not disconnect from server. "
					+ "Make sure that connection was established.");
		}
	}

	private static void sendMessage() {
		String message = convertInputToMessage(input);
		if (communicator != null && communicator.sendMessage(message)) {
			System.out.println(LINE_START + communicator.receiveMessage());
		} else {
			System.out.println(LINE_START + "Message was not sent successfuly");
		}
	};

	private static String convertInputToMessage(String[] stringArray) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 1; i < stringArray.length; i++) {
			stringBuilder.append(stringArray[i] + " ");
		}
		return stringBuilder.toString();
	}

	private static void logLevel() {
		if (input.length >= 2) {
			String logLevel = input[1];
			LoggerInitializer.changeLogLevel(logLevel);
			System.out.println(LINE_START + "Current logging level: "
					+ logger.getLevel());
		} else {
			errorMessage();
		}
	};

	private static void help() {
		System.out.println(LINE_START + HELP_MESSAGE);
	};

	private static void quit() {
		System.out.println(LINE_START + "Application exit. Bye ;)");
		quit = true;
	};

	private static void errorMessage() {
		System.out.println(LINE_START + "Error. Unknown command.");
		help();
	};

}
