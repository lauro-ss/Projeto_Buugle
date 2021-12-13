package pesquisaWeb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor_URL {
	
	public static void main(String[] args) throws IOException {
		
		@SuppressWarnings("resource")
		ServerSocket servidor_web = new ServerSocket(8040);
		while(true) {
			Socket conexao = servidor_web.accept();
			
			Thread_Servidor_URL pesquisaWeb = new Thread_Servidor_URL(conexao);
			pesquisaWeb.start();
		}
	}
}

