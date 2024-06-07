import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.Arrays;

public class WebServer {

	public static void main(String[] args) throws Exception {
		try {
		System.out.println("Server is running...");
		WebServer s = new WebServer();
		s.runServer();
		} catch (Exception e) {System.out.println("womp womop");}
	}

	public void runServer() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
		HttpContext context = server.createContext("/"); // /www
		HttpContext input = server.createContext("/data"); // used to retrieve frontend input
		context.setHandler(new RequestHandler());
		input.setHandler(new InputHandler()); // handle frontend input
		server.start();
	}

	public String[] parseJSON(JSONObject j) {
		int i;
		String[] orderedValues; // ordered: username, name, email, password

		// json object in string form
		String toParse = j.toString();

		// remove brackets, separate into key/value pairs
		toParse = toParse.substring(1, toParse.length()-1);
		String[] pairs = toParse.split(",");
		
		if (pairs.length != 4) throw new IllegalArgumentException("wrong # of pairs");

		// find keys, extract and order values
		orderedValues = new String[4];
		for (i = 0; i < 4; i++) {
			
			System.out.println(pairs[i]);

			if (pairs[i].substring(1, 9).equals("username")) {
				orderedValues[0] = pairs[i].substring(12, pairs[i].length()-1);
				continue;
			}
			
			if (pairs[i].substring(1, 5).equals("name")) {
				orderedValues[1] = pairs[i].substring(8, pairs[i].length()-1);
				continue;
			}

			if (pairs[i].substring(1, 6).equals("email")) {
				orderedValues[2] = pairs[i].substring(9, pairs[i].length()-1);
				continue;
			}

			if (pairs[i].substring(1, 9).equals("password")) {
				orderedValues[3] = pairs[i].substring(12, pairs[i].length()-1);
				continue;
			}
		}

		
		return orderedValues;
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

private class InputHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange e) throws IOException {

	String data;

	if (e.getRequestMethod().equals("POST")) {
		
		System.out.println("success");

		InputStream is = e.getRequestBody();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();	
		System.out.println(br.toString());
		while ((data = br.readLine()) != null) { System.out.println(data); sb.append(data); }
		JSONObject object = new JSONObject(sb.toString());
		
		String[] parsedData = parseJSON(object);
		System.out.println("Recieved: " + Arrays.toString(parsedData));

	
	
	} else {  e.sendResponseHeaders(405, -1);}
	} 
}

}
