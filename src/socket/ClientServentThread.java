package socket;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import socket.method.AcceptBid;
import socket.method.Buy;
import socket.method.ConfirmAd;
import socket.method.DeleteAd;
import socket.method.ListAds;
import socket.method.ListBids;
import socket.method.LockAdForLongTime;
import socket.method.Login;
import socket.method.Logout;
import socket.method.ProtocolMethod;
import socket.method.RefuseBid;
import socket.method.SearchAd;
import socket.method.SendAd;
import socket.method.ShowAdBid;

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
		methods.put("buy", new Buy());
		methods.put("lockadforlongtime", new LockAdForLongTime());
		methods.put("listbids", new ListBids());
		methods.put("acceptbid", new AcceptBid());
		methods.put("refusebid", new RefuseBid());
		methods.put("showadbid", new ShowAdBid());
		methods.put("listbids", new ListBids());
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
		
		Map<String, String> parameters = response.getParameters();
		for (Entry<String, String> entry : parameters.entrySet()) {
			client.out.print(entry.getKey());
			client.out.print("=");
			client.out.println(entry.getValue());
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
			if (line.trim().isEmpty()) {
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
			return Response.status(ResponseStatus.BAD_REQUEST).message("Invalid request");
		}
		
		try {
			
			ProtocolMethod method = methods.get(message.getMethod().toLowerCase());
			if (method != null) {
				return method.handleMessage(client, message);
			}
			return Response.status(ResponseStatus.BAD_REQUEST).message("Method not found");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return Response.status(ResponseStatus.ERROR).message("Error during the request processing");
			
		}
		
		
	}
	
}
