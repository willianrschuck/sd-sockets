package socket.method;

import model.Ad;
import model.AdStatus;
import model.Bid;
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
		
		try {
			
			ad.lock();
			
		} catch (InterruptedException e) {
		
			return Response.status(ResponseStatus.ERROR).message("Internal server error");
			
		}
		
		
		try {

			if (ad.getUser() == cliente.getUser()) {
				return Response.status(ResponseStatus.ERROR).message("Cannot buy your own ad");
			}
			
			if (ad.getStatus() == AdStatus.SOLD) {
				return Response.status(ResponseStatus.ERROR).message("Ad was already sold");
			}
			
			if (ad.getStatus() == AdStatus.PENDING) {
				return Response.status(ResponseStatus.ERROR).message("Ad still pending");
			}
			
			if (ad.getStatus() == AdStatus.BID_PENDING) {
				return Response.status(ResponseStatus.ERROR).message("Ad already has a bid pending");
			}
			
			Bid bid = new Bid(ad);
			bid.setUser(cliente.getUser());
			bid.setValue(message.getDouble("value"));
			
			try {
				validateBid(bid);
			} catch (Exception e) {
				return Response.status(ResponseStatus.BAD_REQUEST).message(e.getMessage());
			}
			
			ad.setStatus(AdStatus.BID_PENDING);
			ad.setBid(bid);
			
			
			Database.save(ad.getBid());
			
			return Response.ok().message("Ad purchased!");
			
		} finally {

			ad.unlock();
			
		}
		
	}

	private void validateBid(Bid bid) throws Exception {
		if (bid.getValue() == null) {
			throw new Exception("The field value was left blank");
		}
		if (bid.getValue() <= 0.0) {
			throw new Exception("The field value cannot have values equals or below zero");
		}		
	}
	
}
