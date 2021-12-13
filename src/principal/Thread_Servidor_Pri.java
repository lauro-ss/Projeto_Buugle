package principal;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Thread_Servidor_Pri extends Thread {
		
	private Socket conexao;
	
	public Thread_Servidor_Pri(Socket s) {
		conexao = s;
	}
	
	public void run() {
		try {
			BufferedReader conexao_entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream())); //conexao com o cliente
			String pesquisa = conexao_entrada.readLine(); //recebe o conteudo procurado do cliente
			System.out.println("Recebeu --- " + pesquisa);
			
			Socket conexao_servidor_url = new Socket("127.0.0.1",8040); //conexao com o servidor_URL
			
			
			DataOutputStream conexao_saida_url = new DataOutputStream(conexao_servidor_url.getOutputStream()); //envia dados para o servidor_url
			conexao_saida_url.writeBytes(pesquisa + '\n'); //envia a pesquisa para o servidor url
			
			
			BufferedReader conexao_entrada_url = new BufferedReader(new InputStreamReader(conexao_servidor_url.getInputStream())); //recebe dados do servidor_url
			pesquisa = conexao_entrada_url.readLine(); //recebe o url de retorno do servidor_url
			
			DataOutputStream conexao_saida = new DataOutputStream(conexao.getOutputStream());
			conexao_saida.writeBytes(pesquisa + '\n');
			
			conexao_saida.close();
			conexao_entrada_url.close();
			conexao_saida_url.close();
			conexao_servidor_url.close();
			conexao_entrada.close();
			conexao.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
