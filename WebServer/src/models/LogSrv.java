package models;

import java.util.Date;

import models.AbstractLog;

/**
 * Classe do Model de LogSrv do design pattern MVC + Abstract.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param prefix : prefixo do log
 */

public class LogSrv extends AbstractLog
{
	
	private static final long serialVersionUID = 1L;
    private String prefix = "> SERVER: ";
    
    /**
     * Construtor principal do LogSrv que recebe os valores do log.
	 * @param data : data do log
	 * @param texto : texto do log
     */
    public LogSrv(Date data, String texto)
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
