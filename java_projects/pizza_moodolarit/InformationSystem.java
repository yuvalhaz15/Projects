import java.util.Vector;
public class InformationSystem{
private Vector <Order> ordersVector1; 
private Vector <Order> ordersVector2; 
private Vector <Order> ordersVector3; 
private Order order;
private boolean dayIsOver;
private double totalPizzasPrice;



public InformationSystem() {
	ordersVector1 = new Vector<Order>();
	ordersVector2 = new Vector<Order>();
	ordersVector3 = new Vector<Order>();
	dayIsOver=false;
	this.totalPizzasPrice=0;
}

public synchronized void insertOrder(Order order) {
	if (order.distance<=3) 
		ordersVector1.add(order);	
	if (order.distance>3 && order.distance<=8  ) 
		ordersVector2.add(order);
	if (order.distance>8)
		ordersVector3.add(order);
	this.notifyAll();
	this.totalPizzasPrice+=order.getPriceAfterDiscount();
}

	public synchronized Order getOrder(KitchenWorker kitchenWorker) {	
		try{
			while (noOrders()&&!(dayIsOver))
				this.wait();
		}
		catch( InterruptedException ie) {
		}
		if (!(ordersVector1.isEmpty())) {	
			order=ordersVector1.get(0);
			ordersVector1.remove(0);
			return order;
		}
		else if (!(ordersVector2.isEmpty())) {
			order=ordersVector2.get(0);
			ordersVector2.remove(0);
			return order;
		}
		else if (!(ordersVector3.isEmpty())) {
			order=ordersVector3.get(0);
			ordersVector3.remove(0);
			return order;
		}
		return null;
	}

	public synchronized void printOrderDetails(Order order, KitchenWorker kitchenWorker) {
		System.out.println();
		//System.out.println("order ID: " + order.getID());
		System.out.println("kitchen worker: " + kitchenWorker.getEmployeeName());
		System.out.println("Worker's salary: " + kitchenWorker.getSalary());
		System.out.println("Number of pizzas: " + order.getCall().getNumOfPizzas());
		System.out.println("Total Price " + order.getPriceAfterDiscount());
		System.out.println("Distance: " + order.distance);
		System.out.println("Address: " + order.getCall().getAddress());
		System.out.println("Credit card number: " + order.getCall().getCreditCardNumber());	
		System.out.println();
	}

	public boolean noOrders() {
		if (ordersVector1.isEmpty() && ordersVector2.isEmpty() && ordersVector3.isEmpty())
			return true;
		return false;
	}

	public  synchronized void wakeUp() {
		this.dayIsOver=true;
		this.notifyAll();	
	}

	public synchronized double getTotalPizzaPrice() {
		return this.totalPizzasPrice;
	}

}