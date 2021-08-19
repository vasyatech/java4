import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class Lesson5Concurrent {
	private static int numThreads = 0;
	private static boolean noLocking = true;
	private static boolean reentrantLock = false;
	private static boolean atomicLong = false;
	public static long count = 0;
	public static AtomicLong nextNumber = new AtomicLong();
	public static ReentrantLock reLock = new ReentrantLock();

	static class fileOne implements Callable {
		public String call() {
			run(new File("../src/Lesson5Concurrent.java"));
			return "The Lesson5Concurrent.java file - processed";
		}
	}
	
	static class fileTwo implements Callable {
		public String call() {
			run(new File("Lesson5Concurrent.class"));
			return "The Lesson5Concurrent.class file - processed";
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		 if (args.length < 1) {
			 showHelp();
			 return;
	        }
		 
		 for (int i = 0; i < args.length; i++) {
			if ("--help".equals(args[i])) {
			 showHelp();
			} else if ("--num-threads".equals(args[i])) {
				i++;
				numThreads = Integer.parseInt(args[i]);
			} else if ("--ReentrantLock".equals(args[i])) {
				noLocking = false;
				reentrantLock = true;
				atomicLong = false;
			} else if ("--AtomicLong".equals(args[i])) {
				noLocking = false;
				reentrantLock = false;
				atomicLong = true;
			} else {
				System.out.println("Wrong arguments. Type --help for help");
			}
		 }
		if(numThreads <= 0) 
			System.out.println("Wrong NUMBER-OF-THREADS. Type --help for help");

		ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
		Future<String> result1 = executorService.submit(new fileOne());
		Future<String> result2 = executorService.submit(new fileTwo());
		System.out.println(result1.get());
		System.out.println(result2.get());
		System.out.println("Count: " + count);
		System.out.println("All Done\n");
		executorService.shutdown();
	}

	private static void showHelp() {
		System.out.println("Options:");
        System.out.println("--num-threads NUMBER-OF-THREADS (NUMBER-OF-THREADS - specify how many threads to create)");
        System.out.println("--ReentrantLock (if you want to use locking. By default you do not use any locking)");
        System.out.println("--AtomicLong (if you want to use AtomicLong. By default you do not use any locking)");
	}
	
	private static void run(File filename) {
		try {
			if(noLocking) {
				countChars(filename);
			} else if (reentrantLock) {
				countCharsReentrantLock(filename);
			} else if (atomicLong) {
				countCharsAtomicLong(filename);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void countChars(File filename) throws IOException {
		//System.out.println("countChars");
	    FileInputStream fis = new FileInputStream(filename);
	    while (fis.available() >= 1) {
	      fis.read();
	      count++;	    
	      
		}
		fis.close();
	}
	
	private static void countCharsReentrantLock(File filename) throws IOException {
		//System.out.println("countCharsReentrantLock");
	    FileInputStream fis = new FileInputStream(filename);
	    while (fis.available() >= 1) {
	      fis.read();
	      reLock.lock();
	      try {
	    	  count++;
	      }
		      finally { reLock.unlock(); }		
		}
		fis.close();
	}
	
	private static void countCharsAtomicLong(File filename) throws IOException {
		//System.out.println("countCharsAtomicLong");
	    FileInputStream fis = new FileInputStream(filename);
	    while (fis.available() >= 1) {
	      fis.read();
	      count = nextNumber.incrementAndGet();	    
		}
		fis.close();
	}
}
