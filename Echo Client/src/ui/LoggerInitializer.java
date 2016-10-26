package ui;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.*;

/** 
 * This class is needed to create logger and enable logging for console and file,
 * that can be found under "logs/client.log". Logging level can be set dynamically.
 * Default level: Error.
 */
public class LoggerInitializer {
	
	private static final String LOG_DIR = "logs/client.log";
	private static final String FILE_LOG_PATTERN = "%d{ISO8601} %-5p [%t] %c: %m%n";
	
	private static Map<String, Logger> loggers = new HashMap<>();
	private static Level currentLogLevel = Level.ERROR;
	
	public static Logger initialize(Class c) {
		if (isCreated(c)) {
			return loggers.get(c.getName());
		} else {
			return createNewLogger(c);
		}
	}

	private static boolean isCreated(Class c) {
		return loggers.containsKey(c.getName());
	}
	
	private static Logger createNewLogger(Class c) {
		Logger logger = Logger.getLogger(c); 
		LoggerInitializer.loggers.put(c.getName(), logger);
		logger.setLevel(currentLogLevel);
		addConsoleLogging(logger);
		addFileLogging(logger);
		return logger;
	}
	
	private static void addConsoleLogging(Logger logger) {
		SimpleLayout sl = new SimpleLayout();
		ConsoleAppender consoleAppender = new ConsoleAppender(sl);
		logger.addAppender(consoleAppender);
	}
	
	private static void addFileLogging(Logger logger) {
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
	
	public static void changeLogLevel(String level) {
		for (Map.Entry<String, Logger> logger : loggers.entrySet()) {
		   logger.getValue().setLevel(Level.toLevel(level));
		}
		currentLogLevel = Level.toLevel(level);
	}
}
