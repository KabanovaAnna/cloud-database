package client;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

import app_kvServer.ClientConnection;
import common.messages.KVMessage;

public class KVStore implements KVCommInterface {
	
	private Socket socket;
	private ClientConnection connection;
	private static final int CONNECTION_TIMEOUT = 2000;
	
	
	/**
	 * Initialize KVStore with address and port of KVServer
	 * @param address the address of the KVServer
	 * @param port the port of the KVServer
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public KVStore(String address, int port) throws UnknownHostException, IOException {
		socket = new Socket(address, port);
		this.connection = new ClientConnection(socket);
	}
	
	@Override
	public void connect() throws Exception {
		try{
			
			connection.run();
			while (isRunning()){
				
					//TODO: handle connection in separate Thread
				
			}
		}catch (IOException e){
			System.out.println("An error occurred while connecting.");
		}finally {
			server.close();
		}
				
	}

	private boolean isRunning(){
		//TODO: check if still running
		
		return false;
	}
	
	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public KVMessage put(String key, String value) throws Exception {

		KVStore kv = new KVStore(address, port);
		
		return null;
	}

	@Override
	public KVMessage get(String key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
