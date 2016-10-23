package ui;

import java.io.IOException;

import org.apache.log4j.*;

/** 
 * This class is needed to create logger and enable logging for console and file,
 * that can be found under "logs/client.log". Information is logged at all levels.
 */
public class LoggerInitializer {

	private Logger logger;
	
	private final String LOG_DIR = "logs/client.log";
	private final String FILE_LOG_PATTERN = "%d{ISO8601} %-5p [%t] %c: %m%n";
	
	public LoggerInitializer(Class c) {
		logger = Logger.getLogger(c); 
	}
	
	public Logger initialize(String logLevel) {
		logger.setLevel(Level.toLevel(logLevel));
		addConsoleLogging();
		addFileLogging();
		return logger;
	}

	private void addConsoleLogging() {
		SimpleLayout sl = new SimpleLayout();
		ConsoleAppender consoleAppender = new ConsoleAppender(sl);
		logger.addAppender(consoleAppender);
	}
	
	private void addFileLogging() {
		String pattern = FILE_LOG_PATTERN;
		PatternLayout patternLayout = new PatternLayout(pattern);
		FileAppender fa;
		try {
			fa = new FileAppender(patternLayout, LOG_DIR, true);
			logger.addAppender(fa);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
