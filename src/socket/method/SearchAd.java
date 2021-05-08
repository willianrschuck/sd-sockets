package socket.method;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import model.Ad;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;

public class SearchAd implements ProtocolMethod {
	
	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		String field = message.getString("field");
		String value = message.getString("value");
		
		List<Ad> searchAd = Database.searchAd(field, value);
		
		Collections.sort(searchAd);
		if (searchAd.size() > 5) {
			searchAd.subList(0, 4);
		}
		
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

