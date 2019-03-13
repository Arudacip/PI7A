package models;

import java.util.Date;

public class FactoryLogWarn extends AbstractFactoryLog
{
    
    /**
     * Classe do Model FactoryLogWarn do design pattern MVC + Abstract Factory.
     *
     * @method retornaLogs: retorna o texto dos logs armazenados
     */
    
    @Override
    public AbstractLog retornaLogs(Date data, String text)
    {
        AbstractLog logline = new LogWarn();
        logline.setParameters(data, text);
        return logline;
    }

}
