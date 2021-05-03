package socket.method;

import model.Ad;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;

public class DeleteAd implements ProtocolMethod {
	
	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (!cliente.isAutenticado()) {
			return new Response("UNAUTHORIZED");
		}
		
		Integer adId = Integer.parseInt(message.getParamValue("id"));
		Ad ad = Database.getAd(adId);
		Database.deleteAd(ad);
		return new Response("OK");
		
	}
	
}
