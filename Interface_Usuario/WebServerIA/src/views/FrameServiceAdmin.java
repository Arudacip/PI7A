package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

import models.AbstractLog;

public class FrameServiceAdmin extends JFrame implements ActionListener
{
	
	private static final long serialVersionUID = 1L;
	private JPanel painel01, painel02, painel03;
	@SuppressWarnings("rawtypes")
	private HashMap componentMap;
	
	public FrameServiceAdmin()
	{
		// Define parametros gerais da View
		super("View Servidor Web v3.0 - ECP7AN-MCA1-09");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		painel01 = new Painel.BuildPanel(new JPanel(), this)
				.layout(Painel.LTBORDER)
				.itens(Painel.PANELSTATUS)
				.borda(Painel.EMPTYBORDER, 10, 10, 10, 10)
				.build();
		getContentPane().add(painel01, BorderLayout.NORTH);
		
		painel02 = new Painel.BuildPanel(new JPanel(), this)
				.layout(Painel.LTBORDER)
				.itens(Painel.PANELLOG)
				.borda(Painel.EMPTYBORDER, 10, 10, 10, 10)
				.build();
		getContentPane().add(painel02, BorderLayout.CENTER);
		
		painel03 = new Painel.BuildPanel(new JPanel(), this)
				.layout(Painel.LTFLOW)
				.itens(Painel.PANELBUTTON)
				.borda(Painel.EMPTYBORDER, 10, 10, 10, 10)
				.build();
		getContentPane().add(painel03, BorderLayout.SOUTH);
		
		createComponentMap();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Component textlog = (Component) getComponentByName("txtLog");
		if(e.getSource() == getComponentByName("btStart")) /*Iniciar*/
		{
			String texto = ((JTextComponent) textlog).getText();
			((JTextComponent) textlog).setText(texto+"\nTentativa de iniciar...");
		}
		else if(e.getSource() == getComponentByName("btRestart"))/*Reiniciar*/
		{
			String texto = ((JTextComponent) textlog).getText();
			((JTextComponent) textlog).setText(texto+"\nTentativa de reiniciar...");
		}
		else if(e.getSource() == getComponentByName("btStop"))/*Parar*/
		{
			String texto = ((JTextComponent) textlog).getText();
			((JTextComponent) textlog).setText(texto+"\nTentativa de parar...");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void createComponentMap()
	{
        componentMap = new HashMap<String,Component>();
        Component[] components = getContentPane().getComponents();
        for (int i=0; i < components.length; i++)
        {
        	componentMap.put(components[i].getName(), components[i]);
        }
	}
	
	public Component getComponentByName(String name)
	{
        if (componentMap.containsKey(name))
        {
        	return (Component) componentMap.get(name);
        }
        else return null;
	}
}