package views;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

/**
 * Esse e o Componente de TODOS os JComponent das Views do Pattern MVC
 */

public class Item extends JComponent
{
	public static final int TITLEBOLD1 = 1;
	public static final int TITLEBOLD2 = 2;
	public static final int TITLEITALIC1 = 3;
	public static final int TITLEITALIC2 = 4;
	public static final int EMPTYBORDER = 1;
	public static final int ETCHEDBORDER1 = 2;
	public static final int ETCHEDBORDER2 = 3;
	private static final long serialVersionUID = 1L;
	private JComponent item;
	
	/**
	 * Esse e o Builder de TODOS os JComponent das Views do Pattern MVC
	 */
	
	public static class BuildItem
	{
		
		//Atributos requeridos
		private final String nome;
		private int tipo;
		private Border borda;
		private String texto;
		private boolean editavel;
		private Font estilo;
		private JFrame listener;
		
		public BuildItem(String nome, int tipo)
		{
			this.nome = nome;
			this.tipo = tipo;
		}
		
		public BuildItem texto(String texto)
		{
			this.texto = texto;
			return this;
		}
		
		public BuildItem editavel(boolean editavel)
		{
			this.editavel = editavel;
			return this;
		}
		
		public BuildItem listener(JFrame ui)
		{
			this.listener = ui;
			return this;
		}
		
		public BuildItem borda(int tipo, int top, int left, int bottom, int right)
		{
			switch (tipo)
			{
				case 1:
					this.borda = BorderFactory.createEmptyBorder(top, left, bottom, right);
					break;
				case 2:
					this.borda = BorderFactory.createEtchedBorder();
					break;
				case 3:
					this.borda = BorderFactory.createEtchedBorder(top);
					break;
				default:
					this.borda = BorderFactory.createEmptyBorder(top, left, bottom, right);
					break;
			}
			return this;
		}
		
		public BuildItem estilo()
		{
			estilo = new Font("Titulo1", Font.BOLD, 20);
			return this;
		}
		
		public BuildItem estilo(int tipo)
		{
			switch (tipo)
			{
				case 1:
					estilo = new Font("Titulo1", Font.BOLD, 20);
					break;
				case 2:
					estilo = new Font("Titulo2", Font.BOLD, 16);
					break;
				case 3:
					estilo = new Font("Titulo3", Font.ITALIC, 20);
					break;
				case 4:
					estilo = new Font("Titulo4", Font.ITALIC, 16);
					break;
				default:
					estilo = new Font("Titulo1", Font.BOLD, 20);
					break;
			}
			return this;
		}
		
		public Item build()
		{
			return new Item(this);
		}
	}
	
	public Item(BuildItem builder)
	{
		// Cria o item com o Builder, seja la qual for o tipo de item de interface
		switch (builder.tipo)
		{
		
			/*
		 	 * JLabel
		 	 */
			case 1:
				item = new JLabel();
				((JLabel) item).setText(builder.texto);
				break;
				
			/*
			 * JButton
			 */
			case 2:
				item = new JButton();
				((AbstractButton) item).setText(builder.texto);
				((AbstractButton) item).addActionListener((ActionListener) builder.listener);
				break;
				
			/*
			 * JTextArea
			 */
			case 3:
				item = new JTextArea();
				((JTextComponent) item).setEditable(builder.editavel);
				break;
				
			/*
			 * JLabel
			 */
			default:
				item = new JLabel();
				((JLabel) item).setText(builder.texto);
				break;
		}
		item.setName(builder.nome);
		item.setBorder(builder.borda);
		item.setFont(builder.estilo);
	}
}
