package app_kvServer.cache;

import app_kvServer.KVTuple;

public class PersistenceLogic {

	private CacheStrategy cache;
	
	public void put(String key, String value) {
		cache = new LfuCacheStrategy();
		if (cache.contains(key)) update(key, value); 
		else {
			putElementToCache(key, value);
		}
	}
	
	public KVTuple get() {
		if (cache.contains(key)) return cache.getValueFor(key);
		else {
			KVTuple tuple = lookUpElementOnDisk(key);
			putElementToCache(tuple.getKey(), tuple.getValue());
			return tuple;
		}
		return null;
	}
	
	private void putElementToCache(String key, String value) {
		if (cache.size() == MAX_CACHE_SIZE) {
			KVTuple tuple = cache.deleteElement();
			putElementToDisk(KVTuple tuple);
			cache.addElement(key, value);
		}
	}
	
	private void putElementToDisk(KVTuple tuple) {
		//TODO put or update element on disk
	}
}
