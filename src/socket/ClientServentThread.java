package socket;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import socket.method.ConfirmAd;
import socket.method.DeleteAd;
import socket.method.ListAds;
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
		methods.put("listads", new ListAds());
		methods.put("confirmad", new ConfirmAd());
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
			e.printStackTrace();
		}
		
	}

	private void sendResponse(Response response) {
		client.out.println(response.getStatus());
		if (response.getMessage() != null && !response.getMessage().isEmpty()) {
			client.out.println(response.getMessage());
		}
	}

	private Message readMessage() throws IOException {
		
		String line = client.in.readLine();
		
		if (line == null) {
			return null;
		}
		
		Message message = new Message();
		
		message.setMethod(line);
		while ( (line = client.in.readLine()) != null ) {
			if (line.equals("END")) {
				break;
			}
			try {
				String[] split = line.split("=");
				message.setParam(split[0], split[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return message;
		
		
	}

	private Response handeRequest(Message message) {
		
		if (message == null) {
			return Response.status(ResponseStatus.BAD_REQUEST);
		}
		
		ProtocolMethod method = methods.get(message.getMethod().toLowerCase());
		if (method != null) {
			return method.handleMessage(client, message);
		}
		
		return Response.status(ResponseStatus.BAD_REQUEST).message("Method not found");
		
	}
	
}
