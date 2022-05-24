
public class Scheduler extends Employee{
	private Queue<Order> ordersQueue;
	private String name;
	private Order order;
	private InformationSystem infoSystem;
	private boolean finishWork;


	public Scheduler (String name, Queue<Order> ordersQueue,InformationSystem infoSystem,Manager manager) {
		super(manager);
		this.name=name;
		this.ordersQueue=ordersQueue;
		this.infoSystem=infoSystem;
		this.finishWork=false;
	}

	@Override
	public void run() {
		while (!(this.myManager.finishWork)) 
			work();	
		finishWork();
		System.out.println("ya manyak" + this.getEmployeeName());
	}

	public void finishWork() {
		System.out.println(this.getEmployeeName()+" salary: " + this.salary);
		//this.myManager.tellTheManagerYourSalary(this.salary);
		
	}

	synchronized void work() {
		order = ordersQueue.extract();
		if(order!=null) {
			calculateDistance();
			calculateSalary();

			try {
				//Thread.sleep((long) (order.distance*250));
				Thread.sleep( 1000);
			}
			catch(InterruptedException ie) {
			}
			infoSystem.insertOrder(order);
			System.out.println("New Order Arrived, ID: "+order.getID());
		}
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
			order.distance+=(int)(order.getCall().getAddress().charAt(0)-'0');
	}

	public Queue<Order> getQo() {
		return ordersQueue;
	}

	public   synchronized String getEmployeeName() {
		return name;
	}

	public   synchronized double getSalary() {
		return this.salary;
	}

	public  synchronized Order getOrder() {
		return order;
	}

	void calculateSalary() {
		this.salary+=order.distance;
	
	}

}
