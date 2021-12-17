package cliente;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main_Panel implements ActionListener{
	private JFrame frame; //recebe os panels
	private JButton botao_pesquisa;
	private JButton estou_com_sorte;
	private JTextField caixa_pesquisa;
	
	private JPanel panel;
	
	/*variavel relacionadas ao logo*/
	private JLabel label_logo;
	
	/*mensagem*/
	private JLabel mensagem;
	
	@SuppressWarnings("static-access")
	public Main_Panel () {
		this.frame = new JFrame();
		/*comando para o codigo parar de executar quando o programa for fechado*/
		this.frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		this.frame.setSize(800,600);
		
		this.panel = new JPanel();
		this.panel.setSize(800,600);
		this.panel.setBackground(Color.WHITE);
	
		this.botao_pesquisa = new JButton("Pesquisar");
		this.estou_com_sorte = new JButton("Buugle?");
	
		this.botao_pesquisa.setFont(new Font("Arial",Font.PLAIN,12));
		this.botao_pesquisa.setBackground(Color.WHITE);
		this.botao_pesquisa.addActionListener(this);
		this.botao_pesquisa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.botao_pesquisa.setBounds(275,300,100,30);
		
		this.estou_com_sorte.setFont(new Font("Arial",Font.PLAIN,12));
		this.estou_com_sorte.setBackground(Color.WHITE);
		this.estou_com_sorte.addActionListener(this);
		this.estou_com_sorte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.estou_com_sorte.setBounds(410,300,100,30);
		
		this.caixa_pesquisa = new JTextField();
		this.caixa_pesquisa.addActionListener(this);
		this.caixa_pesquisa.setBounds(195,260,400,30);
		
		/*declaracao de imagem e label para exibir*/
		this.label_logo = new JLabel();
		this.label_logo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("logo_buugle_pg1.png")));
		this.label_logo.setBounds(225, 140, 341, 82);
		
		/*mensagem*/
		this.mensagem = new JLabel("Buugle - Alpha - 0.2");
		this.mensagem.setFont(new Font("Arial",Font.PLAIN,12));
		this.mensagem.setBounds(340,450,120,12);
		
		
		this.panel.add(botao_pesquisa);
		this.panel.add(estou_com_sorte);
		this.panel.add(caixa_pesquisa);
		this.panel.add(label_logo);
		this.panel.add(mensagem);
		
		/*comandos para adicionar ao frame*/
		this.frame.add(panel);
		
		this.panel.setVisible(true);
		this.panel.setLayout(null);
		//this.frame.setLayout(null);
		this.frame.setVisible(true);
		//this.frame.validate();
	}
	/*sobrecarga de constructor, esse constructor e chamado em momentos em que e necessario retornar para o main_panel*/
	public Main_Panel(JPanel error_panel) {
		
		this.panel = error_panel;
		this.panel.setSize(800,600);
		this.panel.setBackground(Color.WHITE);
		
		this.botao_pesquisa = new JButton("Pesquisar");
		this.estou_com_sorte = new JButton("Buugle?");
	
		this.botao_pesquisa.setFont(new Font("Arial",Font.PLAIN,12));
		this.botao_pesquisa.setBackground(Color.WHITE);
		this.botao_pesquisa.addActionListener(this);
		this.botao_pesquisa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.botao_pesquisa.setBounds(275,300,100,30);
		
		this.estou_com_sorte.setFont(new Font("Arial",Font.PLAIN,12));
		this.estou_com_sorte.setBackground(Color.WHITE);
		this.estou_com_sorte.addActionListener(this);
		this.estou_com_sorte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.estou_com_sorte.setBounds(410,300,100,30);
		
		this.caixa_pesquisa = new JTextField();
		this.caixa_pesquisa.addActionListener(this);
		this.caixa_pesquisa.setBounds(195,260,400,30);
		
		/*declaracao de imagem e label para exibir*/
		this.label_logo = new JLabel();
		this.label_logo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("logo_buugle_pg1.png")));
		this.label_logo.setBounds(225, 140, 341, 82);
		
		/*mensagem*/
		this.mensagem = new JLabel("Buugle - Alpha - 0.2");
		this.mensagem.setFont(new Font("Arial",Font.PLAIN,12));
		this.mensagem.setBounds(340,450,120,12);
		
		
		this.panel.add(botao_pesquisa);
		this.panel.add(estou_com_sorte);
		this.panel.add(caixa_pesquisa);
		this.panel.add(label_logo);
		this.panel.add(mensagem);
		
		this.panel.setVisible(true);
		this.panel.setLayout(null);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		/*compara se a acao foi a de buugled*/
		if(e.getActionCommand().equals("Buugle?")) {
			Desktop d = Desktop.getDesktop();
			try {
				d.browse(new URI("https://github.com/lauro-ss/Projeto_Buugle"));
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else {
			/*remove tudo o que esta conectado no panel, e envia sua referencia para o proximo panel*/
			this.panel.removeAll();
			this.panel.revalidate();
			this.panel.repaint();
			new Second_Panel(this.panel,caixa_pesquisa.getText());
		}
	}
	
	public static void main(String[] args) {
		 new Main_Panel();
	}
	
	
}

