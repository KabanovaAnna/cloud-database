package app_kvServer.cache;

import java.util.Iterator;
import java.util.LinkedList;

import app_kvServer.KVTuple;

public class FifoCacheStrategy implements CacheStrategy {

	private LinkedList<KVTuple> queue;
	
	public FifoCacheStrategy() {
		queue = new LinkedList<KVTuple>(); 
	}
	
	@Override
	public void addElement(String key, String value) {
		if (queue.size() == MAX_CACHE_SIZE) {
			deleteElement();
		}
		queue.add(new KVTuple(key, value));
	}
	
	private KVTuple deleteElement() {
		return queue.pollFirst();
	}

	@Override
	public String getValueFor(String key) {
		Iterator<KVTuple> iterator = queue.iterator();
		while (iterator.hasNext()) {
			KVTuple element = iterator.next();
			if (key.equals(element.getKey())) return element.getValue();
		}
		return null;
	}
	
}