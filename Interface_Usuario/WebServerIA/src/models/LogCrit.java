package models;

public class LogCrit extends AbstractLog
{
	private String prefix = "CRITICAL: ";
	
	@Override
	public String imprime()
	{
		String message = getData().toString() + prefix + getText();
		return message;
	}

}
