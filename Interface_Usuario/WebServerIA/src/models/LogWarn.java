package models;

public class LogWarn extends AbstractLog
{
	private String prefix = "WARNING: ";
	
	@Override
	public String imprime()
	{
		String message = getData().toString() + prefix + getText();
		return message;
	}

}
