package btns;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.IAServiceAdmin;

public class BtnCtrler 
{
	private ArrayList<ArudacipButton> buttonsList = new ArrayList<>();
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
		for(ArudacipButton btn : buttonsList)
		{
			JButton myBT = (JButton)btn;
			myBT.addActionListener(ui);
			frameControl.add(myBT);
		}
		return frameControl;
	}
	
	public JButton getButton(ButtonTypes button)
	{
		for(ArudacipButton obj : buttonsList)
		{
			if(obj.type() == button)
				return (JButton) obj;
		}
		return null;
	}
}
