package controllers;

import models.AbstractLog;
import views.FrameServiceAdmin;

public class Main {
	
	// Models
	private AbstractLog infolog, warnlog, critlog;
		
	// Views
	static FrameServiceAdmin serviceAdmin;
	
	public static void main(String args[])
	{
		serviceAdmin = new FrameServiceAdmin();
		serviceAdmin.setVisible(true);
    }
}
