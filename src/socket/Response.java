package socket;

import java.util.HashMap;
import java.util.Map;

public class Response {

	private ResponseStatus status;
	private Map<String, String> parameters = new HashMap<>();

	private Response(ResponseStatus status) {
		this.status = status;
	}
	
	public static Response ok() {
		return new Response(ResponseStatus.OK);
	}

	public static Response status(ResponseStatus status) {
		return new Response(status);
	}
	
	public Response message(String message) {
		this.parameters.put("message", message);
		return this;
	}
	
	public Response addParameter(String name, String value) {
		this.parameters.put(name, value);
		return this;
	}
	
	public String getStatus() {
		return status.toString();
	}
	
	public Map<String, String> getParameters() {
		return parameters;
	}
	
}
