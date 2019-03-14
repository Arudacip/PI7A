package models;

import java.util.Date;

public class FactoryLogInfo extends AbstractFactoryLog
{
	
	@Override
	public AbstractLog retornaLogs(Date data, String text) {
		AbstractLog logline = new LogInfo();
		logline.setParameters(data, text);
		return logline;
	}
	
}
