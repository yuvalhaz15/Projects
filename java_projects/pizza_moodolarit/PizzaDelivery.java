
public class PizzaDelivery {

	private Order order;

	public PizzaDelivery(Order order) {
		this.order=order;
	}

	public synchronized Order getOrder() {
		return order;
	}


}
