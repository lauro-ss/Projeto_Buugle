package pesquisaWeb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Thread_Servidor_URL extends Thread{
	
	private Socket conexao;
	
	public Thread_Servidor_URL(Socket s) {
		conexao = s;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			BufferedReader conexao_entrada = new BufferedReader(new InputStreamReader(this.conexao.getInputStream())); //buffer para receber dados do cliente
			DataOutputStream conexao_saida = new DataOutputStream(this.conexao.getOutputStream()); //buffer para envia dados para o cliente
			String pesquisa = conexao_entrada.readLine(); //recebe pesquisa que foi enviada pelo cliente
			System.out.println("Procurando por --- " + pesquisa);
			
			/*verifica se existe um cache correspondente ao conteudo procurado*/
			if(verificaCache(pesquisa)) {
				FileInputStream arquivo_entrada = new FileInputStream(System.getProperty("user.dir") + "\\diretoriosWeb\\cache\\" + pesquisa.toLowerCase() + ".txt");
				ObjectInputStream objeto_entrada = new ObjectInputStream(arquivo_entrada);
				
				ArrayList<String> links = new ArrayList<String>();
				links = (ArrayList<String>) objeto_entrada.readObject();
				for(int i = 0; i < links.size(); i++) {
					conexao_saida.writeBytes(links.get(i).toString() + '\n'); //envia a pesquisa para o cliente
				}
				conexao_saida.writeBytes("null" + '\n'); //flag para quebrar o while no lado do cliente
				
				
				objeto_entrada.close();
				conexao_saida.close();
				conexao_entrada.close();
				this.conexao.close();
			}else {
				URLControl url_control = new URLControl();
				ArrayList<String> links = new ArrayList<String>(); //arraylist para guarda todos os hyperlinks
				
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
							links.add(coluna_linhas[i] + ";" + title);
						}
						i++;
					}
					linha = url_entrada_txt.readLine(); //ler a proxima linha do txt
					i = 0; //reseta o i para servir de flag para a proxima linha
					//System.out.println("Linha_TXT: " + linha);
				}
				conexao_saida.writeBytes("null" + '\n'); //flag para quebrar o while no lado do cliente
				
				criaCache(links,pesquisa); //cria um cache a partir do retorno da pesquisa
				
				url_entrada_txt.close();
				conexao_saida.close();
				conexao_entrada.close();
				this.conexao.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private boolean verificaCache(String conteudo_procurado) {
		try {
			FileInputStream arquivo_entrada = new FileInputStream(System.getProperty("user.dir") + "\\diretoriosWeb\\cache\\" + conteudo_procurado.toLowerCase() + ".txt");
			arquivo_entrada.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	private void criaCache(ArrayList<String> t, String conteudo_procurado) {
		FileOutputStream arquivo_saida;
		ObjectOutputStream objeto_saida;
		try {
			arquivo_saida = new FileOutputStream(System.getProperty("user.dir") + "\\diretoriosWeb\\cache\\" + conteudo_procurado.toLowerCase() + ".txt");
			objeto_saida = new ObjectOutputStream(arquivo_saida);
			objeto_saida.writeObject(t);
			
			objeto_saida.close();
			arquivo_saida.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
