package controllers.facades;

import controllers.ControllerMain;

/**
 * Classe em design pattern Facade, para comunicacao simplificada entre a execucao principal e o controller.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 * 
 * @param controller : ponteiro para o Controller do servidor
 */

public class FacadeMain
{
	// VARIAVEIS DE AMBIENTE
	private ControllerMain controller;
	
	/**
	 * Construtor unico do Facade.
	 */
	public FacadeMain()
	{
		controller = ControllerMain.getInstance();
	}
	
	/**
	 * Inicia a interface administrativa do servidor.
	 */
	public void startAdmin()
	{
		controller.createView();
		controller.createService();
	}
}
