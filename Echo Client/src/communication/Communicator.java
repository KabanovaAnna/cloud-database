package communication;

import org.apache.log4j.Logger;

import ui.LoggerInitializer;

public class Communicator {

	private static final int MAX_MESSAGE_SIZE = 128000;

	private Logger logger;
	private NetworkCommunicator communicator;
	private String address;
	private int port;

	public boolean establishConnection(String address, String port) {
		initializeLogger();
		this.address = address;
		this.port = parsePort(port);
		if (this.port > 0) {
			return connectToServerWithAddressAndPort(address, this.port);
		} else
			return false;
	}

	private void initializeLogger() {
		logger = LoggerInitializer.initialize(Communicator.class);
	}

	private int parsePort(String port) {
		int parsedPort = -1;
		try {
			parsedPort = Integer.valueOf(port);
		} catch (NumberFormatException e) {
			logger.warn("Port could not be parsed, it is not a number");
		}
		return parsedPort;
	}

	private boolean connectToServerWithAddressAndPort(String address, int port) {
		try {
			communicator = new NetworkCommunicator(address, port);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	public String receiveMessage() {
		String message;
		if (communicator != null) {
			StringMarshaller stringMarshaller = new StringMarshaller();
			try {
				message = stringMarshaller.unmarshal(communicator.receive());
				logger.info("Unmarshaled message: " + message);
			} catch (Exception e) {
				message = "Error!" + e.getMessage();
				logger.error(message);
			}
		} else {
			message = "Error! Message can not be sent because no connection to server was established";
			logger.error(message);
		}
		return message;
	}

	public boolean sendMessage(String message) {
		logger.info("Message to send: " + message);
		if (communicator != null) {
			if (isMessageSizeValid(message)) {
				return marshalAndSendMessage(message);
			}
		}
		return false;
	}

	private boolean isMessageSizeValid(String message) {
		return message.getBytes().length <= MAX_MESSAGE_SIZE;
	}

	private boolean marshalAndSendMessage(String message) {
		StringMarshaller stringMarshaller = new StringMarshaller();
		try {
			communicator.send(stringMarshaller.marshal(message));
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	public boolean disconnect() {
		if (communicator != null) {
			try {
				communicator.disconnectFromServer();
				this.communicator = null;
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return false;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
}
