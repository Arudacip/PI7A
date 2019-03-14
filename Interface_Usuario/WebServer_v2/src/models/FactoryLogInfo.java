package models;

import java.util.Date;

public class FactoryLogInfo extends AbstractFactoryLog
{
    
    /**
     * Classe do Model FactoryLogInfo do design pattern MVC + Abstract Factory.
     *
     * @method retornaLogs: retorna o texto dos logs armazenados
     */
    
    @Override
    public AbstractLog retornaLogs(Date data, String text)
    {
        AbstractLog logline = new LogInfo(data, text);
        return logline;
    }

}
