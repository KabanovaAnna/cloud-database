package ui;

import org.apache.log4j.Logger;

public class Application {

	public static void main(String[] args) {
		System.out.println("Hello World");
		LoggerInitializer li = new LoggerInitializer();
		Logger mylogger = li.initializeLogger();
		mylogger.info("my first log");
	}
	
}
