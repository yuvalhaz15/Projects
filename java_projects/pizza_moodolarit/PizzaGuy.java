import java.util.Vector;

public class PizzaGuy extends Employee {
	private BoundedQueue<PizzaDelivery> pizzaDeliveryQueue;
	private String name;
	private int maxOrders;
	private double randomNum;
	private boolean finishWork;
	private Vector <PizzaDelivery> pizzaDeliveryList;
	private int ordersCounter;
	private int totalTips;
	private double totalDistance;

	public PizzaGuy(String name, BoundedQueue <PizzaDelivery> pizzaDeliveryQueue,Manager manager) {
		super(manager);
		this.name=name;
		this.pizzaDeliveryQueue=pizzaDeliveryQueue;
		this.maxOrders=calculateNumberOfOrders();
		pizzaDeliveryList=new Vector <PizzaDelivery>();
		this.finishWork=false;
	}

	public String getEmployeeName() {
		return name;
	}

	public boolean isFinishWork() {
		return finishWork;
	}


	public BoundedQueue<PizzaDelivery> getPizzaDeliveryQueue() {
		return pizzaDeliveryQueue;
	}

	public int getMaxOrders() {
		return maxOrders;
	}

	public Vector<PizzaDelivery> getPizzaDeliveryList() {
		return pizzaDeliveryList;
	}

	public int getTotalTips() {
		return totalTips;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	private  synchronized int calculateNumberOfOrders() {
		randomNum=Math.random();
		if (randomNum<=0.33)
			return 2;
		if (randomNum<=0.66)
			return 3;
		return 4;
	}

	@Override
	public void run() {
		while(!(this.myManager.finishWork)) 
			work();
		finishWork();
		System.out.println("ya manyak" + this.getEmployeeName());
	}

	synchronized void work() {
		collectOrders();
		startDelivery();
	}

	public void finishWork() {
		calculateSalary();
		//this.myManager.tellTheManagerYourSalary(this.salary);
		System.out.println(this.getEmployeeName()+" salary: " + this.salary);
		
	}

	synchronized void calculateSalary() {
		this.salary+=3*numOfDeliveries()+4*distanceDrove()+tips();
		
	}

	private int numOfDeliveries() {
		return pizzaDeliveryList.size();
	}

	private int tips() {
		return totalTips;
	}

	private double distanceDrove() {
		return totalDistance;
	}

	private void startDelivery() {
		while (ordersCounter>0 && !(this.myManager.finishWork)) {
			rideToDestination();
			takeTip();
			this.myManager.totalOrdersDeliverd++;
			ordersCounter--;
		}
		//tellTheManagerNumOfOrders();
		this.myManager.checkIsDayOver();
	}

	private void takeTip() {
		try {
			Thread.sleep(1);
		}
		catch(InterruptedException ie) {
		}
		//totalTips+=(int)(Math.random()*15);
		totalTips+=5;
		
	}

	private void rideToDestination() {
		try {
			//Thread.sleep((long) (pizzaDeliveryList.get(pizzaDeliveryList.size()-ordersCounter).getOrder().distance));
		Thread.sleep(1);}
		catch(InterruptedException ie) {
		}	
		totalDistance += pizzaDeliveryList.get(pizzaDeliveryList.size()-ordersCounter).getOrder().distance;
	}

	private void collectOrders() {
		if(!this.myManager.checkLessThenTenOrders()) 	
			while (ordersCounter<maxOrders) {
				pizzaDeliveryList.add((PizzaDelivery)(pizzaDeliveryQueue.extract()));
				if (pizzaDeliveryList.get(pizzaDeliveryList.size()-1)!=null)
				ordersCounter++;
		}
		else {
			pizzaDeliveryList.add((PizzaDelivery)(pizzaDeliveryQueue.extract()));
			ordersCounter++;
		}
	}
	/*public synchronized void tellTheManagerNumOfOrders() {
		if (!this.myManager.checkLessThenTenOrders()) 
			this.myManager.totalOrdersDeliverd+=this.maxOrders;
		else {
			System.out.println("dayOver");
			this.myManager.totalOrdersDeliverd++;
			this.myManager.checkIsDayOver();
		}
	}*/
}