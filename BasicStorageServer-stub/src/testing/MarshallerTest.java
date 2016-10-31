package testing;

import org.junit.Test;

import common.messages.KVMessage;
import common.messages.Marshaller;
import common.messages.KVMessage.StatusType;
import common.messages.KVMessageItem;
import junit.framework.TestCase;

public class MarshallerTest extends TestCase {

	@Test
	public void shouldMarshalAndUnmarshalGetMessage() {
		KVMessage message = new KVMessageItem(StatusType.GET, "12345");
		byte[] marshaledMessage = Marshaller.marshal(message);
		KVMessage unmarshaledMessage = Marshaller.unmarshal(marshaledMessage);
		assertTrue(unmarshaledMessage.getStatus().equals(StatusType.GET));
		assertTrue(unmarshaledMessage.getKey().equals("12345"));
	}
}
