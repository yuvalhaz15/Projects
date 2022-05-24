import java.util.Vector;

public class Queue<T> {

	public Vector<T> queue; 
	private int place;
	private boolean queueIsOver;
	private boolean managerWakeUp;

	public Queue() {
		queue = new Vector<T>();
		queueIsOver=false;
		this.managerWakeUp=false;
		this.place=1;
	}
	public synchronized void queueIsOver() {
		this.queueIsOver=true;
		this.notifyAll();
	}

	public synchronized void insert(T item) {
		this.queue.add(item);
		this.notifyAll();		
	}

	public Vector <T> getQueue() {
		return queue;
	}

	public synchronized T extract() {
		try{
				while (queue.isEmpty() && !queueIsOver && !managerWakeUp)
					this.wait();
		}
		catch( InterruptedException ie) {
		}
		this.managerWakeUp=false;
		if (this.queue.size()>0) {
			T t = queue.elementAt(0);
			queue.remove(t);
			return t;
		}
		return null;
	}

	public synchronized void managerWakeUp() {
		this.managerWakeUp=true;
		this.notifyAll();
	}
	public synchronized  int getPlace() {
		this.place++;
		return this.place-1;
	}

}