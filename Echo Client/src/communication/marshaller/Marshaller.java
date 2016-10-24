package communication.marshaller;

public interface Marshaller {

	public byte[] marshal(String message);
	
	public String unmarshal(byte[] message);
	
}
