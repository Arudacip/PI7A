package models;

import java.util.Date;

public class FactoryLogCrit extends AbstractFactoryLog
{
	
	@Override
	public AbstractLog retornaLogs(Date data, String text) {
		AbstractLog logline = new LogCrit();
		logline.setParameters(data, text);
		return logline;
	}
	
}
