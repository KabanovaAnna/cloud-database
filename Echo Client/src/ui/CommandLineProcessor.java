package ui;
import java.util.*;
public class CommandLineProcessor {
	
	String [] input;
	String adress;
	String port;

	void readString() {
		int i=0;
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			input[i] = scanner.next();
			i++;
		}
	}
	
	void analyseInput() {
		switch (input[0]) {
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
	
	void connectToServer(){
		adress = input[1];
		port = input[2];
		//TODO;;
		System.out.println("Connection to MSGR Server estblished: " + adress + "/" + port);
	}
	
	void disconnect(){
		//TODO;;
		System.out.println("Connection terminated: " + adress + "/" + port);
	};
	
	String send(){
		String msg = "";
		for (int i=1; i<input.length; i++) {
			msg = msg + input[i];
		}
		return msg;
	};
	
	void logLevel(){
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
	
	void help(){
		
	};
	
	void quit(){
		//TODO;;
		System.out.println("Aplication exit!");
	};
	
	void sendError(){
		System.out.println("Error! Unknown command!");
		help();
	};
	
}
