import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadedHandler implements Runnable {
	private Socket incoming;
	
	public ThreadedHandler(Socket incoming)
	{
		this.incoming = incoming;
	}
	
	@Override
	public void run()
	{
		// wait for client connection
		try { 
			OutputStream outStream = incoming.getOutputStream();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream, "UTF-8"), true);
			out.println("HTTP/1.1 200 OK");
			out.println("Content-Type: text/html");
			out.println();
			out.println();
			out.println("<html>");
			out.println("<head><title>Java Networking</title></head>");
			out.println("<body>");
			out.println("<h1>Java Networking</h1>");
			out.println("</body>");
			out.println("</html>");
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
