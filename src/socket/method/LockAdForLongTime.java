package socket.method;

import model.Ad;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;
import socket.ResponseStatus;

public class LockAdForLongTime implements ProtocolMethod {

	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		Integer adId = message.getInteger("id");
		Ad ad = Database.getAd(adId);
		
		try {
			ad.lock();
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			ad.unlock();
			return Response.status(ResponseStatus.ERROR).message("Internal server error");
		}
		
		ad.unlock();
		
		return Response.ok().message("Long work was done!");
		
	}

}
