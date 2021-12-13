package pesquisaWeb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor_URL {
	
	public static void main(String[] args) throws IOException {
		
		@SuppressWarnings("resource")
		ServerSocket servidor_url = new ServerSocket(8040); //cria canal para receber conexoes
		while(true) {
			Socket conexao = servidor_url.accept(); //aguarda ate uma conexao ser aceita
			
			Thread_Servidor_URL pesquisaWeb = new Thread_Servidor_URL(conexao); //cria uma thread para tratar cada cliente
			pesquisaWeb.start(); //inicia a thread
		}
	}
}

