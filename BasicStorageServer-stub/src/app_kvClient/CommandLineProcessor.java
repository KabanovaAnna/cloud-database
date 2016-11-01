package app_kvClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import logger.LogSetup;

import org.apache.log4j.*;

import client.KVCommInterface;
import common.messages.KVMessage;
import common.messages.KVMessageItem;

/** 
 * This class is needed to process the user input from the console. 
 * It reads the input, analyzes it and differentiates the command from the 
 * rest of the message.
 * This class gets the address and the port number and creates an Object of type
 * Communicator that is used to build the connection to the server.
 * It also provides the commands: connect, disconnect, put, get, logLevel, help and 
 * quit from which the user can chose. 
 * 
 * @see Communicator
 */

public class CommandLineProcessor implements KVCommInterface{

	private static String[] input;
	private static KVCommInterface kvStore;
	private static Logger logger;
	private static boolean quit;

	private static final String LINE_START = "EchoClient> ";
	private static final String HELP_MESSAGE = "Welcome to EchoClient!\r\n\r\n"
			+ "Commands:\r\nconnect <address> <port> - tries to establish a TCP"
			+ "- connection to the server based on the given server address and "
			+ "the port\r\ndisconnect - tries to disconnect from the connected "
			+ "server\r\nput<key> <value> - inserts a key-value pair into the "
			+ "storage server data structures or updates the current value\r\n "
			+ "if the server already contains the specified key or deletes the"
			+ "entry if for the given key if <value> equals null\r\nget <key> - "
			+ "retrievs the value for the given key from the storage server\r\n"
			+ "logLevel<level> - sets the logger to the specified log level\r\n"
			+ "help - information about all possible commands\r\nquit - tears down"
			+ "the active connection to the server and exits the program execution\n";

	public static void readAndProcessInput() {
		initializeLogger();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (!quit) {
			readInputLine(br);
		}
	}

	private static void initializeLogger() {
		logger = Logger.getRootLogger();
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

	private void parseInput() {
		String command = input[0];
		String key = input[1];
		String value = input[2];
		switch (command) {
		case "connect":
			connect();
			break;
		case "disconnect":
			disconnect();
			break;
		case "put":
			try {
				put(key, value);
			} catch (Exception e) {
				errorMessage();
			}
			break;
		case "get":
			try {
				get(key);
			} catch (Exception e) {
				errorMessage();
			}
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

	@Override
	public void connect() {
		try {
			kvStore.connect();
			System.out.println("Connection was established successfuly");
		} catch (Exception e) {
			System.out.println(LINE_START + "Error. Connection was not established successfuly");
			logger.info(e.getMessage());
		}
	}

	@Override
	public void disconnect() {
		kvStore.disconnect();
			System.out.println(LINE_START + "Connection terminated.");
	}

	@Override
	public KVMessage put(String key, String value) throws Exception {
		KVMessage newTuple = null;
		try {
			newTuple = new KVMessageItem(KVMessage.StatusType.PUT, key, value);
		} catch (Exception e) {
			
		}
		return newTuple;
	}

	@Override
	public KVMessage get(String key) throws Exception {
		KVMessage getValue = null;
		try {
			getValue = new KVMessageItem(KVMessage.StatusType.GET, key);
		} catch (Exception e) {
			
		}
		return null;
	};
	private static void logLevel() {
		if (input.length >= 2) {
			String logLevel = input[1];
			if (LogSetup.isValidLevel(logLevel)) logger.setLevel(Level.toLevel(logLevel));
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
	}
}
