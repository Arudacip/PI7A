package models;

import java.util.Date;

public abstract class AbstractLog
{
	private Date data;
	private String text;
	
	public abstract String imprime();

	public Date getData()
	{
		return data;
	}

	public String getText()
	{
		return text;
	}

	public void setParameters(Date data, String text)
	{
		this.data = data;
		this.text = text;
	}
}
