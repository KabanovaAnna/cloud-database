package common.messages;

import java.nio.charset.Charset;

import common.messages.KVMessage.StatusType;

public class Marshaller {
	
	private KVMessageImplementation result;
	private static final char UNIT_SEPARATOR = (char) 31;	
	private static final char CARRIAGE = (char) 13;
	private final String CHARSET = "US-ASCII";
	
	

	public byte[] marshal(KVMessage message) {
		String result = "";
		String value = "";
		String key = "";		
		String type = new String(message.getStatus().name());
		
		result += type;
		
		if(message.getKey() != null){
			key = message.getKey();
			result = result + UNIT_SEPARATOR + key;
		}
		if(message.getValue() != null){
			value = message.getValue();
			result = result + UNIT_SEPARATOR + value;
		}
		result += CARRIAGE;
		return result.getBytes();
	};
	
	
	
	/**
	 * 2 is subtracted from unmarshaled message length, because last characters in a message from
	 * server are new line and carriage return.
	 */
	
	private String stringUnmarshaller(byte[] msg){
		String unmarshaledMessage = new String(msg, Charset.forName(CHARSET));
		return unmarshaledMessage.substring(0, msg.length - 2);
	}
	
	private byte [] partArrayCoppy(byte[] message, int from){
		byte [] result = new byte[message.length];
		
		while(message[from] != UNIT_SEPARATOR || message[from] != CARRIAGE){
			result[from] = message[from];			
			from++;
		}		
		return result;
	}
	
	public KVMessage unmarshal(byte[] message) {		
	
		byte[] t = partArrayCoppy(message,0);
		StatusType type = StatusType.valueOf(stringUnmarshaller(t));
		result.setStatusType(type);
		
		if(message.length == t.length + 1){			
			return result;			
		}else{
			
			byte[] elem = partArrayCoppy(message, t.length + 1);
			
			if(type.equals(KVMessage.StatusType.GET_SUCCESS) || type.equals(KVMessage.StatusType.PUT_UPDATE)){
				String value = stringUnmarshaller(elem);
				result.setValue(value);
				return result;
				
			}else if(type.equals(KVMessage.StatusType.GET)){
				String key = stringUnmarshaller(elem);
				result.setKey(key);
				return result;
		
			}else if(type.equals(KVMessage.StatusType.PUT)){
				String key = stringUnmarshaller(elem);
				byte[] elem2 = partArrayCoppy(message, t.length + elem.length + 2);
				String value = stringUnmarshaller(elem2);
				result.setKey(key);
				result.setValue(value);
				return result;
			}
		}
		return result;
			
	};
	
}
