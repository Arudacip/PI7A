package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class IAServiceAdmin extends JFrame implements ActionListener
{
	
	private static final long serialVersionUID = 1L;
	private JButton btStart, btStop, btRestart;
	private JLabel lbStatus;
	private JTextArea txtLog;
	
	
	// Constroi a IA de Administracao do serviço
	public IAServiceAdmin()
	{
		super("Servidor Web v1.0 - ECP7AN-MCA1-09");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false);
		
		// Cria os frames da interface
		JPanel panelStatus = criaFrameStatus();
		JPanel panelLog = criaFrameLog();
		JPanel panelControl = criaFrameControl();
		panelStatus.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelLog.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelControl.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		
		// adiciona os frames na interface principal e exibe a janela
		getContentPane().add(panelStatus, BorderLayout.NORTH);
		getContentPane().add(panelLog, BorderLayout.CENTER);
		getContentPane().add(panelControl, BorderLayout.SOUTH);
	}
	
	private JPanel criaFrameStatus()
	{
		JPanel frameStatus = new JPanel();
		
		// instanciar os objetos de tela
		JLabel lbTitle = new JLabel("Servidor Web - ECP7AN-MCA1-09");
		lbTitle.setFont(new Font("Serif", Font.BOLD, 20));
		lbTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		lbStatus = new JLabel("Status: ---");
		this.setStatus("Ativo"); // STATUS STUB	
		
		// define os listeners dos objetos
		// NAO HA LISTENER AQUI
		
		// acrescenta no frame os objetos
		frameStatus.setLayout(new BorderLayout());
		frameStatus.add(lbTitle, BorderLayout.CENTER);
		frameStatus.add(lbStatus, BorderLayout.SOUTH);
		
		return frameStatus;
	}
	
	private JPanel criaFrameLog()
	{
		JPanel frameLog = new JPanel();
		
		// instanciar os objetos de tela
		JLabel lbTitle = new JLabel("Últimos eventos:");
		txtLog = new JTextArea(6, 30);
		txtLog.setEditable(false);
		txtLog.setBorder(BorderFactory.createEtchedBorder());
		txtLog.setText("Servidor iniciado as 14:17:32 14/02/2019 \n"
				+ "Servidor parado as 14:16:20 14/02/2019"); // TEXTO STUB
		
		// define os listeners dos objetos
		// NAO HA LISTENER AQUI
		
		// acrescenta no frame os objetos
		frameLog.setLayout(new BorderLayout());
		frameLog.add(lbTitle, BorderLayout.NORTH);
		frameLog.add(txtLog, BorderLayout.CENTER);
		
		return frameLog;
	}
	
	private JPanel criaFrameControl()
	{
		JPanel frameControl = new JPanel();
		
		// instanciar os objetos de tela
		btStart = new JButton("Iniciar");
		btStop = new JButton("Parar");
		btRestart = new JButton("Reiniciar");
		
		// define os listeners dos objetos
		btStart.addActionListener(this);
		btStop.addActionListener(this);
		btRestart.addActionListener(this);
		
		// acrescenta no frame os objetos
		frameControl.setLayout(new FlowLayout());
		frameControl.add(btStart);
		frameControl.add(btStop);
		frameControl.add(btRestart);
		
		return frameControl;
	}
	
	public void setStatus(String status)
	{
		lbStatus.setText("Status: " + status);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO: Criar as ações a ser realizadas
	}

}
