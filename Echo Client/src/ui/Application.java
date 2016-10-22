package ui;

import org.apache.log4j.Logger;

public class Application {

	public static void main(String[] args) {
		initializeLogger();
	}
	
	private static void initializeLogger() {
		LoggerInitializer li = new LoggerInitializer(Application.class);
		Logger logger = li.initialize();
		logger.info("Logger initialized");
	}
}
