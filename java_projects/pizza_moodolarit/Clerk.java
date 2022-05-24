public class Clerk extends Employee {
	private Queue<Call> callsQueue;
	private Queue<Order> ordersQueue;
	private Queue<Call> managerCallsQueue;
	private String name;
	private Call call;
	private Order order;


	public Clerk(String name,Queue<Call> callsQueue,Queue<Order> ordersQueue,Queue<Call> managerCallsQueue,Manager manager) {
		super(manager);
		this.name=name;
		this.callsQueue=callsQueue;
		this.ordersQueue=ordersQueue;
		this.managerCallsQueue=managerCallsQueue;
	}

	public void run() {
		while(!(this.myManager.noCalls())) 
			work();
		finishWork();
		System.out.println("ya manyak " + this.getEmployeeName());
	}

	void finishWork() {
		System.out.println(this.getEmployeeName()+" salary: " + this.salary);
		//this.myManager.tellTheManagerYourSalary(this.salary);
		this.callsQueue.queueIsOver();
	}

	void work() {
		takeCall();
		if (this.call!=null) {
			this.myManager.changeCallsCounter() ;
			calculateSalary();
			if(this.call.getNumOfPizzas()<10) {
				takeCareOfOrder();
				finishCall();
			}
			else 
				passCallToManger();
		}
		//this.myManager.checkIsDayOver();
	}



	public String getEmployeeName() {
		return name;
	}

	private synchronized void takeCall() {
		this.call=callsQueue.extract();
	}


	synchronized void calculateSalary() {
		this.salary+=2;

	}

	private Order createNewOrder() {
		order=new Order(this.ordersQueue.getPlace(),25*this.call.getNumOfPizzas(),this.call);
		return this.order;
	}

	private  void finishCall() {
	
		this.call.wakeUp();

	}

	private synchronized void takeCareOfOrder() {
		try {
			//Thread.sleep((int)(call.getCallDuration()*1000));

			Thread.sleep((int)(1));
		}
		catch(InterruptedException ie) {
		}
		ordersQueue.insert(createNewOrder());
	}

	private  synchronized void passCallToManger() {
		try {
			Thread.sleep((int)(1));
		}
		catch(InterruptedException ie) {

		}
		managerCallsQueue.insert(this.call);
	}

}