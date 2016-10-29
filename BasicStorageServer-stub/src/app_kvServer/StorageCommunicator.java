package app_kvServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class StorageCommunicator {

	private static final String FILE_PATH = "storage/storage.txt";
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private Logger logger;
	
	public StorageCommunicator() {
		initialize();
		logger = Logger.getRootLogger();
	}
	
	private void initialize() {
		try {
			FileWriter fileWriter = new FileWriter(FILE_PATH, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			FileReader fileReader = new FileReader(FILE_PATH);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			logger.error("File was not found: " + e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void write(String key, String value) {
		String lineToWrite = key + ":" + value;
		try {
			bufferedWriter.write(lineToWrite);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String read(String key) {
		try {
			FileReader fileReader = new FileReader(FILE_PATH);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			logger.error("File was not found: " + e.getMessage());
		}
		String[] lineTokens = new String[2];
		try {
			return findLine(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lineTokens[1];
	}
	
	private String findLine(String key) throws IOException {
		while (true) {
			String lineFromFile = bufferedReader.readLine();
			if (!(lineFromFile == null || lineFromFile.equals(""))) {
				//TODO make separator global attribute
				String[] tokens = lineFromFile.split(":");
				String keyFromFile = tokens[0];
				if (keyFromFile.equals(key)) return tokens[1];
			} else {
				return "";
			}
		}	
	}
	
	
	//TODO only for testing, delete before deployment
	public static void main(String[] args) {
		StorageCommunicator storage = new StorageCommunicator();
		storage.write("123", "Hello");
		storage.write("132", "sfsd");
		storage.write("hey", "why not");
		System.out.println(storage.read("hey"));
		storage.write("music", "w3");
		System.out.println(storage.read("123"));
		System.out.println(storage.read("123"));
		System.out.println(storage.read("123"));
		System.out.println("Stop program");
	}
	
}
