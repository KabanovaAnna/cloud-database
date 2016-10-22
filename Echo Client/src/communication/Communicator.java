package communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Communicator {
	
	private Socket echoSocket;
	
	Communicator (String address, int port) throws IOException{
		echoSocket = new Socket(parseIP(address), port);
	}
	
	/**
	 * This function parses the ip address from a string into a network 
	 * InetAddress representation
	 *
	 * @param adr The ip address as string
	 *
	 * @return An InetAddress with the ip address, null on error
	 * @throws UnknownHostException 
	 */
	private InetAddress parseIP(String adr){		
		InetAddress address = null;
		try {
			address = InetAddress.getByName(adr);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}		 
		return address;	
	}
	
	
	
	
	private void send(byte[] message) throws IOException{
		 OutputStream output = echoSocket.getOutputStream();
   
		    output.write(message);
		    output.flush();
	}

	
	
	
	private int receive() throws IOException{
		
		InputStream input = echoSocket.getInputStream();
	    
		return input.read();
	    
	   
	}
	

}
