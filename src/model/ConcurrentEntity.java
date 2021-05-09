package model;

public interface ConcurrentEntity {

	void lock() throws Exception;
	
	void unlock();
	
	boolean isLocked();
	
}
