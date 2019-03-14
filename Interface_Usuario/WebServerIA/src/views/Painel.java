package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Esse e o Componente de TODOS os JPanel das Views do Pattern MVC
 */

public class Painel extends JPanel
{
	
	public static final int LTBORDER = 1;
	public static final int LTFLOW = 2;
	public static final int PANELSTATUS = 1;
	public static final int PANELLOG = 2;
	public static final int PANELBUTTON = 3;
	public static final int EMPTYBORDER = 1;
	public static final int ETCHEDBORDER1 = 2;
	public static final int ETCHEDBORDER2 = 3;
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private JPanel painel;
	
	/**
	 * Esse e o Builder de TODOS os JPanel das Views do Pattern MVC
	 */

	public static class BuildPanel
	{
		
		// Atributos requeridos
		private JPanel painel;
		private LayoutManager layout;
		private Border borda;
		private JFrame ui;
		
		public BuildPanel(JPanel painel, JFrame ui)
		{
			this.painel = painel;
			this.ui = ui;
		}
		
		public BuildPanel layout(int tipo)
		{
			switch (tipo)
			{
				case 1:
					this.layout = new BorderLayout();
					break;
				case 2:
					this.layout = new FlowLayout();
					break;
				default:
					this.layout = new BorderLayout();
					break;
			}
			painel.setLayout(layout);
			return this;
		}
		
		public BuildPanel borda(int tipo, int top, int left, int bottom, int right)
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
			painel.setBorder(borda);
			return this;
		}
		
		public BuildPanel itens(int tipo)
		{
			JComponent item;
			
			switch (tipo)
			{
				/*
			 	 *Painel de Status
			 	*/
				case 1:
					item = new Item.BuildItem("lbTitulo", 1)
									.texto("Servidor Web - ECP7AN-MCA1-09")
									.estilo(Item.TITLEBOLD1)
									.borda(Item.EMPTYBORDER, 0, 0, 5, 0)
									.build();
					painel.add(item, BorderLayout.CENTER);
					item = new Item.BuildItem("lbStatus", 1)
									.texto("Status: ---")
									.build();
					painel.add(item, BorderLayout.SOUTH);
					break;
					
				/*
				 *Painel de Log
				*/
				case 2:
					item = new Item.BuildItem("lbUltimos", 1)
									.texto("Ultimos eventos:")
									.build();
					painel.add(item, BorderLayout.NORTH);
					item = new Item.BuildItem("txtLog", 3) // Ver tamanho
									.editavel(false)
									.borda(Item.ETCHEDBORDER1, 0, 0, 0, 0)
									.build();
					painel.add(item, BorderLayout.CENTER);
					break;
					
				/*
				 *Painel de Botoes
				*/
				case 3:
					item = new Item.BuildItem("btStart", 2)
									.texto("Iniciar")
									.listener(ui)
									.build();
					painel.add(item);
					item = new Item.BuildItem("btStop", 2)
									.texto("Parar")
									.listener(ui)
									.build();
					painel.add(item);
					item = new Item.BuildItem("btRestart", 2)
									.texto("Reiniciar")
									.listener(ui)
									.build();
					painel.add(item);
					break;
					
				/*
				 * Painel de Status
				*/
				default:
					item = new Item.BuildItem("lbTitulo", 1)
									.texto("Servidor Web - ECP7AN-MCA1-09")
									.estilo(Item.TITLEBOLD1)
									.borda(Item.EMPTYBORDER, 0, 0, 5, 0)
									.build();
					painel.add(item, BorderLayout.CENTER);
					item = new Item.BuildItem("lbStatus", 1)
									.texto("Status: ---")
									.build();
					painel.add(item, BorderLayout.SOUTH);
					break;
			}
			return this;
		}
		
		public Painel build()
		{
			return new Painel(this);
		}
	}
	
	private Painel(BuildPanel builder)
	{
		// Monta o painel do View
		painel = builder.painel;
	}

}
