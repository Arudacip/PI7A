package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import frameLog.LogCtrl;
import frameStatus.StatusCtrl;
import btns.BtnCtrler;
import btns.ButtonTypes;

public class IAServiceAdmin extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private BtnCtrler buttons = new BtnCtrler();
	private LogCtrl frameLog = new LogCtrl();
	private StatusCtrl frameStatus = new StatusCtrl();
	
	public IAServiceAdmin()
	{
		super("Servidor Web v1.0 - ECP7AN-MCA1-09");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelStatus = frameStatus.criaFrameStatus();
		JPanel panelLog = frameLog.criaFrameLog();
		JPanel panelControl = buttons.criaFrameControl(this);
		panelStatus.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelLog.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelControl.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		
		getContentPane().add(panelStatus, BorderLayout.NORTH);
		getContentPane().add(panelLog, BorderLayout.CENTER);
		getContentPane().add(panelControl, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == buttons.getButton(ButtonTypes.Start)) /*Iniciar*/
		{
			frameLog.AddMessage("Tentativa de iniciar...");
		}
		else if(e.getSource() == buttons.getButton(ButtonTypes.Restart))/*Reiniciar*/
		{
			frameLog.AddMessage("Tentativa de reiniciar...");
		}
		else if(e.getSource() == buttons.getButton(ButtonTypes.Stop))/*Parar*/
		{
			frameLog.AddMessage("Tentativa de parar...");
		}
	}
}