package communication.marshaller;

public interface Marshaller<T> {

	public byte[] marshal(T message);
	
	public T unmarshal(byte[] message);
	
}
