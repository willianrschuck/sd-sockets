package socket.method;

import socket.Cliente;
import socket.Message;
import socket.Response;

public interface ProtocolMethod {

	Response handleMessage(Cliente cliente, Message message);
	
}
