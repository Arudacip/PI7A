package models;

import java.util.Date;

public class FactoryLogWarn extends AbstractFactoryLog
{
	
	@Override
	public AbstractLog retornaLogs(Date data, String text) {
		AbstractLog logline = new LogWarn();
		logline.setParameters(data, text);
		return logline;
	}
	
}
