import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lesson3Streams {

	public static void main(String[] args) throws IOException {
		 if (args.length < 1) {
			 System.out.println("File name required");
			 return;
	        }
		 
		 String filename = args[0];
		 byte[] bytes = Files.readAllBytes(Paths.get(filename));
		 String s = new String(bytes, StandardCharsets.UTF_8);
         
		 Predicate<String> wordFilter = Pattern
                .compile("00000000+([[a-f0-9]]{8})")
                .asPredicate(); 
		 
		 double startTime, endTime, streamTime, parallelStreamTime, difference;
		 int iteration = 0;
		 difference = -1;
		 while (difference <= 0) {
			 ++iteration;
			 System.out.println("Try " + iteration + ":");
			 System.out.println("String Size:" + s.length());
			 List<String> words = Arrays.asList(s.split("\\r?\\n"));
			 startTime = System.currentTimeMillis();
			 long count = words
	                 .stream()
	                 .filter(wordFilter)
	                 .count();
			 endTime = System.currentTimeMillis();
			 streamTime = endTime - startTime;
			 
			 startTime = System.currentTimeMillis();
			 count = words
	                 .parallelStream()
	                 .filter(wordFilter)
	                 .count();
			 endTime = System.currentTimeMillis();
			 parallelStreamTime = endTime - startTime;
			 
			 //System.out.println("Count: " + count);
			 System.out.println("Millisecs using stream: " + streamTime);
			 System.out.println("Millisecs using parallelStream: " + parallelStreamTime);
			 
			 difference = streamTime - parallelStreamTime; 
			 if (difference < 0) {
				 System.out.println("Results: stream was " + Math.abs(difference) + "ms faster than parallelStream");
				 System.out.println("Doubling String size and trying again\n");
				 s = s + s;
			 }
			 else if (difference == 0) {
				 System.out.println("Results: stream was equal to parallelStream");
				 System.out.println("Doubling String size and trying again\n");
				 s = s + s; 
			 }
			 else {
				 System.out.println("Results: paralleStream was " + Math.abs(difference) + "ms faster than stream\nAll done!");
			 }
		 }
		
	}

}
