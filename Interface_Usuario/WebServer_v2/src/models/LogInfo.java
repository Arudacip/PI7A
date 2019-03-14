package models;

import java.util.Date;

public class LogInfo extends AbstractLog
{

    /**
     * Classe do Model de LogInfo do design pattern MVC + Abstract Factory.
     *
     * @param prefix: prefixo do log
     * @method imprime: retorna o texto do log
     */
    
    private String prefix = "INFO: ";
    
    public LogInfo(Date data, String texto)
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
