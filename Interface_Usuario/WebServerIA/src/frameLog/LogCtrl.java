package frameLog;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LogCtrl 
{
	private JTextArea txtLog;
	
	public JPanel criaFrameLog()
	{
		JPanel frameLog = new JPanel();
		
		// instanciar os objetos de tela
		JLabel lbTitle = new JLabel("�ltimos eventos:");
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
}