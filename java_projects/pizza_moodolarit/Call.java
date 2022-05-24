public class Call extends Thread {
	private int numOfPizzas;
	private String address;	
	private String creditCardNumber;	
	private int waitForQueue;
	private double callDuration;
	private boolean isOn;
	private Queue<Call> callsQueue;

	public Call (String creditCardNumber,String numOfPizzas,String waitForQueue,String callDuration,String address,Queue<Call> callsQueue ) {
		this.numOfPizzas=Integer.parseInt(numOfPizzas);
		this.address=address;		
		this.creditCardNumber=creditCardNumber;		
		//this.waitForQueue=Integer.parseInt(waitForQueue);
		//this.callDuration=Double.parseDouble(callDuration);
		this.callDuration=1;
		this.waitForQueue=1;
		this.callsQueue=callsQueue;
		this.isOn=true;
	}
	public int getNumOfPizzas() {
		return numOfPizzas;
	}
	public  synchronized String getAddress() {
		return address;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public int getWaitForQueue() {
		return waitForQueue;
	}
	public double getCallDuration() {
		return callDuration;
	}

	@Override
	public synchronized void run() {
		while (this.isOn) {
			waitForQueue();
			this.callsQueue.insert(this);
			//System.out.println(this.address);
			waitUntilFinished();
		}
	}

		private void waitForQueue() {
			try {
				//Thread.sleep(this.waitForQueue*1000);
				Thread.sleep(1);
			}
			catch( InterruptedException ie) {
				ie.printStackTrace();
			}	
		}

		private synchronized void waitUntilFinished() {
			try {
					this.wait();
			}
			catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		public synchronized void wakeUp() {
			isOn=false;
			this.notifyAll();
		}

	}