package communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Communicator {
	
	private Socket echoSocket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String address;
	private int port;
	
	private final int CONNECTION_TIMEOUT = 2000;
	
	public Communicator (String address, int port) throws IOException{
		this.address = address;
		this.port = port;
		echoSocket = new Socket();
		echoSocket.connect(new InetSocketAddress(address, port), CONNECTION_TIMEOUT);
		inputStream = echoSocket.getInputStream();	
		outputStream = echoSocket.getOutputStream();
	}
	
	public void send(byte[] message) throws IOException {
		outputStream.write(message);
		outputStream.flush();
	}
	
	public byte[] receive() throws IOException {
		List<Byte> readMessage = new ArrayList<>();
		int readByte = inputStream.read();
		readMessage.add((byte)readByte);
		while (inputStream.available() > 0) {
			readMessage.add((byte) inputStream.read());
		}
		return convertToByteArray(readMessage);
	}
	
	private byte[] convertToByteArray(List<Byte> list) {
		byte[] convertedArray = new byte[list.size()];
		Iterator<Byte> iterator = list.iterator();
		for (int i = 0; i < list.size(); i++) {
			convertedArray[i] = iterator.next();
		}
		return convertedArray;
	}
	
	public void disconnectFromServer() throws IOException {
		inputStream.close();
		outputStream.close();
		echoSocket.close();
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
