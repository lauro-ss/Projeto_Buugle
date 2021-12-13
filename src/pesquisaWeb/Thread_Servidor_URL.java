package pesquisaWeb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Thread_Servidor_URL extends Thread{
	
	private Socket conexao;
	
	public Thread_Servidor_URL(Socket s) {
		conexao = s;
	}
	
	public void run() {
		try {
			BufferedReader conexao_entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream())); //recebe dados do servidor_principal
			String pesquisa = conexao_entrada.readLine(); //recebe pesquisa que foi enviada para o servidor_principal
			System.out.println("Procurando por --- " + pesquisa);
			URLAccess procura = new URLAccess(); //classe que controla o acesso ao url e retorna a pesquisa feita
	
			pesquisa = procura.pesquise(pesquisa); //retorna o url que deu match com o conteudo pesquisado
			
			DataOutputStream conexao_saida = new DataOutputStream(conexao.getOutputStream()); //envia dados para o servidor_pri
			conexao_saida.writeBytes(pesquisa + '\n'); //envia a pesquisa para o servidor_principal
			
			conexao_saida.close();
			conexao_entrada.close();
			conexao.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
