package socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import model.User;

public class Cliente {

	public final BufferedReader in;
	public final PrintStream out;
	
	private boolean autenticado; // É possível utilizar um enum para controlar os estados
	private User user;

	public Cliente(Socket conexao) throws IOException {
		in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
		out = new PrintStream(conexao.getOutputStream());
		autenticado = false;
	}
	
	public boolean isAutenticado() {
		return autenticado;
	}
	
	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

}
