package app_kvServer;

import common.messages.KVMessage;
import common.messages.KVMessage.StatusType;
import common.messages.KVMessageItem;
import app_kvServer.cache.CacheStrategy;
import app_kvServer.cache.LfuCacheStrategy;

public class PersistenceLogic {

	private static CacheStrategy cache;
	private static StorageCommunicator storageCommunicator  = new StorageCommunicator();
	private static final int MAX_CACHE_SIZE = 1000;
	
	public static StatusType put(String key, String value) {
		cache = new LfuCacheStrategy();
		if (cache.contains(key)) {
			cache.updateElement(key, value); 
			return StatusType.PUT_UPDATE;
		}
		else {
			putElementToCache(key, value);
			return StatusType.PUT_SUCCESS;
		}
	}
	
	public static KVMessage get(String key) {
		if (cache.contains(key)) {
			return new KVMessageItem(StatusType.GET_SUCCESS, cache.getValueFor(key));
		} else {
			KVTuple tuple = lookUpElementOnDisk(key);
			if (tuple.getValue() != null) {
				putElementToCache(tuple.getKey(), tuple.getValue());
				return new KVMessageItem(StatusType.GET_SUCCESS, tuple.getKey());
			} else {
				return new KVMessageItem(StatusType.GET_ERROR);
			}

		} 
	}
	
	private static KVTuple lookUpElementOnDisk(String key) {
		String value = storageCommunicator.read(key);
		return new KVTuple(key, value);
	}
	
	private static void putElementToCache(String key, String value) {
		if (cache.size() == MAX_CACHE_SIZE) {
			KVTuple tuple = cache.deleteElement();
			putElementToDisk(tuple);
			cache.addElement(key, value);
		}
	}
	
	private static void putElementToDisk(KVTuple tuple) {
		storageCommunicator.write(tuple.getKey(), tuple.getValue());
	}
}