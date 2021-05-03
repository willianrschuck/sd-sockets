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
	
	public String getParamValue(String key) {
		return params.get(key);
	}

	@Override
	public String toString() {
		return "Message [method=" + method + ", params=" + params + "]";
	}

}
