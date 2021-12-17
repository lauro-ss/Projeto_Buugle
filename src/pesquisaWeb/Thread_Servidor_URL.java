package pesquisaWeb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
			BufferedReader conexao_entrada = new BufferedReader(new InputStreamReader(this.conexao.getInputStream())); //buffer para receber dados do cliente
			DataOutputStream conexao_saida = new DataOutputStream(this.conexao.getOutputStream()); //buffer para envia dados para o cliente
			String pesquisa = conexao_entrada.readLine(); //recebe pesquisa que foi enviada pelo cliente
			System.out.println("Procurando por --- " + pesquisa);
			
			/*a pesquisa e null em momentos em que e necessario o servidor responder se esta online ou nao*/
			if(pesquisa != null) {
				/*verifica se existe um cache correspondente ao conteudo procurado*/
				if(verificaCache(pesquisa)) {
					FileReader tt = new FileReader(System.getProperty("user.dir") + "\\diretoriosWeb\\cache\\" + pesquisa.toLowerCase() + ".txt");
					BufferedReader buffer = new BufferedReader(tt);
					String linha;
					while((linha = buffer.readLine()) != null) {
						conexao_saida.writeBytes(linha + '\n');
					}
					conexao_saida.writeBytes("null" + '\n');
					
					buffer.close();
					tt.close();
					conexao_saida.close();
					conexao_entrada.close();
					this.conexao.close();
				}else {
					URLControl url_control = new URLControl();
					url_control.importUrls();//importa os ulrs que estiverem salvos no import
					
					String path = System.getProperty("user.dir") + "\\diretoriosWeb\\url\\urls.txt";
					BufferedReader url_entrada_txt = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
					
					String url_linha = url_entrada_txt.readLine();
					
					/*cache da pesquisa*/
					FileWriter cache_saida = new FileWriter(System.getProperty("user.dir") + "\\diretoriosWeb\\cache\\" + pesquisa.toLowerCase() + ".txt");
					BufferedWriter buffer_cache_saida = new BufferedWriter(cache_saida);
					
					while(url_linha != null) {
						/*compara o titulo que esta no indice 1 do split, com o conteudo procurado*/
						if(url_linha.split(";")[1].toLowerCase().contains(pesquisa.toLowerCase())) 
						{
							conexao_saida.writeBytes(url_linha + '\n'); //envia a pesquisa para o cliente
							buffer_cache_saida.write(url_linha); //a cada link encontrado, e salvo no cache da pesquisa
							buffer_cache_saida.newLine();
						}
					
						url_linha = url_entrada_txt.readLine(); //ler a proxima linha do txt
						
					}
					conexao_saida.writeBytes("null" + '\n'); //flag para quebrar o while no lado do cliente
					
					buffer_cache_saida.close();
					cache_saida.close();
					url_entrada_txt.close();
					conexao_saida.close();
					conexao_entrada.close();
					this.conexao.close();
				}
			}else {
				conexao_saida.close();
				conexao_entrada.close();
				this.conexao.close();
			}
		} catch (IOException e) {
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
	
}
