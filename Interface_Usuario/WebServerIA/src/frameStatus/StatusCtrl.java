package frameStatus;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.IAServiceAdmin;

public class StatusCtrl 
{
	private JLabel lbStatus;
	public JPanel criaFrameStatus()
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
	
	public void setStatus(String status)
	{
		lbStatus.setText("Status: " + status);
	}
}
