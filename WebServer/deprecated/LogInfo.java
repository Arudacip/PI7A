package models;

import java.util.Date;

/**
 * Classe do Model de LogInfo do design pattern MVC + Abstract Factory.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param prefix: prefixo do log
 */

public class LogInfo extends AbstractLog
{
	
	private static final long serialVersionUID = 1L;
    private String prefix = "> INFO: ";
    
    /**
     * Construtor principal do LogInfo que recebe os valores do log.
	 * @param data : data do log
	 * @param texto : texto do log
     */
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
