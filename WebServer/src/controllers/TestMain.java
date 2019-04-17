package controllers;

import controllers.facades.FacadeMain;

/**
 * <h2>Projeto de PI - Servidor Web</h2>
 * Esta e a classe de execucao do Controller principal do servidor. Serve apenas para chamada do servidor.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 * @version 4.1
 */

public class TestMain
{
	
	/**
     * Inicia o servidor e sua interface administrativa.
     * 
	 * @param args : default do Java
	 */
	public static void main(String args[])
	{
		FacadeMain main = new FacadeMain();
		main.startAdmin();
	}
}
