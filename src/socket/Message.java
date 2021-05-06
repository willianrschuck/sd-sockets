package socket;

import java.util.HashMap;
import java.util.Map;

public class Message {

	private String method;
	private Map<String, String> params = new HashMap<>();

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setParam(String key, String value) {
		params.put(key, value);
	}
	
	public String getString(String key) {
		return params.get(key);
	}
	
	public Integer getInteger(String key) {
		String value = params.get(key);
		return value != null ? Integer.parseInt(value) : null;
	}
	
	public Double getDouble(String key) {
		String value = params.get(key);
		return value != null ? Double.parseDouble(value) : null;
	}

	@Override
	public String toString() {
		return "Message [method=" + method + ", params=" + params + "]";
	}

}
