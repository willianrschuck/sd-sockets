package socket.method;

import static java.util.Arrays.asList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Ad;
import socket.Cliente;
import socket.Database;
import socket.Message;
import socket.Response;

public class SendAd implements ProtocolMethod {
	
	@Override
	public Response handleMessage(Cliente cliente, Message message) {
		
		if (!cliente.isAutenticado()) {
			return new Response("UNAUTHORIZED");
		}
		
		Ad ad = new Ad();
		ad.setName(message.getParamValue("name"));
		ad.setKeywords(asList(message.getParamValue("keywords")));
		ad.setTarget(message.getParamValue("target"));
		ad.setText(message.getParamValue("text"));
		ad.setPriority(Integer.parseInt(message.getParamValue("priority")));
		ad.setSchedule(parseSchedule(message.getParamValue("schedule")));
		ad.setUser(cliente.getUser());
		
		Database.save(ad);
		System.out.println(ad);
		
		return new Response("OK");
		
	}

	private List<Date> parseSchedule(String scheduleText) {
		List<Date> schedule = new ArrayList<>();
		String[] scheduleFormatedDates = scheduleText.split(",");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		for (String formatedDate : scheduleFormatedDates) {
			try {
				schedule.add(dateFormat.parse(formatedDate));
			} catch (Exception e) {
				//
			}
		}
		return schedule;
	}
	
}
