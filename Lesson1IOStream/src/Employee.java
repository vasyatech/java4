import java.io.Serializable;
import java.time.LocalDate;

public class Employee implements Serializable {
	private String name;
	private double salary;
	private LocalDate hireDate;
	
	public Employee(String name, double salary, int year, int month, int day) {
		super();
		this.name = name;
		this.salary = salary;
		this.hireDate = LocalDate.of(year, month, day);
	}
	
	public String getName() {
		return name;
	}
	public double getSalary() {
		return salary;
	}
	public String getHireDate() {
		return hireDate.toString();
	}
	
    @Override
    public String toString() {
        return String.format(getName() + " " + getSalary() + " " + getHireDate());
    }
}
