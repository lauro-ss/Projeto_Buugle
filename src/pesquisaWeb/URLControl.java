package pesquisaWeb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLControl {
	
	public String getDataTitle(String url_string) {
		StringBuilder title = new StringBuilder();
		try {
			URL url = new URL(url_string);
			URLConnection url_conect = url.openConnection();
			BufferedReader url_entrada = new BufferedReader(new InputStreamReader(url_conect.getInputStream(),"UTF-8"));
			String linha = "";
			StringBuilder linha_builder = new StringBuilder();
			
			/*encontra o titulo do url*/
			int i = 0;
			int tamanho_linha = 0;
			while(linha != null) {
				linha = url_entrada.readLine();
				linha_builder.append(linha + '\n');
				tamanho_linha = linha_builder.length();
				while(i < tamanho_linha){
					/* compara o conjunto de caracteres em sequencial para dar match com o title do data url */
					if(linha_builder.charAt(i) == '<' && linha_builder.charAt(i+1) == 't' && linha_builder.charAt(i+2) == 'i' && linha_builder.charAt(i+3) == 't' 
					&& linha_builder.charAt(i+4) == 'l' && linha_builder.charAt(i+5) == 'e') 
					{
						
						int aux = 0;
						if(linha_builder.charAt(i+6) != '>') {
							aux = i+7;
							while(linha_builder.charAt(aux) != '>'){
								aux++;
							}
							/* incremento para dar match com o comeco do titulo 
							 * pois o while nao incrementa a ultima soma */
							aux++;
						}else {
							/* condicional caso o titulo esteja em uma quebra de linha */
							if(linha_builder.charAt(i+7) == '\n') {					
								int z = 0;
								do{
									linha = url_entrada.readLine();
									linha_builder.append(linha + '\n');
									z++;
								}while(linha_builder.charAt(z) == '\n');
								/*aux recebe o indice+ o numero de linhas puladas ate o titulo + o numero de caracteres do title*/
								aux = i+z+7;
							}else {
								/*aux recebe o indice +  o numero de caracteres do title*/
								aux = i+7;
							}
						}
						int prim = aux;
						while(linha_builder.charAt(aux) != '<' && linha_builder.charAt(aux) != '\n'){
							/*if para remover espaco no comeco do titulo*/
							if(linha_builder.charAt(prim) == ' ')
								linha_builder.deleteCharAt(prim);
							
							title.append(linha_builder.charAt(aux));
							aux++;
						}
						/*quebra o fluxo do for e do while
						 pois o titulo ja foi encontado */
						i = tamanho_linha;
						linha = null;
					}
					i++;
				}
				
			}
			
			
			url_entrada.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return title.toString();
	}
	
	public Boolean compareTitle(String a, String b) {
		/* caso alguma das strings sejam vazias, o retorno e false 
		 * por isso o operador ou exclusivo e essencial */
		if(a.length() == 0 ^ b.length() == 0) {
			return false;
		}
		/* caso a string a(procurada) seja maior que a string b(title) e feito
		 * uma troca de conteudo, para que a string b seja a maior */
		if(a.length() > b.length()) {
			String auxiliar = a;
			a = b;
			b = auxiliar;
			
		}
		/* o conteudo de cada string e passado para um vetor de caracteres */
		char a_char[] = a.toCharArray(); // metodo que retorna a string em um vetor de char
		char b_char[] = b.toCharArray(); // metodo que retorna a string em um vetor de char
		
		int i = 0; // variavel auxiliar para as repeticoes
		int cont = 0; // variavel flag para definir se as strings sao iguais ou nao
		
		/* caso ambas tenham o mesmo tamanho, e feito uma comparacao direta caractere por caractere */
		if(a.length() == b.length()) {
			for(i = 0; i < a.length(); i++) {
				if(Character.toLowerCase(a_char[i]) == Character.toLowerCase(b_char[i])) {
					cont++;
				}
			}
			/* se o tamanho de a ou de b for igual ao cont, entao eles sao iguais */
			if(cont == a.length()) {
				return true;
			}
		}else {
			/* for que procura a string a(procurada) na string b(title) */
			for(;i < b.length(); i++) {
				/* caso o contador seja igual ao tamanho da palavra a, entao a palavra foi encontrada */
				if(cont == a.length())
					return true;
				/* caso a string b possua um espaco vazio, entao o cont reseta, para continuar a comparacao */
				if(b_char[i] == ' ')
					cont = 0;
				/* utilizando o metodo toLowerCase para converter e comparar ambas as strings 
				 * caso sejam iguais, o cont e incrementado */
				if(Character.toLowerCase(a_char[cont]) == Character.toLowerCase(b_char[i])) {
					cont++;
				}
				
			}
		}
		return false;
	}
	
}
