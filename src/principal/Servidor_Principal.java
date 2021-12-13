package principal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor_Principal {

	public static void main(String[] args) throws IOException {
		
		@SuppressWarnings("resource")
		ServerSocket servidor_principal = new ServerSocket(8020);
		while(true) {
			Socket conexao = servidor_principal.accept();
			
			Thread_Servidor_Pri pesquisaWeb = new Thread_Servidor_Pri(conexao);
			pesquisaWeb.start();
		}

	}

}
