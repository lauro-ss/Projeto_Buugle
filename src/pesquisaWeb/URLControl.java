package pesquisaWeb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLControl {
	/**
	 * Encontra o titulo da URL
	 * @param url_string URL na qual o titulo sera buscado, url ex: "https://www.google.com.br/"
	 * @return
	 */
	public String getDataTitle(String url_string) {
		StringBuilder title = new StringBuilder();
		try {
			URL url = new URL(url_string);
			URLConnection url_conect = url.openConnection();
			BufferedReader url_entrada = new BufferedReader(new InputStreamReader(url_conect.getInputStream(),"UTF-8"));
			String linha = url_entrada.readLine();
			
			while(linha != null) {
				//linha_builder.append(linha + '\n');
				/*mudancas*/
				if(linha.contains("<title")) {
					int indice_title = linha.indexOf("<title") + 6; //posicao na linha que corresponde ao final do <title> no caso, o '>'
					
					/* if que trata casos comuns de escrita de url, como por exemplo:
					 * <title>titulo do url</title>
					 */
					if(linha.charAt(indice_title) == '>') {
						indice_title++;
						if(linha.length() > indice_title && linha.charAt(indice_title) != '\n') {
							while(linha.charAt(indice_title) != '<') {
								title.append(linha.charAt(indice_title));
								indice_title++;
							}
							
							url_entrada.close();
							return title.toString();
							
						}else {
							linha = url_entrada.readLine(); //como o proximo e um \n, e necessario ler a proxima linha a qual e o titulo
							/*logica utilizando um while, sera usada apenas caso exista uma exeçao para casos em que esteja dessa forma
							 * <title>
							 * titulo do url</title>
							 */
							//indice_title = 0;
							/*while para pega todos os caracteres do titulo*/
							/*while(linha.length() > indice_title && linha.charAt(indice_title) != '<' && linha.charAt(indice_title) != '\n') {
								title.append(linha.charAt(indice_title));
								indice_title++;
							}*/
							
							url_entrada.close();
							return linha;
						}
					}
					/* else trata casos em que o titulo esta escrito dessa forma:
					 * <title id="pageTitle">titulo do url</title>
					 */
					else {
						
						while(linha.charAt(indice_title) != '>')
							indice_title++; //roda ate o final do primeiro title
						indice_title++; //indice do inicio do titulo do url
						while(linha.charAt(indice_title) != '<') {
							title.append(linha.charAt(indice_title));
							indice_title++;
						}
						
						url_entrada.close();
						return title.toString();
					}
				}
				linha = url_entrada.readLine();
				
			}
			
			
			url_entrada.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "Erro durante a busca";
		}
		
		
		return "Título Indisponível";
	}
	/**
	 * Importa as urls que deseja que sejam encontradas pelo servidor
	 * @throws IOException
	 */
	public void importUrls() throws IOException {
		FileWriter url_saida_txt = new FileWriter(System.getProperty("user.dir") + "\\diretoriosWeb\\url\\urls.txt",true);
		BufferedWriter buffer_saida = new BufferedWriter(url_saida_txt);
		
		FileReader url_entrada_txt = new FileReader(System.getProperty("user.dir") + "\\diretoriosWeb\\url\\import_urls.txt");
		BufferedReader buffer_entrada = new BufferedReader(url_entrada_txt);
		
		String url;
		while((url = buffer_entrada.readLine()) != null) {
			buffer_saida.write(url + ";" + getDataTitle(url));
			buffer_saida.newLine();
		}
		buffer_saida.close();
		buffer_entrada.close();
		url_saida_txt.close();
		url_entrada_txt.close();
		zero_importUrls();
	}
	/**
	 * Zera os dados do arquivo de import_urls
	 * @throws IOException
	 */
	public void zero_importUrls() throws IOException {
		FileWriter url_saida_txt = new FileWriter(System.getProperty("user.dir") + "\\diretoriosWeb\\url\\import_urls.txt");
		url_saida_txt.close();
	}
	
}
