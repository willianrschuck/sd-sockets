package socket.method;

import model.Ad;
import model.AdStatus;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;
import socket.ResponseStatus;

public class DeleteAd implements ProtocolMethod {
	
	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (!cliente.isAutenticado()) {
			return Response.status(ResponseStatus.UNAUTHORIZED);
		}
		
		Integer adId = Integer.parseInt(message.getString("id"));
		Ad ad = Database.getAd(adId);
		
		if (ad.getStatus() == AdStatus.CONFIRMED) {
			return Response.status(ResponseStatus.UNAUTHORIZED).message("Ad is already confirmed.");
		}
		
		Database.deleteAd(ad);
		return Response.status(ResponseStatus.OK);
		
	}
	
}
