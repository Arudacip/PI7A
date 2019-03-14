package models;

import java.util.Date;

public class LogWarn extends AbstractLog
{
    
    /**
     * Classe do Model de LogWarn do design pattern MVC + Abstract Factory.
     *
     * @param prefix: prefixo do log
     * @method imprime: retorna o texto do log
     */

    private String prefix = "WARNING: ";
    
    public LogWarn(Date data, String texto)
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
