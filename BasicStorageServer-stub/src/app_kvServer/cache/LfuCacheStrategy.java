package app_kvServer.cache;

import java.util.HashMap;
import java.util.PriorityQueue;

public class LfuCacheStrategy implements CacheStrategy {

	private PriorityQueue<LfuQueueNode> queue;
	private HashMap<String, String> kvPairs;
	
	public LfuCacheStrategy() {
		queue = new PriorityQueue<LfuQueueNode>(new LfuQueueNodeComparator());
	}

	@Override
	public boolean addElement(String key, String value) {
		
	}
	
	public void updateElement() {
		
	}

	@Override
	public String getValueFor(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	private void deleteElement() {

	}

}
