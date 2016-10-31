package common.messages;

public class KVMessageItem implements KVMessage{

	private String key;
	private String value;
	private StatusType status;

	public KVMessageItem(StatusType type, String key, String value){
		this.key = key;
		this.value = value;
		this.status = type;
	}

	public KVMessageItem(StatusType type, String keyOrValue){
		this.status = type;
		if (type.equals(KVMessage.StatusType.GET)) {
			this.key = keyOrValue;
		} else if (type.equals(KVMessage.StatusType.GET_SUCCESS) ||
				type.equals(KVMessage.StatusType.PUT_UPDATE)) {
			this.value = keyOrValue;
		}				
	}

	public KVMessageItem(StatusType type){
		this.status = type;
	}

	public String getKey() {
		return key;	
	}

	public String getValue() {
		return value;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType type) {
		this.status = type;
	}

	public void setValue(String value){
		this.value = value;
	}

	public void setKey(String key){
		this.key = key;
	}
}
