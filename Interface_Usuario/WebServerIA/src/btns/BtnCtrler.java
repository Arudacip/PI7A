package btns;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.IAServiceAdmin;

public class BtnCtrler 
{
	private ArrayList<JButton> buttonsList = new ArrayList<>();
	public BtnCtrler()
	{
		buttonsList.add(new start());
		buttonsList.add(new stop());
		buttonsList.add(new restart());
	}
	public JPanel criaFrameControl(IAServiceAdmin ui)
	{
		JPanel frameControl = new JPanel();
		frameControl.setLayout(new FlowLayout());
		for(JButton btn : buttonsList)
		{
			btn.addActionListener(ui);
			frameControl.add(btn);
		}
		return frameControl;
	}

}
