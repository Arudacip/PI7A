package models;

import java.util.Date;

public abstract class AbstractFactoryLog
{
	public abstract AbstractLog retornaLogs(Date data, String text);
}
