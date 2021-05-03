package socket.method;

import model.User;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;

public class Login implements ProtocolMethod {
	
	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		String username = message.getParamValue("username");
		String password = message.getParamValue("password");
		
		User user = Database.getUserBy(username);
		
		if (user != null && user.getPassword().equals(password)) {
			cliente.setAutenticado(true);
			cliente.setUser(user);
			return new Response("OK");
		}
		
		return new Response("UNAUTHORIZED");
		
	}
	
}
