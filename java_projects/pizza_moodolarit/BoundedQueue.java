import java.util.Vector;

public class BoundedQueue <T> {
	private Vector<T> bQueue;
	private boolean queueIsOver;
	
	public BoundedQueue() {
		bQueue = new Vector<T>();
		this.queueIsOver=false;
	}
	public synchronized void insert( T item) {
		try{
			while (bQueue.size()>=12)
				this.wait();
		}
		catch( InterruptedException ie) {
		}
		bQueue.add(item);
		notifyAll();  
	}

	public synchronized T extract()  
	{
		try {	
			while (bQueue.isEmpty() && !this.queueIsOver)
			wait();
		}
		catch(InterruptedException ie) {
	}	
		if (this.bQueue.size()>0) {
			this.notifyAll();
			T item = bQueue.elementAt(0);
			bQueue.remove(item);
			return item;
		}
		return null;
	}

	public Vector<T> getbQueue() {
		return bQueue;
	}
	public synchronized void queueIsOver() {
		this.queueIsOver=true;
		this.notifyAll();	
	}
}