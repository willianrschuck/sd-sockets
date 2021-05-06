package socket.method;

import socket.Cliente;
import socket.Message;
import socket.Response;
import socket.ResponseStatus;

public class Logout implements ProtocolMethod {
	
	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (cliente.isAutenticado()) {
			
			cliente.setAutenticado(false);
			cliente.setUser(null);
			return Response.ok();
			
		}
		return Response.status(ResponseStatus.UNAUTHORIZED);
		
	}
	
}
