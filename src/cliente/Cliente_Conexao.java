package cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;

public class Cliente_Conexao {
	
	public Hyperlink[] enviar_pesquisa(String pesquisa) {
		try {
			
			Socket conexao = new Socket("127.0.0.1",8020); //conexao com o servidor_principal
			BufferedReader conexao_entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream())); //recebe dados do servidor_pri
			DataOutputStream conexao_saida = new DataOutputStream(conexao.getOutputStream()); //envia dados para o servidor_pri
			conexao_saida.writeBytes(pesquisa + '\n'); //envia para o servidor_principal o conteudo procurado
			
			pesquisa = conexao_entrada.readLine(); //recebe o url procurado
			
			conexao_saida.close();
			conexao.close();
			
			return cria_Hyperlinks(pesquisa); //retorna o url procurado
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Hyperlink[] cria_Hyperlinks(String conteudos) throws MalformedURLException{
		String remove_1[] = conteudos.split("!");
		int quant_links = remove_1.length;
		Hyperlink hyper[] = new Hyperlink[quant_links];
		
		String remove_2[];
		
		for(int i = 0;i < quant_links; i++) {
			remove_2 = remove_1[i].split(";");
			hyper[i] = new Hyperlink(remove_2[0],remove_2[1]);
		}
		return hyper;
	}
	
}
