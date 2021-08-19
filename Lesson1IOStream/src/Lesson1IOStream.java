import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class Lesson1IOStream {

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		 if (args.length < 1) {
			 showHelp();
			 return;
	        }
		 
		 String arg = args[0];
		 
		 if ("--help".equals(arg)) {
			 showHelp();
	        } else if ("--text".equals(arg)) {
	            textFile();
	        }
	        else if ("--binary".equals(arg)) {
	        	binaryFile();
	        }
	        else if ("--object".equals(arg)) {
	        	objectFile();
	        }
	        else {
	        	System.out.println("Wrong argument. Type --help for help");
	        }
		 
	}
	
	private static void textFile() throws UnsupportedEncodingException, IOException {
		Employee[] employees = getEmployees();
	    String filename = "employees.txt";
		
	    //Write To Text File
    	FileOutputStream fos = new FileOutputStream(filename);        
        try (OutputStreamWriter osw =  new OutputStreamWriter(fos, "UTF-8")) 
        {
        	for(Employee e: employees) 
        	{
        		osw.write(e.toString() + System.lineSeparator());
			}
        }

	    //Read From Text File
	    FileInputStream fis = new FileInputStream(filename);
	    while (fis.available() >= 1) {
	      int value = fis.read();
	      System.out.print((char)value);
	    }
	    fis.close();
	    System.out.println();
	}
	
	private static void binaryFile() {
		Employee[] employees = getEmployees();
	    String filename = "employees.bin";  	
		
	    //Write To Binary File
        try
        {
        	DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename));  
        	
        	for(Employee e: employees) 
        	{
        		dos.writeUTF(e.getName());
        		dos.writeDouble(e.getSalary());
        		dos.writeUTF(e.getHireDate());	
			}
        	dos.close();
        }
        catch (Exception e) {
			System.out.println("Error: " + e.getMessage()); 
		}

	    //Read From Binary File
        try
        {
        	DataInputStream dis = new DataInputStream(new FileInputStream(filename));  
        	while (dis.available() >= 1) {
	        	System.out.print(dis.readUTF() + " ");
	    		System.out.print(dis.readDouble() + " ");
	    		System.out.println(dis.readUTF());	
        	}
        	dis.close();
        }
        catch (Exception e) {
			System.out.println("Error: " + e.getMessage()); 
		}
	}
	
	private static void objectFile() throws FileNotFoundException, IOException, ClassNotFoundException  {
		Employee[] employees = getEmployees();
		
		//write
		String filename = "employees.dat";
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
			oos.writeObject(employees);
		}
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage()); 
		}
		
		//read
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(filename))){
			// Read Array
			Employee[] newEmployees = (Employee[]) ois.readObject();
			//Display records from the file
			for(Employee e: newEmployees) {
				System.out.println(e);
			}
		}
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage()); 
		}
	}
	
	private static void showHelp() {
		System.out.println("Options:");
        System.out.println("--text (writes/reads as text file and displays results on console)");
        System.out.println("--binary (writes/reads as binary file and displays results on console)");
        System.out.println("--object (writes/reads as object file and displays results on console)");
	}
 
	private static Employee[] getEmployees() {
	  Employee[] employees = new Employee[100];
	  for (int i = 0; i < 100; i++ ) {
		  employees[i] = new Employee("Employee "+i, 1000+1, 2021, 10, 10);
	  }
	  return employees;
	}
	
}
