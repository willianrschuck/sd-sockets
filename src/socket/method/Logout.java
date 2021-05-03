package socket.method;

import socket.Cliente;
import socket.Message;
import socket.Response;

public class Logout implements ProtocolMethod {
	
	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (cliente.isAutenticado()) {
			cliente.setAutenticado(false);
			cliente.setUser(null);
			return new Response("OK");
		}
		return new Response("UNAUTHORIZED");
		
	}
	
}
