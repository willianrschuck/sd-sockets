import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SocketClient extends Thread {

	private static boolean conexaoEstaAtiva = true;
	private Socket conexao;

	public SocketClient(Socket conexao) {
		this.conexao = conexao;
	}

	@Override
	public void run() {

		try {
			BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
			String mensagemRecebida;
			while (true) {
				mensagemRecebida = entrada.readLine();
				System.out.println(mensagemRecebida);
				if (mensagemRecebida == null) {
					System.out.println("Conexão encerrada!");
					conexaoEstaAtiva = false;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		try {

			String ip = "localhost";
			Integer port = 3500;

			Socket conexao = new Socket(ip, port);

			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			PrintStream saida = new PrintStream(conexao.getOutputStream());

			new SocketClient(conexao).start();

			String mensagem;
			while (conexaoEstaAtiva) {
				mensagem = teclado.readLine();
				saida.println(mensagem);
			}
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}