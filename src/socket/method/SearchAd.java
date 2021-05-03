package socket.method;

import java.util.List;

import model.Ad;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;

public class SearchAd implements ProtocolMethod {
	
	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (!cliente.isAutenticado()) {
			return new Response("UNAUTHORIZED");
		}

		String field = message.getParamValue("field");
		String value = message.getParamValue("value");
		
		List<Ad> searchAd = Database.searchAd(field, value);
		
		System.out.println(searchAd);
		
		return new Response("OK");
		
	}
	
}

