import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lesson2RegEx {

	public static void main(String[] args) throws IOException {
		 if (args.length < 1) {
			 System.out.println("File name required");
			 return;
	        }
		 
		 String filename = args[0];
		 byte[] bytes = Files.readAllBytes(Paths.get(filename));
		 String s = new String(bytes, Charset.forName("UTF-8"));
		 String regex = "PANID\\s=\\s*(.*)";
		 Pattern pattern = Pattern.compile(regex);
		 Matcher matcher = pattern.matcher(s);		 
		 ArrayList<String> panIds = new ArrayList<String>();
		 while (matcher.find()) 
		 {
			 String match = matcher.group(1);
			 panIds.add(match);
		 }
		 System.out.println("- List of PAN IDs (Total of "+panIds.size()+")");
		 for (String panId : panIds) 
		 {
			 System.out.println("PANID = " + panId);
		 }
		 
		 regex = "Dead_flag\\s?.*?\\s(\\w{16})";
		 pattern = Pattern.compile(regex);
		 matcher = pattern.matcher(s);		 
		 ArrayList<String> macAddresses = new ArrayList<String>();
		 while (matcher.find()) 
		 {
			 String match = matcher.group(1);
			 macAddresses.add(match);
		 }
		 System.out.println("- List of MAC Addresses (Total of "+macAddresses.size()+")");
		 for (String macAddress : macAddresses) 
		 {
			 System.out.println(macAddress);
		 }
		 
		 regex = "Dead_flag\\s?.*?\s(\\w{16}).*(-\\d+\\.\\d+)"; 
		 pattern = Pattern.compile(regex);
		 matcher = pattern.matcher(s);		 
		 ArrayList<String> rssis = new ArrayList<String>();
		 while (matcher.find()) 
		 {
			 String match = matcher.group(2);
			 rssis.add(match);
		 }
		 System.out.println("- List of RF_RSSI_M values for each MAC address");
		 for (int i = 0; i < macAddresses.size(); i++) 
		 {
			 System.out.println(macAddresses.get(i) + " " + rssis.get(i));
		 }
	}
}
