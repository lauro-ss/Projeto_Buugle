package pesquisaWeb;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TesteArquivos {
	

	public static void main(String[] args) throws IOException {
	
	    URLAccess teste = new URLAccess();
	    System.out.println(teste.pesquise("google"));
		/*
			String a = "";
			String b = "";
			if(a.length() == 0 ^ b.length() == 0) {
				System.out.println("Zeroou");
				return;
			}
			char a_char[];
			a_char = a.toCharArray();
			char b_char[];
			b_char = b.toCharArray();
			int cont = 0;
			int i = 0;
			
			if(a.length() == b.length()) {
				cont = 0;
				for(; i < a.length(); i++) {
					if(Character.toLowerCase(a_char[i]) == Character.toLowerCase(b_char[i])) {
						cont++;
					}
				}
			}else {
				/* variavel auxiliar para controlar o indice do menor vetor */
				/*for(; i < b.length(); i++) {
					if(cont == a.length())
						break;
					if(b_char[i] == ' ')
						cont = 0;
					if(Character.toLowerCase(a_char[cont]) == Character.toLowerCase(b_char[i])) {
						cont++;
					}
					
				}
			}*/
			/*if(a.length() == cont)
				System.out.println("São semelhantes");*/
		/*try {
			
			URL url = new URL("https://www.youtube.com/watch?v=d54ISermi4E&ab_channel=EiNerd");
			URLConnection url_conect = url.openConnection();
			/* ler um pedaco do codigo fonte */
			/*BufferedReader arquivo_entrada = new BufferedReader(new InputStreamReader(url.openStream()));
			BufferedReader url_entrada = new BufferedReader(new InputStreamReader(url_conect.getInputStream()));
			String linha;
			StringBuilder linha_builder = new StringBuilder();
			*/
			/*encontra o titulo do url*/
	    /*
			while((linha = url_entrada.readLine()) != null) {
				linha_builder.append(linha + '\n');
			}
			for(int i = 0; i < linha_builder.length(); i++) {
				if(linha_builder.charAt(i) == '<' && linha_builder.charAt(i+1) == 't' && linha_builder.charAt(i+2) == 'i') {
					int aux = i+7;
					while(linha_builder.charAt(aux) != '<'){
						System.out.print(linha_builder.charAt(aux));
						aux++;
					}
				}
			}
			url_entrada.close();
			//arquivo_entrada.close();
			
			/*
			//, "UTF-8"
			String diretorio_arquivo = System.getProperty("user.dir") + "\\diretoriosWeb\\" + "view-source_https___www.google.com.br" + ".html";
			BufferedReader arquivo_entrada = new BufferedReader(new InputStreamReader(new FileInputStream(diretorio_arquivo),"UTF-8"));
			ArrayList<String> lista_tag = new ArrayList<String>();
			int i = 0;
			String linha;
			while((linha = arquivo_entrada.readLine()) != null) {
				if(linha.equals("// Google Inc."))
					System.out.println(linha);
				lista_tag.add(linha);
				i++;
			}
			System.out.println(lista_tag.size());
			for(String leia : lista_tag) {
				System.out.println(leia);
			}
			System.out.println(i);
			arquivo_entrada.close();
			*/
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

}
