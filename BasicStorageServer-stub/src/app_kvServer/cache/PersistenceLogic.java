package app_kvServer.cache;

import app_kvServer.KVTuple;
import app_kvServer.StorageCommunicator;

public class PersistenceLogic {

	private CacheStrategy cache;
	private StorageCommunicator storageCommunicator;
	private final int MAX_CACHE_SIZE = 1000;
	
	public PersistenceLogic() {
		storageCommunicator = new StorageCommunicator();
	}
	
	public void put(String key, String value) {
		cache = new LfuCacheStrategy();
		if (cache.contains(key)) cache.updateElement(key, value); 
		else {
			putElementToCache(key, value);
		}
	}
	
	public String get(String key) {
		if (cache.contains(key)) return cache.getValueFor(key);
		else {
			KVTuple tuple = lookUpElementOnDisk(key);
			putElementToCache(tuple.getKey(), tuple.getValue());
			return tuple.getValue();
		}
	}
	
	private KVTuple lookUpElementOnDisk(String key) {
		String value = storageCommunicator.read(key);
		return new KVTuple(key, value);
	}
	
	private void putElementToCache(String key, String value) {
		if (cache.size() == MAX_CACHE_SIZE) {
			KVTuple tuple = cache.deleteElement();
			putElementToDisk(tuple);
			cache.addElement(key, value);
		}
	}
	
	private void putElementToDisk(KVTuple tuple) {
		storageCommunicator.write(tuple.getKey(), tuple.getValue());
	}
}
