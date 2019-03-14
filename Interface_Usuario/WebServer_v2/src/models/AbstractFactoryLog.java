package models;

import java.util.Date;

public abstract class AbstractFactoryLog
{
    
    /**
     * Classe do Model raiz da Factory do design pattern MVC + Abstract Factory.
     *
     * @method retornaLogs: abstrato que retorna o texto dos logs armazenados
     */
    
    public abstract AbstractLog retornaLogs(Date data, String text);
}
