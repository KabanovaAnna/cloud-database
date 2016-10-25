package communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Communicator {
	
	private Socket echoSocket;
	private static InputStream input;
    private static OutputStream output;
    
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

	
//	private void send(byte[] message) throws IOException{
//		OutputStream output = echoSocket.getOutputStream();
//		output.write(message);
//		output.flush();
//	}
//	
//	
//	private byte[] receive() throws IOException{
//		InputStream input = echoSocket.getInputStream();
//		byte[] message = new byte[input.read()];
//		input.read(message);
//		return message;
//		
//	}
	
	
	 	private void connect(String ipv4, int port) throws IOException{
	    	quit();
	    	echoSocket = new Socket(parseIP(ipv4),port);
	    	input = echoSocket.getInputStream();
	    	output = echoSocket.getOutputStream();
	    }
	    
	    private boolean isConnected() {
	    	
	    	    if (echoSocket == null){
	    	        return false;
	    	    }
	    	    else if (echoSocket.isClosed()){
	    	    	return false;
	    	    }
	    	    else if (echoSocket.isConnected()){
	    	        return true;
	    	    }
	    	    else {
	    	        try {
	    	            echoSocket.close();
	    	        } catch (IOException ignored) {
	    	        }
	    	        echoSocket = null;
	    	        return false;
	    	    
	    	}  
	    }
	    
	    
	    public void quit() throws IOException {
	        if (echoSocket != null) {
	            echoSocket.close();
	        }
	        if (input != null) {
	            input.close();
	        }
	        if (output != null) {
	            output.close();
	        }
	    }
	    
	    
		public void send(byte[] message) throws IOException{
			if (!isConnected()) {
	            throw new IOException("Not connected");
	        }
			output.write(message);
			output.flush();
			
		}
		
		//fail .. TODO
		
		public byte[] receive() throws IOException{
			if (!isConnected()) {
	            throw new IOException("Connection failed.");
	        }
			byte [] tmp = new byte[16384];		
			int size = input.read(tmp);
			byte [] message = new byte [size];
			input.read(message);
			return message;
			
		}

}
