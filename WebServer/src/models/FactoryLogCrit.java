package models;

import java.util.Date;

public class FactoryLogCrit extends AbstractFactoryLog
{
    
    /**
     * Classe do Model FactoryLogCrit do design pattern MVC + Abstract Factory.
     *
     * @method retornaLogs: retorna o texto dos logs armazenados
     */

    @Override
    public AbstractLog retornaLogs(Date data, String text)
    {
        AbstractLog logline = new LogCrit(data, text);
        return logline;
    }

}
