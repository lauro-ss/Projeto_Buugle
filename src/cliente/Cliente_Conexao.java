package cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Cliente_Conexao {
	
	@SuppressWarnings("resource")
	public Hyperlink[] enviar_pesquisa(String pesquisa) {
		try {
			Socket conexao = new Socket("127.0.0.1",8040); //conexao com o servidor_principal
			BufferedReader conexao_entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream())); //recebe dados do servidor_pri
			DataOutputStream conexao_saida = new DataOutputStream(conexao.getOutputStream()); //envia dados para o servidor_pri
			if(pesquisa == null)
				return new Hyperlink[0];
			if(pesquisa.equals(""))
				return new Hyperlink[0]; //caso a pesquisa seja vazia ou nula retorna 0 hyperlinks
			conexao_saida.writeBytes(pesquisa + '\n'); //envia para o servidor_principal o conteudo procurado
			String conteudo_encontrado = "";
			ArrayList<Hyperlink> links = new ArrayList<Hyperlink>(); //arraylist para guarda todos os hyperlinks
			while(!(conteudo_encontrado.equals("null"))) {
				conteudo_encontrado = conexao_entrada.readLine(); //recebe cada url + titulo
				if(!(conteudo_encontrado.equals("null"))) //condicao para nao guardar o flag "null" vindo pelo servidor
					links.add(new Hyperlink(conteudo_encontrado.split(";")[0],conteudo_encontrado.split(";")[1])); //recebe o url procurado
			}
			
			conexao_saida.close();
			conexao.close();
			
			return links.toArray(new Hyperlink[0]); //retorna o array com os dados procurado
			//return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}
	}
	public boolean verificaServidor() {
		try {
			Socket conexao = new Socket("127.0.0.1",8040);
			conexao.close();
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
	}
	
}
