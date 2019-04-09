package controllers.facades;

import controllers.ControllerMain;

public class FacadeMain
{
	
	// TODO: Javadoc
	
	private ControllerMain controller;
	
	public FacadeMain()
	{
		controller = ControllerMain.getInstance();
	}
	
	public void startAdmin()
	{
		controller.createView();
		controller.createService();
	}
}
