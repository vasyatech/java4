import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Lesson6NetworkingClient {
	private static int port;
	private static String address;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		if (args.length < 1) {
			showHelp();
			return;
		}
		try {
			for (int i = 0; i < args.length; i++) {
				if ("--help".equals(args[i])) {
					showHelp();
				} else if ("--address".equals(args[i])) {
					i++;
					address = args[i];
				} else if ("--port".equals(args[i])) {
					i++;
					port = Integer.parseInt(args[i]);
				} else {
					System.out.println("Wrong arguments. Type --help for help");
					return;
				}
			}	
			if(port <= 0) {
				System.out.println("Wrong PORT_NUMBER. Type --help for help");
				return;
			}
			if(address.isEmpty()) { 
				System.out.println("Wrong ADDRESS. Type --help for help");
				return;
			}
		}
		catch (Exception e) {
			System.out.println("Wrong arguments. Type --help for help");
			return;
		}



		try (Socket s = new Socket(address, port);
		Scanner in = new Scanner(s.getInputStream(), "UTF-8"))
		{
			while (in.hasNextLine())
			{
				String line = in.nextLine();
				System.out.println(line);
			}
		}
	}
	
	private static void showHelp() {
		System.out.println("Options:");
		System.out.println("--address ADDRESS (ADDRESS - specify address)");
        System.out.println("--port PORT (PORT - specify port number)");
	}	

}
