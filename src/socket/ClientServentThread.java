package socket;
import java.io.IOException;

import socket.method.DeleteAd;
import socket.method.Login;
import socket.method.Logout;
import socket.method.SearchAd;
import socket.method.SendAd;

public class ClientServentThread extends Thread {
	
	private Cliente client;

	public ClientServentThread(Cliente client) {
		this.client = client;
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
		if (message.getMethod().equals("LOGIN")) {
			return new Login().handleMessage(client, message);
		}
		if (message.getMethod().equals("LOGOUT")) {
			return new Logout().handleMessage(client, message);
		}
		if (message.getMethod().equals("SENDAD")) {
			return new SendAd().handleMessage(client, message);
		}
		if (message.getMethod().equals("DELETEAD")) {
			return new DeleteAd().handleMessage(client, message);
		}
		if (message.getMethod().equals("SEARCHAD")) {
			return new SearchAd().handleMessage(client, message);
		}
		return new Response("METHOD NOT FOUND");
	}
	
}
