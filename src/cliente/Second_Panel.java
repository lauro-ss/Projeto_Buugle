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
		this.botao_pesquisa.addActionListener(this);
		this.botao_pesquisa.setBounds(620,15,100,29);
		
		this.label_logo = new JLabel();
		this.label_logo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("logo_buugle_pg2.png")));
		this.label_logo.setBounds(10, 10, 168, 40);
		
		this.caixa_pesquisa = new JTextField(pesquisa);
		this.caixa_pesquisa.addActionListener(this);
		this.caixa_pesquisa.setBounds(195,15,400,30);
		
		/*mensagem*/
		this.conexao = new Cliente_Conexao();
		this.links = conexao.enviar_pesquisa(pesquisa); //envia a string procurada para o servidor principal
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
		mostraLinks();
		//acessConteudo(0); //adiciona na tela ate cinco hyperlinks
		
		this.panel.add(botao_pesquisa);
		this.panel.add(caixa_pesquisa);
		this.panel.add(label_logo);
		
		this.panel.setVisible(true);
		this.panel.setLayout(null);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(caixa_pesquisa.getText().compareToIgnoreCase(last_pesquisa) == 0) {
			/*caso o conteudo pesquisado seja o mesmo, nao ocorre nada*/
		}else {
			int t = conteudo_encontrado.length;
			for(int i = 0; i < t; i++) {
				panel.remove(conteudo_encontrado[i]);
			}
			this.conteudo_encontrado = null;
			this.conteudo_encontrado_aux = null;
			this.links = conexao.enviar_pesquisa(caixa_pesquisa.getText()); //envia a string procurada para o servidor principal
			this.last_pesquisa = caixa_pesquisa.getText();
			this.quant_links = links.length;
			this.quant_pags = (this.quant_links/5)+1;
			this.conteudo_encontrado = new JLabel[quant_links];
			this.conteudo_encontrado_aux = new JLabel[quant_links];
			
			this.pg_atual = 0; //numero de paginas setado para 0
			mostraLinks(); //adiciona na tela ate cinco hyperlinks
			
			this.panel.setVisible(true);
			this.panel.setLayout(null);
			this.panel.repaint();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		/*condicional para caso o usuario va para a proxima pagina*/
		if(e.getComponent().getBounds().x == 250) {
			acessConteudo(0);
			flag = 0;
		}
		else if(e.getComponent().getBounds().x == 280) {
			acessConteudo(1);
			flag = 5*1;
		}
		
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
			this.conteudo_encontrado[i].setBounds(195,60+((i-(5*pg_atual))*100),350,26);
			this.conteudo_encontrado_aux[i].setBounds(205,85+((i-(5*pg_atual))*100), 350, 12);
			/*adicional ao panel todos os cinco primeiros hyperlinks*/
			if(i < 5) {
				this.panel.add(conteudo_encontrado[i]);
				this.panel.add(conteudo_encontrado_aux[i]);
			}
		}
		
		this.panel.setVisible(true);
		this.panel.setLayout(null);
		this.panel.repaint();
	}
}
