package app_kvServer.cache;

public interface CacheStrategy {
	
	int MAX_CACHE_SIZE = 100;
	
	public void addElement(String key, String value);
	
	public String getValueFor(String key);

}
