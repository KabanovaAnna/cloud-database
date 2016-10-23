package ui;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import org.apache.log4j.Logger;

import communication.Communicator;

public class Application {

	public static void main(String[] args) throws Exception {
		initializeLogger();
		/*
	    Communicator com = new Communicator("131.159.52.2", 50000);
		System.out.println(Arrays.toString(com.receive()));
		com.send(new byte[]{'h','e','l','l','o'});
		System.out.println(Arrays.toString(com.receive()));
		*/
		CommandLineProcessor.readInput();
		
	}
	
	private static void initializeLogger() {
		LoggerInitializer li = new LoggerInitializer(Application.class);
		Logger logger = li.initialize("all");
		logger.info("Logger initialized");
	}
}
