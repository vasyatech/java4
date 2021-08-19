import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;

public class Lesson6NetworkingServer {

	private static int port;
	
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			showHelp();
			return;
		}
		 
		try {
			for (int i = 0; i < args.length; i++) {
				if ("--help".equals(args[i])) {
				 showHelp();
				} else if ("--port".equals(args[i])) {
					i++;
					port = Integer.parseInt(args[i]);
				} 
				else {
					System.out.println("Wrong arguments. Type --help for help");
					return;
				}
			 }
			 if(port <= 0) {
				System.out.println("Wrong PORT_NUMBER. Type --help for help");
				return;
			}
		}
		catch (Exception e) {
			System.out.println("Wrong arguments. Type --help for help");
			return;
		}


		// establish server socket
		 try (ServerSocket s = new ServerSocket(port))
		 {
			int i = 1;
			while (true)
			{
				Socket incoming = s.accept();
				Runnable r = new ThreadedHandler(incoming);
				Thread t = new Thread(r);
				t.start();
				i++;
			}
		 }
	}
		 
	private static void showHelp() {
		System.out.println("Options:");
        System.out.println("--port PORT_NUMBER (PORT_NUMBER - specify port number)");
	}	
}


