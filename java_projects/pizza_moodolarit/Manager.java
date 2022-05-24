import java.util.Vector;

public class Manager extends Employee {

	private Queue<Call> callsQueue;
	private Queue <Order> ordersQueue;
	private Queue<Call> managerCallsQueue;
	private BoundedQueue <PizzaDelivery> pizzaDeliveryQueue;
	private InformationSystem infoSystem;
	private String name;
	private Call call;
	private Order order;
	private int totalCallsForToday;
	public int totalCallsCounter;
	public int totalOrdersDeliverd;
	public boolean finishWork;
	private double totalWorkersSalary;
	private Vector<Employee> employeesVector;

	public Queue<Call> getCallsQueue() {
		return callsQueue;
	}

	public Queue<Order> getOrdersQueue() {
		return ordersQueue;
	}

	public Queue<Call> getManagerCallsQueue() {
		return managerCallsQueue;
	}

	public BoundedQueue<PizzaDelivery> getPizzaDeliveryQueue() {
		return pizzaDeliveryQueue;
	}

	public InformationSystem getInfoSystem() {
		return infoSystem;
	}

	public Call getCall() {
		return call;
	}

	public Order getOrder() {
		return order;
	}

	public int getTotalCallsForToday() {
		return totalCallsForToday;
	}

	public int getTotalCallsCounter() {
		return totalCallsCounter;
	}

	public int getTotalOrdersDeliverd() {
		return totalOrdersDeliverd;
	}

	public boolean isFinishWork() {
		return finishWork;
	}


	Manager(Vector<Employee> employeesVector, String name, Queue <Call>callsQueue, Queue <Order> ordersQueue,Queue <Call> managerCallsQueue, BoundedQueue <PizzaDelivery> pizzaDeliveryQueue,InformationSystem infoSystem,Manager manager,int numberOfCalls) {
		super(manager);
		this.name=name;
		this.callsQueue=callsQueue;
		this.infoSystem=infoSystem;
		this.managerCallsQueue=managerCallsQueue;
		this.ordersQueue=ordersQueue;
		this.pizzaDeliveryQueue=pizzaDeliveryQueue;
		this.totalCallsForToday=numberOfCalls;
		this.totalCallsCounter=numberOfCalls;
		this.totalOrdersDeliverd=0;
		this.finishWork=false;
		this.totalWorkersSalary=0;
		this.employeesVector=employeesVector;
	}

	public boolean checkLessThenTenOrders() {
		return(this.totalCallsForToday-this.totalOrdersDeliverd<=10);
	}

	public void calculateSalary() {
		this.salary=0;

	}
	public void run() {
		while(!endOfTheDay()) 
			work();
		finishWork();
		System.out.println("ya manyak" + this.getEmployeeName());
	}

	private boolean endOfTheDay() {
		return (this.totalCallsForToday==this.totalOrdersDeliverd);
	}

	void finishWork() {
		
		announceDayIsOver();
		printDetails();
	}
public   void changeCallsCounter() {
	this.totalCallsCounter--;
	
}
	private void announceDayIsOver() {
		finishWork=true;
		this.infoSystem.wakeUp();
		this.ordersQueue.queueIsOver();
		this.pizzaDeliveryQueue.queueIsOver();

	}

	private void printDetails() {
		System.out.println("Total orders deliverd :"+" "+this.totalOrdersDeliverd);
		System.out.println("Total workers salary :"+" "+calculateTotalSalary());
		System.out.println("Total income for today : "+this.infoSystem.getTotalPizzaPrice());
	}

	private double calculateTotalSalary() {
		for(int s=0;s<this.employeesVector.size();s++) {
			this.totalWorkersSalary+=this.employeesVector.get(s).getSalary();
		}
		return 	this.totalWorkersSalary;
	}


	synchronized void work() {
		takeCall();
		if (call!=null) {	
			takeCareOfOrder();
			finishCall();
		}
	}

	public String getEmployeeName() {
		return name;
	}


	private void takeCall() {
		this.call=managerCallsQueue.extract();

	}


	private double calculatePriceOfOrder() {
		if (this.call!=null) {
			if(this.call.getNumOfPizzas()<20)	
				return this.call.getNumOfPizzas()*15;
			return (this.call.getNumOfPizzas()*15)*0.9;
		}
		return 0;
	}
	private Order createNewOrder() {
		if (this.call!=null) {
			this.order=new Order(this.ordersQueue.getPlace(),calculatePriceOfOrder(),this.call);
			calculateDistance();
		}return this.order;
	}

	private synchronized void calculateDistance() {
		for (int i=0; i<order.getCall().getAddress().length();i++) {
			if (order.getCall().getAddress().charAt(i)==' ') 
				order.distance+=1;
		}
		if (order.getCall().getAddress().charAt(0)>='A' && order.getCall().getAddress().charAt(0)<='H')
			order.distance+=0.5;
		if (order.getCall().getAddress().charAt(0)>='I' && order.getCall().getAddress().charAt(0)<='P')
			order.distance+=2;
		if (order.getCall().getAddress().charAt(0)>='Q' && order.getCall().getAddress().charAt(0)<='Z')
			order.distance+=7;
		if (order.getCall().getAddress().charAt(0)>='0' && order.getCall().getAddress().charAt(0)<='9')
			order.distance+=(int)(order.getCall().getAddress().charAt(0));
	}

	private void finishCall() {
		call.wakeUp();

	}

	private void takeCareOfOrder() {

		try {
			//Thread.sleep(2000);
			Thread.sleep(1);
		}
		catch(InterruptedException ie) {
		}
		infoSystem.insertOrder(createNewOrder());
		System.out.println("New Order Arrived, ID: "+order.getID() );
	}

	public void checkIsDayOver() {
		this.managerCallsQueue.managerWakeUp();
		if(endOfTheDay())	
			this.managerCallsQueue.queueIsOver();
	}

	public boolean noCalls() {
System.out.println("manager counter calls  "+this.totalCallsCounter);
		return this.totalCallsCounter==0;
	}

}