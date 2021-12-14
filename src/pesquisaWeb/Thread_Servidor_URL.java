package pesquisaWeb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
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
			BufferedReader conexao_entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream())); //buffer para receber dados do cliente
			DataOutputStream conexao_saida = new DataOutputStream(conexao.getOutputStream()); //buffer para envia dados para o cliente
			String pesquisa = conexao_entrada.readLine(); //recebe pesquisa que foi enviada pelo cliente
			System.out.println("Procurando por --- " + pesquisa);
			URLControl url_control = new URLControl();
			
			String path = System.getProperty("user.dir") + "\\diretoriosWeb\\url\\urls.txt";
			BufferedReader url_entrada_txt = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String linha = url_entrada_txt.readLine();
			String title;
			String coluna_linhas[] = new String[3]; //numero de colunas do arquivo urls.txt
			int i = 0;
			while(linha != null) {
				coluna_linhas = linha.split(";");
				while(i < coluna_linhas.length) {
					//System.out.println("Coluna_URL_TXT: " + coluna_linhas[i]);
					title = url_control.getDataTitle(coluna_linhas[i]);
					//System.out.println("Titulo_URL: " + title);
					if(url_control.compareTitle(pesquisa, title)) 
					{
						conexao_saida.writeBytes(coluna_linhas[i] + ";" + title + '\n'); //envia a pesquisa para o cliente
					}
					i++;
				}
				linha = url_entrada_txt.readLine(); //ler a proxima linha do txt
				i = 0; //reseta o i para servir de flag para a proxima linha
				//System.out.println("Linha_TXT: " + linha);
			}
			conexao_saida.writeBytes("null" + '\n');
			
			
			url_entrada_txt.close();
			conexao_saida.close();
			conexao_entrada.close();
			conexao.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
