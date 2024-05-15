import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.io.File;
import java.util.Scanner;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public class WebServer {

	public static void main(String[] args) throws Exception {
		//try {
		System.out.println("Server is running...");
		WebServer s = new WebServer();
		s.runServer();
		//} catch (Exception e) {System.out.println("womp womop");}
	}

	public void runServer() throws IOException {
		
		HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
		HttpContext context = server.createContext("/"); // /www
		context.setHandler(new RequestHandler());
		server.start();
	}


private class RequestHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange e) throws IOException {
		
		String path = e.getRequestURI().getPath().substring(1); // 5
		System.out.println("Recieved Request: " + path);
		e.getResponseHeaders().add("Content-type", "text/html");
		System.out.println(path);	
		File file = new File(path);
		file = new File("public/" + file.getName());
		String response = "";
		Scanner fin;

		if ( file.exists() ) {  
			fin = new Scanner(file);
			while(fin.hasNextLine()) response += fin.nextLine() + "\n";
		} 
		else { // else go to homepage
			file = new File("public/homepage.html");
			fin = new Scanner(file);
			while(fin.hasNextLine()) response += fin.nextLine() + "\n";
		}

		e.sendResponseHeaders(200, 0);
		OutputStream os = e.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	
}
}
