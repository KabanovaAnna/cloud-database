package ui;

import org.apache.log4j.*;

public class Application {

	public static void main(String[] args) throws Exception {
		initializeLogger();
		CommandLineProcessor.readInput();
	}
	
	private static void initializeLogger() {
		LoggerInitializer li = new LoggerInitializer(Application.class);
		Logger logger = li.initialize(Level.ALL);
		logger.info("Application started");
	}
}
