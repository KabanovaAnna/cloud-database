package communication;

import java.nio.charset.Charset;

public class StringMarshaller implements Marshaller {
	
	private final char CARRIAGE = (char) 13;
	private final String CHARSET = "US-ASCII";

	public byte[] marshal(String message) {
		String messageWithCarriage = message + CARRIAGE;
		return messageWithCarriage.getBytes();
	};
	
	/**
	 * 2 is subtracted from unmarshaled message length, because last characters in a message from
	 * server are new line and carriage return.
	 */
	public String unmarshal(byte[] message) {
		String unmarshaledMessage = new String(message, Charset.forName(CHARSET));
		return unmarshaledMessage.substring(0, message.length - 2);
	};
	
}
