import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Restaurant {

	private Queue<Call> callsQueue;
	private Queue<Call> managerCallsQueue;
	private Queue<Order> ordersQueue;
	private BoundedQueue<PizzaDelivery> pizzaDeliverysQueue;
	private Vector <Call> callsVector;
	private Vector <Clerk> clerksVector;
	private Manager manager;
	private Vector <Scheduler> schedulersVector;
	private InformationSystem infoSystem;
	private Vector <KitchenWorker> kitchenWorkersVector;
	private Vector <PizzaGuy> pizzaGuysVector;
	public Vector <Employee> employeesVector;
	private int numOfPizzaGuys;
	private double kitchenWorkerTime;

	public Restaurant(int numOfPizzaGuys, double  kitchenWorkerTime) {//The main restaurant constructor
		employeesVector=new Vector <Employee>();
		this.numOfPizzaGuys=numOfPizzaGuys;
		this.kitchenWorkerTime=kitchenWorkerTime;
		createQueues();
		createCall(createFile("data\\Pizza.txt"));
		createInformationSystem();
		createManager();
		createClerks();
		createSchedulers();
		createKitchenWorkers();
		createPizzaGuys();
		
	}

	private void createInformationSystem() {
		this.infoSystem=new InformationSystem();		
	}

	private void createManager() {

		this.manager=new Manager(employeesVector,"manager", callsQueue, ordersQueue, managerCallsQueue, pizzaDeliverysQueue, infoSystem, null,callsVector.size());	
	}

	public void startWork() {	
		runCalls();//run call threads
		runManager();//run manager thread
		runClerks();//run clerk threads
		runPizzaGuys();//run pizza guy threads	
		runKitchenWorkers();//run kitchen worker threads
		runSchedulers();//run scheduler threads
	
	
	}

	private void runCalls() {
		for (int i=0; i<callsVector.size();i++) 
			callsVector.get(i).start();
	}

	private void runPizzaGuys() {
		for (int i=0; i<pizzaGuysVector.size();i++) {
			

			pizzaGuysVector.get(i).start();
		}
	}

	private void runKitchenWorkers() {
		for (int i=0; i<kitchenWorkersVector.size();i++) {
			

			kitchenWorkersVector.get(i).start();
		}
	}

	private void runManager() {
		this.manager.start();
	}

	private void runSchedulers() {
		for (int i=0; i<schedulersVector.size();i++) {
			

			schedulersVector.get(i).start();
		}
	}

	private void runClerks() {
		for (int i=0; i<clerksVector.size();i++) {
			
			clerksVector.get(i).start();	
		}
	}

	private void createPizzaGuys() {
		this.pizzaGuysVector = new Vector<PizzaGuy>();
		for (int i=0; i<numOfPizzaGuys;i++) {
			pizzaGuysVector.add(new PizzaGuy("pizzaGuy" + i,pizzaDeliverysQueue,manager));
		}
		for (int i=0; i<pizzaGuysVector.size();i++) 
			employeesVector.add(pizzaGuysVector.get(i));
	}

	private void createKitchenWorkers() {
		this.kitchenWorkersVector = new Vector<KitchenWorker>();
		kitchenWorkersVector.add(new KitchenWorker("kitchenWorker1", this.kitchenWorkerTime,pizzaDeliverysQueue,infoSystem,manager));
		kitchenWorkersVector.add(new KitchenWorker("kitchenWorker2",this.kitchenWorkerTime,pizzaDeliverysQueue,infoSystem,manager));
		for (int i=0; i<kitchenWorkersVector.size();i++) 
			employeesVector.add(kitchenWorkersVector.get(i));
	}

	private void createSchedulers() {
		this.schedulersVector = new Vector<Scheduler>();
		schedulersVector.add(new Scheduler("scheduler1",ordersQueue,infoSystem,manager));
		schedulersVector.add(new Scheduler("scheduler2",ordersQueue,infoSystem,manager));
		for (int i=0; i<schedulersVector.size();i++) 
			employeesVector.add(schedulersVector.get(i));
	}
	private void createClerks() {
		this.clerksVector = new Vector<Clerk>();
		clerksVector.add(new Clerk("clerk1",callsQueue,ordersQueue,managerCallsQueue,manager));
		clerksVector.add(new Clerk("clerk2",callsQueue,ordersQueue,managerCallsQueue,manager));
		clerksVector.add(new Clerk("clerk3",callsQueue,ordersQueue,managerCallsQueue,manager));
		for (int i=0; i<clerksVector.size();i++) 
			employeesVector.add(clerksVector.get(i));
	}
	private void createQueues() {
		this.callsQueue=new Queue<Call>();
		this.managerCallsQueue=new Queue<Call>();
		this.ordersQueue=new Queue<Order>();
		this.pizzaDeliverysQueue=new BoundedQueue<PizzaDelivery>();	
	}

	private void createCall(String[] file) {
		callsVector=new Vector <Call>();
		for (int i=0; i<file.length; i+=5) {
			callsVector.add(new Call (file[i], file[i+1],file[i+2],file[i+3],file[i+4],this.callsQueue));
		}
	}
	private static String readString(String file) {//convert file to a long String 
		String text = "";
		try {
			Scanner s = new Scanner (new File(file));
			s.nextLine();
			while (s.hasNextLine()) {
				text+=s.nextLine()+ "\t";
			}
		}
		catch (FileNotFoundException e) {//Throw an exception if the file is not found
			System.out.println("file "+ file + " not found");
		}
		return text;
	}
	public static  String[] createFile (String file) {
		String text = readString(file);
		return text.split("\t");	
	}

}