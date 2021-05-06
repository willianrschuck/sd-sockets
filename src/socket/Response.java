package socket;

public class Response {

	private ResponseStatus status;
	private String message;

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
		this.message = message;
		return this;
	}
	
	public String getStatus() {
		return status.toString();
	}
	
	public String getMessage() {
		return message;
	}
	
}
