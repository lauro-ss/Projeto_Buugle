package cliente;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Error_Panel implements MouseListener{
	private JPanel panel; //painel generico
	private JLabel label_error_img; //label utilizada para exibir a imagem
	private JLabel label_messenger; //label com mensagem de erro
	private JLabel label_messenger_guide; //label com messagem de guia
	private JLabel label_messenger_link; //label para clicar
	
	private JButton button_tente_novamente;
	
	private Cliente_Conexao conexao;
	
	public Error_Panel(JPanel second_page,Cliente_Conexao conexao) {
		this.panel = second_page;
		this.panel.setSize(800,600);
		this.panel.setBackground(Color.WHITE);
		
		/*carrega a imagem para a label*/
		this.label_error_img = new JLabel();
		this.label_error_img.setIcon(new ImageIcon(getClass().getClassLoader().getResource("error_img_400x400.png")));
		this.label_error_img.setBounds(0, 160, 410, 410);
		
		/*cria a label com a mensagem*/
		this.label_messenger = new JLabel("Ocorreu um erro durante a conexão :(");
		this.label_messenger.setFont(new Font("Arial",Font.BOLD,24));
		this.label_messenger.setBounds(300,276,500,24);
		
		/*cria a label com a mensagem de guia*/
		this.label_messenger_guide = new JLabel("Se o problema persistir, entre em contato pelo link -");
		this.label_messenger_guide.setFont(new Font("Arial",Font.PLAIN,12));
		this.label_messenger_guide.setBounds(300,320,300,24);
		
		/*cria o hyperlink com a funcao de ser clicado*/
		this.label_messenger_link = new JLabel("github.com");
		this.label_messenger_link.setFont(new Font("Arial",Font.PLAIN,12));
		this.label_messenger_link.setForeground(Color.BLUE.darker());
		this.label_messenger_link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.label_messenger_link.addMouseListener(this);
		this.label_messenger_link.setBounds(585,320,70,24);
		
		/*cria um label clicavel*/
		this.button_tente_novamente = new JButton("Recarregar");
		this.button_tente_novamente.setFont(new Font("Arial",Font.BOLD,24));
		this.button_tente_novamente.setBackground(Color.WHITE);
		this.button_tente_novamente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.button_tente_novamente.addMouseListener(this);
		this.button_tente_novamente.setBounds(450,400,170,40);
		
		/*adiciona todas labels ao painel atual*/
		this.panel.add(label_messenger_link);
		this.panel.add(label_messenger_guide);
		this.panel.add(button_tente_novamente);
		this.panel.add(label_error_img);
		this.panel.add(label_messenger);
		
		this.conexao = conexao; //referencia da conexao atual
		
		this.panel.setVisible(true);
		this.panel.setLayout(null);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		/*a partir da posicao y do evento, e possivel saber qual foi a opcao escolhida*/
		if(e.getComponent().getBounds().y == 320) {
			Desktop d = Desktop.getDesktop();
			try {
				/*abre o link no navegador padrao*/
				d.browse(new URI("https://github.com/lauro-ss/Projeto_Buugle"));
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getComponent().getBounds().y == 400) {
			/*verifica se o servidor esta online*/
			if(this.conexao.verificaServidor()) {
				/*remove tudo o que esta conectado no panel, e envia sua referencia para o proximo panel*/
				this.panel.removeAll();
				this.panel.revalidate();
				this.panel.repaint();
				new Main_Panel(this.panel);
			}else {
				/*nao faz nada, pois o servidor esta desligado*/
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
}
