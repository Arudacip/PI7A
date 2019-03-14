package models;

public class LogInfo extends AbstractLog
{
	private String prefix = "INFO: ";
	
	@Override
	public String imprime()
	{
		String message = getData().toString() + prefix + getText();
		return message;
	}

}
