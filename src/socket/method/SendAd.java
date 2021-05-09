package socket.method;

import model.Ad;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;
import socket.ResponseStatus;

public class SendAd implements ProtocolMethod {
	
	private static final int defaultPriority = 5;
	private static final Double pricePerChar = 0.5; 
	
	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (!cliente.isAutenticado()) {
			return Response.status(ResponseStatus.UNAUTHORIZED).message("Client not authenticated");
		}
		
		try {
			
			Ad ad = extractAd(cliente, message);
			validate(ad);
			Database.save(ad);
			
		} catch (Exception e) {
			
			return Response.status(ResponseStatus.BAD_REQUEST).message(e.getMessage());
			
		}
		
		return Response.ok().message("Ad recived");
		
	}

	private void validate(Ad ad) throws Exception {
		if (ad.getName() == null || ad.getName().trim().isEmpty()) {
			throw new Exception("The field name was left blank");
		}
		if (ad.getText() == null || ad.getText().trim().isEmpty()) {
			throw new Exception("The field text was left blank");
		}
		if (ad.getAdPrice() == null) {
			throw new Exception("The field adPrice was left blank");
		}
		if (ad.getAdPrice() <= 0.0) {
			throw new Exception("The field adPrice cannot have values equals or below zero");
		}
	}

	private Ad extractAd(Cliente cliente, Message message) {
		Ad ad = new Ad();
		
		ad.setName(message.getString("name"));
		ad.setText(message.getString("text"));
		ad.setProductPrice(message.getDouble("price"));
		ad.setPriority(message.getInteger("priority"));
		ad.setUser(cliente.getUser());
		
		if (ad.getPriority() == null) {
			ad.setPriority(defaultPriority);
		}
		
		double priorityMultiplier = ad.getPriority() / 5.0;
		ad.setAdPrice(ad.getText().length() * pricePerChar * priorityMultiplier);
		
		return ad;
	}
	
}
