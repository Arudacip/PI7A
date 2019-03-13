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
		JLabel lbTitle = new JLabel("�ltimos eventos:");
		txtLog = new JTextArea(6, 30);
		txtLog.setEditable(false);
		txtLog.setBorder(BorderFactory.createEtchedBorder());
		frameLog.setLayout(new BorderLayout());
		frameLog.add(lbTitle, BorderLayout.NORTH);
		frameLog.add(txtLog, BorderLayout.CENTER);
		
		return frameLog;
	}
	
	public void AddMessage(String message)
	{
		txtLog.setText(txtLog.getText() + "\n" + message);
	}
}