package models;

import java.util.Date;

public class LogCrit extends AbstractLog
{
    
    /**
     * Classe do Model de LogCrit do design pattern MVC + Abstract Factory.
     *
     * @param prefix: prefixo do log
     * @method imprime: retorna o texto do log
     */
    
    private String prefix = "> CRITICAL: ";
    
    public LogCrit(Date data, String texto)
    {
		super(data, texto);
	}
    
    @Override
    public String imprime()
    {
        String message = getData().toString() + prefix + getText();
        return message;
    }

}
