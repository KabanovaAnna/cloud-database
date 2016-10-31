package client;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import common.messages.*;
import common.messages.KVMessage.StatusType;

public class KVStore implements KVCommInterface {

	private String address;
	private int port;
	
	private CommunicationModule communicationModule;
	private Logger logger;

	public KVStore(String address, int port) {
		logger = Logger.getRootLogger();
		this.address = address;
		this.port = port;
	}
	
	/**
	 * Initialize KVStore with address and port of KVServer
	 * @param address the address of the KVServer
	 * @param port the port of the KVServer
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */

	public void connect() throws IllegalArgumentException, IOException {
		communicationModule = new CommunicationModule(address, port);
	}

	public void disconnect() {
		try {
			communicationModule.disconnectFromServer();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public KVMessage put(String key, String value) {
		KVMessage kvMessage = new KVMessageItem(StatusType.PUT, key, value);
		return sendMessage(kvMessage, StatusType.PUT_ERROR);
	}

	public KVMessage get(String key) {
		KVMessage kvMessage = new KVMessageItem(StatusType.GET, key);
		return sendMessage(kvMessage, StatusType.GET_ERROR);
	}

	private KVMessage sendMessage(KVMessage kvMessage, StatusType errorType) {
		Marshaller marshaller = new Marshaller();
		try {
			communicationModule.send(marshaller.marshal(kvMessage));
			byte[] recievedMessage = communicationModule.receive();
			return marshaller.unmarshal(recievedMessage);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return new KVMessageItem(errorType);
	}
}
