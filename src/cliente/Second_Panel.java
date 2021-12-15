package cliente;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Second_Panel implements ActionListener, MouseListener{

	private JButton botao_pesquisa;
	private JTextField caixa_pesquisa;
	private String last_pesquisa;
	
	private JPanel panel;
	
	/*variavel relacionada ao logo*/
	private JLabel label_logo;
	
	private JLabel conteudo_encontrado[];
	private JLabel conteudo_encontrado_aux[];
	private JLabel conteudo_nao_encontrado;
	
	/*variavel relacionadal a segunda pagina em diante*/
	private JLabel go_to_pg[];
	private int flag = 0;
	private int quant_pags = 0;
	private int pg_atual = 0; //indice da pagina
	
	private Cliente_Conexao conexao;
	private Hyperlink links[];
	private int quant_links = 0;
	
	
	public Second_Panel(JPanel main_page,String pesquisa) {
		
		this.panel = main_page;
		this.panel.setSize(800,600);
		this.panel.setBackground(Color.WHITE);
		
		this.botao_pesquisa = new JButton("Pesquisar");
		this.botao_pesquisa.setFont(new Font("Arial",Font.PLAIN,12));
		this.botao_pesquisa.setBackground(Color.WHITE);
		this.botao_pesquisa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.botao_pesquisa.addActionListener(this);
		this.botao_pesquisa.setBounds(620,15,100,29);
		
		this.label_logo = new JLabel();
		this.label_logo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("logo_buugle_pg2.png")));
		this.label_logo.addMouseListener(this);
		this.label_logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.label_logo.setBounds(10, 10, 168, 40);
		
		this.caixa_pesquisa = new JTextField(pesquisa);
		this.caixa_pesquisa.addActionListener(this);
		this.caixa_pesquisa.setBounds(195,15,400,30);
		
		/*mensagem*/
		this.conexao = new Cliente_Conexao();
		this.links = conexao.enviar_pesquisa(pesquisa); //envia a string procurada para o servidor principal
		if(this.links != null && this.links.length > 0) {
			this.last_pesquisa = pesquisa;
			this.quant_links = links.length;
			this.quant_pags = (this.quant_links/5)+1; //quantidade de paginas
			this.conteudo_encontrado = new JLabel[quant_links]; //quantidade de links encontrados
			this.conteudo_encontrado_aux = new JLabel[quant_links];
			
			this.go_to_pg = new JLabel[quant_pags];
			/*condicao que confirma a necessidade de haver mais de uma pagina*/
			for(int i = 0; i < quant_pags; i++) {
				this.go_to_pg[i] = new JLabel(Integer.toString(i+1));
				this.go_to_pg[i].setFont(new Font("Arial",Font.PLAIN,12));
				this.go_to_pg[i].addMouseListener(this);
				this.go_to_pg[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				this.go_to_pg[i].setBounds(250+(i*30),530,12,12);
				this.panel.add(go_to_pg[i]);
			}
			/*exibe todos os hyperlinks*/
			mostraLinks();
			
			this.panel.add(botao_pesquisa);
			this.panel.add(caixa_pesquisa);
			this.panel.add(label_logo);
			
			this.panel.setVisible(true);
			this.panel.setLayout(null);
		}else {
			/*caso para servidor desligado ou com problemas*/
			if(this.links == null) {
				/*remove tudo o que esta conectado no panel, e envia sua referencia para o proximo panel*/
				this.panel.removeAll();
				this.panel.revalidate();
				this.panel.repaint();
				new Error_Panel(this.panel,this.conexao);
			}else {
			this.conteudo_encontrado = new JLabel[0]; //seta para 0 a pesquisa vazia
			this.conteudo_encontrado_aux = new JLabel[0]; //seta para 0 a pesquisa vazia
			this.go_to_pg = new JLabel[0]; //seta para 0 a pesquisa vazia
			this.last_pesquisa = pesquisa;
			
			this.conteudo_nao_encontrado = new JLabel("Conteúdo não encontrado.");
			this.conteudo_nao_encontrado.setFont(new Font("Arial",Font.PLAIN,24));
			this.conteudo_nao_encontrado.setBounds(195,60,350,26);
			
			this.panel.add(conteudo_nao_encontrado);
			this.panel.add(botao_pesquisa);
			this.panel.add(caixa_pesquisa);
			this.panel.add(label_logo);
			
			/*atualiza o painel atual*/
			this.panel.setVisible(true);
			this.panel.setLayout(null);
			}
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(caixa_pesquisa.getText().compareToIgnoreCase(last_pesquisa) == 0) {
			/*caso o conteudo pesquisado seja o mesmo, nao ocorre nada*/
		}else if(caixa_pesquisa.getText().equals("")) {
			/*caso o conteudo pesquisado seja vazio, nao ocorre nada*/
		}else {
			int t = conteudo_encontrado.length;
			for(int i = 0; i < t; i++) {
				panel.remove(conteudo_encontrado[i]); //remove do painel todos os hyperlinks
				panel.remove(conteudo_encontrado_aux[i]); //remove do painel todos os host from <url>
			}
			t = go_to_pg.length;
			for(int i = 0; i < t; i++) {
				panel.remove(go_to_pg[i]);
			}
			this.conteudo_encontrado = null; //seta como nulo para 'zerar' o array
			this.conteudo_encontrado_aux = null; //seta como nulo para 'zerar' o array
			this.go_to_pg = null; //seta o como nulo para 'zerar' o numero de paginas
			
			this.panel.repaint();
			
			this.links = conexao.enviar_pesquisa(caixa_pesquisa.getText()); //envia a string procurada para o servidor principal
			/*tratamento para pesquisa encontrada ou nao*/
			if(this.links != null && this.links.length > 0) {
				/*remove o label com mensagem de nada encontrado */
				if(conteudo_nao_encontrado != null)
					this.panel.remove(conteudo_nao_encontrado);
				this.panel.setVisible(true);
				this.panel.setLayout(null);
				this.panel.repaint();
				
				this.last_pesquisa = caixa_pesquisa.getText(); //guarda a ultima pesquisa feita, pois caso seja igual a proxima, nao muda os links
				this.quant_links = links.length; //quantidade de hyperlinks
				this.quant_pags = (this.quant_links/5)+1; //como o maximo de links exibidos e 5, entao essa expressao pega a media deles em 5
				this.conteudo_encontrado = new JLabel[quant_links];
				this.conteudo_encontrado_aux = new JLabel[quant_links];
				
				this.go_to_pg = new JLabel[quant_pags];
				/*condicao que confirma a necessidade de haver mais de uma pagina*/
				for(int i = 0; i < quant_pags; i++) {
					this.go_to_pg[i] = new JLabel(Integer.toString(i+1));
					this.go_to_pg[i].setFont(new Font("Arial",Font.PLAIN,12));
					this.go_to_pg[i].addMouseListener(this);
					this.go_to_pg[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					this.go_to_pg[i].setBounds(250+(i*30),530,12,12);
					this.panel.add(go_to_pg[i]);
				}
				
				this.pg_atual = 0; //numero de paginas setado para 0
				mostraLinks(); //adiciona na tela ate cinco hyperlinks
				
				/*atualiza o painel atual*/
				this.panel.setVisible(true);
				this.panel.setLayout(null);
				this.panel.repaint();
			}else {
				/*caso para servidor desligado ou com problemas*/
				if(this.links == null) {
					/*remove tudo o que esta conectado no panel, e envia sua referencia para o proximo panel*/
					this.panel.removeAll();
					this.panel.revalidate();
					this.panel.repaint();
					new Error_Panel(this.panel,this.conexao);
				}else {
					if(conteudo_nao_encontrado != null)
						this.panel.remove(conteudo_nao_encontrado);
					
					this.panel.setVisible(true);
					this.panel.setLayout(null);
					this.panel.repaint();
					
					this.conteudo_encontrado = new JLabel[0]; //seta para 0 a pesquisa vazia
					this.conteudo_encontrado_aux = new JLabel[0]; //seta para 0 a pesquisa vazia
					this.go_to_pg = new JLabel[0]; //seta para 0 a pesquisa vazia
					this.last_pesquisa = caixa_pesquisa.getText(); //guarda a ultima pesquisa feita, pois caso seja igual a proxima, nao muda os links
					
					if(this.conteudo_nao_encontrado == null) {
						this.conteudo_nao_encontrado = new JLabel("Conteúdo não encontrado.");
						this.conteudo_nao_encontrado.setFont(new Font("Arial",Font.PLAIN,24));
						this.conteudo_nao_encontrado.setBounds(195,60,350,26);
					}
					
					this.panel.add(conteudo_nao_encontrado);
					this.panel.add(botao_pesquisa);
					this.panel.add(caixa_pesquisa);
					this.panel.add(label_logo);
					
					/*atualiza o painel atual*/
					this.panel.setVisible(true);
					this.panel.setLayout(null);
				}
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		/*condicao para clicar na logo e voltar ao menu inicial*/
		if(e.getComponent().getBounds().y == 10) {
			/*remove tudo o que esta conectado no panel, e envia sua referencia para o proximo panel*/
			this.panel.removeAll();
			this.panel.revalidate();
			this.panel.repaint();
			new Main_Panel(this.panel);
		}
		/*for para comparar todas as paginas e ver qual delas foi clicada*/
		for(int i = 0, x = 250; i < quant_pags; i++, x += 30) {
			if(e.getComponent().getBounds().x == x) {
				acessConteudo(i);
				flag = i*5;
			}
		}
		
		/*for para comparar todos os hyperlinks e ver qual foi clicado*/
		Desktop d = Desktop.getDesktop();
		for(int i = flag; i < quant_links; i++) {
			if(e.getComponent().getBounds().y == (60+((i-flag)*100))) {
				try {
					d.browse(new URI(links[i].getUrl()));
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// Ocorre quando clica com o botao do mouse
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// Ocorre quando solta o botao do mouse
		
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// Ocorre quando passa o mouse em cima do objeto
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// Ocorre quando tira o mouse de cima do objeto
		
	}
	
	private void acessConteudo(int start) {
		for(int i = 0; i < conteudo_encontrado.length; i++) {
				this.conteudo_encontrado[i].setVisible(false);
				this.conteudo_encontrado_aux[i].setVisible(false);
				//System.out.println("Ocultando -- " + this.conteudo_encontrado[i].getText());
			}
		int aux = 0;
		for(int i = start*5; i < quant_links; i++) {
			/*exibe apenas os cinco links que sao possiveis na interface*/
				if(aux == 5)
					break;
				this.conteudo_encontrado[i].setVisible(true);
				this.conteudo_encontrado_aux[i].setVisible(true);
				//System.out.println("Exibindo -- " + this.conteudo_encontrado[i].getText());
				this.panel.add(conteudo_encontrado[i]);
				this.panel.add(conteudo_encontrado_aux[i]);
				aux++;
			}
			/*atualiza o painel atual*/
			this.panel.setVisible(true);
			this.panel.setLayout(null);
			this.panel.repaint();
			
		
	}
	private void mostraLinks() {
		int aux = 0;
		for(int i = 0; i < quant_links; i++) {
			/*if(links[i].getTitle().length() == 32) {
				System.out.println("Preciso");
			}*/
			System.out.println("Criando hyperlink para: "+links[i].getTitle());
			this.conteudo_encontrado[i] = new JLabel(links[i].getTitle());
			this.conteudo_encontrado[i].setFont(new Font("Arial",Font.PLAIN,20));
			this.conteudo_encontrado[i].setForeground(Color.BLUE.darker());
			this.conteudo_encontrado[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.conteudo_encontrado[i].addMouseListener(this);
			
			this.conteudo_encontrado_aux[i] = new JLabel("Host from <"+ links[i].getHost()+">");
			this.conteudo_encontrado_aux[i].setFont(new Font("Arial",Font.PLAIN,12));
			this.conteudo_encontrado_aux[i].setForeground(Color.GRAY);
			/*caso o i seja menor que cinco, entao adiciona normalmente o hyperlink*/
			//if(i < 5)
			//	this.conteudo_encontrado[i].setBounds(195,60+(i*100),350,26);
			/*adiciona todos os outros hyperlinks apos o quinto*/
			/*condicao para aumentar o indice da pagina*/
			if(i >= 5 && aux == 5) {
				pg_atual++;
				aux = 0;
			}
			aux++;
			/*((i-(5*pg_atual))*100) e a equacao responsavel por pular um mesmo numero x de pixels para encaixar os hyperlinks*/
			this.conteudo_encontrado[i].setBounds(195,60+((i-(5*pg_atual))*100),350,26);
			this.conteudo_encontrado_aux[i].setBounds(205,85+((i-(5*pg_atual))*100), 350, 12);
			/*adicional ao panel todos os cinco primeiros hyperlinks*/
			if(i < 5) {
				this.panel.add(conteudo_encontrado[i]);
				this.panel.add(conteudo_encontrado_aux[i]);
			}
		}
		/*atualiza o painel atual*/
		this.panel.setVisible(true);
		this.panel.setLayout(null);
		this.panel.repaint();
	}
}
