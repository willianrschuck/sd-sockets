package socket.method;

import model.Ad;
import model.AdStatus;
import model.Bid;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;
import socket.ResponseStatus;

public class AcceptBid implements ProtocolMethod {

	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (!cliente.isAutenticado()) {
			return Response.status(ResponseStatus.UNAUTHORIZED);
		}
		
		Ad ad = Database.getAd(message.getInteger("id"));
		
		if (ad == null) {
			return Response.status(ResponseStatus.ERROR).message("Ad not found");
		}
		
		try {
			
			ad.lock();
			
		} catch (InterruptedException e) {
		
			return Response.status(ResponseStatus.ERROR).message("Internal server error");
			
		}
		
		try {
			
			if (ad.getUser() != cliente.getUser()) {
				return Response.status(ResponseStatus.UNAUTHORIZED).message("This ad is not owned by you");
			}
			
			if (ad.getStatus() == AdStatus.BID_PENDING) {
				ad.setStatus(AdStatus.SOLD);
				ad.setSoldFor(ad.getBid().getUser());
				ad.getBid().setStatus(Bid.Status.ACCEPTED);
				ad.setBid(null);
			}
			
			return Response.ok().message("Bid accepted");
			
		} finally {
			
			ad.unlock();
			
		}
			
	}

}
