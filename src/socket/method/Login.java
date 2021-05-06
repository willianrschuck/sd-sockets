package socket.method;

import model.User;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;
import socket.ResponseStatus;

public class Login implements ProtocolMethod {
	
	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		String username = message.getString("username");
		String password = message.getString("password");
		
		User user = Database.getUserBy(username);
		
		if (user != null && user.getPassword().equals(password)) {
			
			cliente.setAutenticado(true);
			cliente.setUser(user);
			return Response.ok();
			
		}
		
		return Response.status(ResponseStatus.UNAUTHORIZED);
		
	}
	
}
