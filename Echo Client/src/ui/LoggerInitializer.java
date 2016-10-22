package ui;

import java.io.IOException;

import org.apache.log4j.*;

public class LoggerInitializer {

	private Logger logger;

	public Logger initializeLogger() {
		logger = Logger.getLogger(Application.class); 
		logger.setLevel(Level.ALL);
		SimpleLayout sl = new SimpleLayout();
		ConsoleAppender consoleAppender = new ConsoleAppender(sl);
		logger.addAppender(consoleAppender);
		String logDir = "logs/client.log";
		String pattern = "%d{ISO8601} %-5p [%t] %c: %m%n";
		PatternLayout pLayout = new PatternLayout(pattern);
		FileAppender fa;
		try {
			fa = new FileAppender(pLayout, logDir, true);
			logger.addAppender(fa);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logger;
	}

}
