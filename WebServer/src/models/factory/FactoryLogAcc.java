package models.factory;

import java.util.Date;

import models.AbstractLog;
import models.LogAcc;
import models.factory.AbstractFactoryLog;

/**
 * Classe do Model FactoryLogAcc do design pattern MVC + Abstract Factory.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 */

public class FactoryLogAcc extends AbstractFactoryLog
{
    
    @Override
    public AbstractLog retornaLogs(Date data, String text)
    {
        AbstractLog logline = new LogAcc(data, text);
        return logline;
    }

}
