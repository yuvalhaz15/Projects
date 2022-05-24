
abstract class Employee extends Thread {
	
	protected Manager myManager;
	protected double salary;
	
	public Employee(Manager manager) {
		this.myManager=manager;
		this.salary=0;
	}
	public Manager getMyManager() {
		return myManager;
	}
	
	public double getSalary() {
		
		return this.salary;
	}
	abstract void calculateSalary();
	abstract void work();
	abstract void finishWork();
}
