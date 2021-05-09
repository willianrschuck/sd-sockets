package socket.method;

import java.util.Iterator;
import java.util.List;

import model.Bid;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;
import socket.ResponseStatus;

public class ListBids implements ProtocolMethod {

	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (!cliente.isAutenticado()) {
			return Response.status(ResponseStatus.UNAUTHORIZED);
		}
		
		List<Bid> searchAd = Database.getBids(cliente.getUser());
		
		StringBuilder serializedBids = new StringBuilder();
		Iterator<Bid> iterator = searchAd.iterator();

		while (iterator.hasNext()) {
			serializedBids.append(iterator.next().serialize());
			if (iterator.hasNext()) {
				serializedBids.append("|");
			}
		}
		
		return Response.ok().message(serializedBids.toString());
		
	}

}
