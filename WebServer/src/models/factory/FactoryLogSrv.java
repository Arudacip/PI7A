package models.factory;

import java.util.Date;

import models.AbstractLog;
import models.LogSrv;
import models.factory.AbstractFactoryLog;

/**
 * Classe do Model FactoryLogSrv do design pattern MVC + Abstract Factory.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 */

public class FactoryLogSrv extends AbstractFactoryLog
{
    
    @Override
    public AbstractLog retornaLogs(Date data, String text)
    {
        AbstractLog logline = new LogSrv(data, text);
        return logline;
    }

}
