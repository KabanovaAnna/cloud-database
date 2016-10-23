package communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Communicator {
	
	private Socket echoSocket;
	public InputStream inputStream;
	private OutputStream outputStream;
	
	public Communicator (String address, int port) throws IOException{
		echoSocket = new Socket(address, port);
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
}
