package socket.method;

import model.Ad;
import model.AdStatus;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;
import socket.ResponseStatus;

public class ShowAdBid implements ProtocolMethod {

	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (!cliente.isAutenticado()) {
			return Response.status(ResponseStatus.UNAUTHORIZED);
		}
		
		Ad ad = Database.getAd(message.getInteger("id"));
		
		if (ad == null) {
			return Response.status(ResponseStatus.ERROR).message("Ad not found");
		}
		
		if (ad.getStatus() == AdStatus.SOLD) {
			return Response.ok().message("Ad was already sold");
		}
		
		if (ad.getStatus() == AdStatus.PENDING) {
			return Response.ok().message("Ad still pending");
		}
		
		if (ad.getStatus() == AdStatus.CONFIRMED) {
			return Response.ok().message("Ad has no bids");
		}
		
		return Response.ok()
				.addParameter("user", ad.getBid().getUser().getUsername())
				.addParameter("value", ad.getBid().getValue().toString());
		
	}

}
