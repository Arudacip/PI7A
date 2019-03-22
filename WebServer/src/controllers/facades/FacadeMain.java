package controllers.facades;

import controllers.ControllerMain;

public class FacadeMain
{
	
	// TODO: Javadoc
	
	private ControllerMain controller;
	
	public FacadeMain()
	{
		controller = new ControllerMain();
	}
	
	public void startAdmin()
	{
		controller.createService();
		controller.createView();
	}
}
