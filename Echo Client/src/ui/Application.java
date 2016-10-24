package ui;

import org.apache.log4j.*;

public class Application {

	public static void main(String[] args) throws Exception {
		initializeLogger();
		CommandLineProcessor.readInput();
	}
	
	private static void initializeLogger() {
		Logger logger = LoggerInitializer.initialize(Application.class);
		logger.info("Application started");
	}
}
