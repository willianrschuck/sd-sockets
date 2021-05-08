package socket.method;

import model.Ad;
import model.AdStatus;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;
import socket.ResponseStatus;

public class Buy implements ProtocolMethod {

	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (!cliente.isAutenticado()) {
			return Response.status(ResponseStatus.UNAUTHORIZED);
		}
		
		Ad ad = Database.getAd(message.getInteger("id"));
		
		if (ad == null) {
			return Response.status(ResponseStatus.ERROR).message("Ad not found");
		}
		
		if (ad.getUser() == cliente.getUser()) {
			return Response.status(ResponseStatus.ERROR)
					.message("Cannot buy your own ad");
		}
		
		if (ad.getStatus() == AdStatus.SOLD) {
			return Response.status(ResponseStatus.ERROR).message("Ad was already sold");
		}
		
		if (ad.getStatus() == AdStatus.PENDING) {
			return Response.status(ResponseStatus.ERROR).message("Ad still pending");
		}
		
		ad.setStatus(AdStatus.CONFIRMED);
		
		return Response.ok().message("Ad purchased!");
		
	}
	
}
