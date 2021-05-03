package socket;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import socket.method.DeleteAd;
import socket.method.Login;
import socket.method.Logout;
import socket.method.ProtocolMethod;
import socket.method.SearchAd;
import socket.method.SendAd;

public class ClientServentThread extends Thread {
	
	private Cliente client;
	private Map<String, ProtocolMethod> methods;

	public ClientServentThread(Cliente client) {
		this.client = client;
		methods = new HashMap<>();
		methods.put("login", new Login());
		methods.put("logout", new Logout());
		methods.put("sendad", new SendAd());
		methods.put("deletead", new DeleteAd());
		methods.put("searchad", new SearchAd());	
	}

	@Override
	public void run() {

		try {
			
			while (true) {
				
				Message message = readMessage();
				
				if (message == null) {
					break;
				}
				
				Response response = handeRequest(message);
				sendResponse(response);
				
			}
			
		} catch (IOException e) {
			// Nada a fazer
		}
		
	}

	private void sendResponse(Response response) {
		client.out.println(response.getStatus());
	}

	private Message readMessage() throws IOException {
		
		String line = client.in.readLine();
		
		if (line == null) {
			return null;
		}
		
		Message message = new Message();
		
		message.setMethod(line);
		while (!(line = client.in.readLine()).equals("end")) {
			String[] split = line.split("=");
			message.setParam(split[0], split[1]);
		}
		
		return message;
		
		
	}

	private Response handeRequest(Message message) {
		ProtocolMethod method = methods.get(message.getMethod());
		if (method != null) {
			return method.handleMessage(client, message);
		}
		return new Response("METHOD NOT FOUND");
	}
	
}
