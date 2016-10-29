package app_kvServer.cache;

import app_kvServer.KVTuple;

public class LfuQueueNode {
	
	private KVTuple tuple;
	private int freqeuncy;
	
	public LfuQueueNode(KVTuple tuple) {
		this.tuple = tuple;
	}

	public KVTuple getTuple() {
		return tuple;
	}

	public void setTuple(KVTuple tuple) {
		this.tuple = tuple;
	}

	public int getFreqeuncy() {
		return freqeuncy;
	}

	public void setFreqeuncy(int freqeuncy) {
		this.freqeuncy = freqeuncy;
	}
}
