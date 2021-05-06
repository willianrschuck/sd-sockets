package socket.method;

import java.util.List;

import model.Ad;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;
import socket.ResponseStatus;

public class ListAds implements ProtocolMethod {

	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (!cliente.isAutenticado()) {
			return Response.status(ResponseStatus.UNAUTHORIZED);
		}
		
		List<Ad> searchAd = Database.searchAd("user", cliente.getUser());
		
		System.out.println(searchAd);
		
		return Response.ok();
		
	}

}
