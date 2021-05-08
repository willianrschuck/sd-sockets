package socket.method;

import java.util.Iterator;
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
		
		StringBuilder serializedAds = new StringBuilder();
		Iterator<Ad> iterator = searchAd.iterator();

		while (iterator.hasNext()) {
			serializedAds.append(iterator.next().serialize());
			if (iterator.hasNext()) {
				serializedAds.append("|");
			}
		}
		
		return Response.ok().message(serializedAds.toString());
		
	}

}
