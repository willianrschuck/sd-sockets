package socket;
import java.io.IOException;
import java.net.ServerSocket;

public class Servidor {
	
	public static void main(String[] args) {
		
		try (ServerSocket server = new ServerSocket(3500)) {
			
			while (true) {

				Cliente cliente = new Cliente(server.accept());
				ClientServentThread thread = new ClientServentThread(cliente);
				thread.start();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}