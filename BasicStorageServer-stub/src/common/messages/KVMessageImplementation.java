package common.messages;

public class KVMessageImplementation implements KVMessage{
	
	private KVMessageImplementation message;
	private String key;
	private String value;
	private StatusType type;

	
	public KVMessageImplementation(StatusType type, String key, String value){
		this.key = key;
		this.value = value;
		this.type = type;
	}
	
	
	public KVMessageImplementation(StatusType type, String keyOrValue){
		this.type = type;
		if(type.equals(KVMessage.StatusType.GET)){
			this.key = keyOrValue;
		}else if(type.equals(KVMessage.StatusType.GET_SUCCESS) ||
				type.equals(KVMessage.StatusType.PUT_UPDATE)){
			this.value = keyOrValue;
		}				
	}

	
	public KVMessageImplementation(StatusType type){
		this.type = type;
	}
	
	
	@Override
	public String getKey() {
		if(message.value == null){
			return null;
		}else{
			return message.key;
		}		
	}

	@Override
	public String getValue() {
		if(message.key == null){
			return null;
		}else{
			return message.value;
		}
	}

	@Override
	public StatusType getStatus() {
		return message.type;
	}
	
	

	public void setStatusType(StatusType type) {
		this.type = type;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public void setKey(String key){
		this.key = key;
	}
}
