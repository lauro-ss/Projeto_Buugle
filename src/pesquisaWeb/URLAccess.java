package pesquisaWeb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class URLAccess {
		
	private URLControl url_control;
	
	public URLAccess() {
		this.url_control = new URLControl();
	}
	
	public String pesquise(String procure) {
		StringBuilder url_string = new StringBuilder();
		try {
			String path = System.getProperty("user.dir") + "\\diretoriosWeb\\url\\urls.txt";
			BufferedReader url_entrada_txt = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String linha = url_entrada_txt.readLine();
			String title;
			String coluna_linhas[] = new String[3];
			int i = 0;
			while(linha != null) {
				coluna_linhas = linha.split(";");
				while(i < coluna_linhas.length) {
					//System.out.println("Coluna_URL_TXT: " + coluna_linhas[i]);
					title = url_control.getDataTitle(coluna_linhas[i]);
					//System.out.println("Titulo_URL: " + title);
					if(url_control.compareTitle(procure, title)) 
					{
						url_string.append(coluna_linhas[i] +";"+title+"!");
					}
					i++;
				}
				linha = url_entrada_txt.readLine(); //ler a proxima linha do txt
				i = 0; //reseta o i para servir de flag para a proxima linha
				//System.out.println("Linha_TXT: " + linha);
			}
			
			url_entrada_txt.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return url_string.toString();
	}
	
	
}
