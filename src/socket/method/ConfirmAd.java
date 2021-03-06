package socket.method;

import model.Ad;
import model.AdStatus;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;
import socket.ResponseStatus;

public class ConfirmAd implements ProtocolMethod {

	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (!cliente.isAutenticado()) {
			return Response.status(ResponseStatus.UNAUTHORIZED);
		}
		
		Integer adId = Integer.parseInt(message.getString("id"));
		Ad ad = Database.getAd(adId);
		
		if (ad == null) {
			return Response.status(ResponseStatus.ERROR).message("Ad not found");
		}
		
		if (ad.getUser() != cliente.getUser()) {
			return Response.status(ResponseStatus.UNAUTHORIZED)
					.message("This ad is not owned by you");
		}
		
		if (ad.getStatus() == AdStatus.PENDING) {
			ad.setStatus(AdStatus.CONFIRMED);
		}
		
		return Response.ok().message("Ad confirmed");
		
	}

}
