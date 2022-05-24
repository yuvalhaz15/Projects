public class KitchenWorker extends Employee {
	private String name;
	private Order order;
	private InformationSystem infoSystem;
	private BoundedQueue <PizzaDelivery> pizzasDeliveryQueue;
	private double cookingTime;


	public KitchenWorker(String name,double cookingTime, BoundedQueue <PizzaDelivery> pizzasDeliveryQueue, InformationSystem infoSystem,Manager manager) {
		super(manager);
		this.cookingTime=cookingTime;
		this.name=name;
		this.infoSystem=infoSystem;
		this.pizzasDeliveryQueue=pizzasDeliveryQueue;
	}

	public String getEmployeeName() {
		return name;
	}

	public  synchronized double getSalary() {
		return this.salary;
	}

	public Order getOrder() {
		return order;
	}


	@Override
	public void run() {
		while (!myManager.finishWork) 
			work();
		finishWork();
		System.out.println("ya manyak" + this.getEmployeeName());
	}

	synchronized void work() {
		order = infoSystem.getOrder(this);
		if (order!=null) {
			makeTheOrder();
			calculateSalary();
			this.infoSystem.printOrderDetails(order, this);
			sendPizzas();		
		}
	}

	private void sendPizzas() {
		pizzasDeliveryQueue.insert(new PizzaDelivery(order));

	}

	public  synchronized void calculateSalary() {
		this.salary+=order.getCall().getNumOfPizzas()*2;	

	}

	private void makeTheOrder() {
		try {
			Thread.sleep((int)(this.cookingTime*this.order.getCall().getNumOfPizzas()*1000));
		}
		catch(InterruptedException ie) {
		}
	}

	@Override
	void finishWork() {
		System.out.println(this.getEmployeeName()+" salary: " + this.salary);
		//this.myManager.tellTheManagerYourSalary(this.salary);

	}

}