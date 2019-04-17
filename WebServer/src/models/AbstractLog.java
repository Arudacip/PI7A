package models;

import java.util.Date;

/**
 * Classe do Model raiz de FactoryItem do design pattern MVC + Abstract Factory.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param data: data do log
 * @param texto: texto do log
 */

public abstract class AbstractLog
{
	
    private Date data;
    private String texto;
	
	/**
	 * Construtor principal do AbstractLog que recebe os valores do log.
	 * @param data : data do log
	 * @param texto : texto do log
	 */
    public AbstractLog(Date data, String texto)
    {
        this.data = data;
        this.texto = texto;
    }
    
    /**
     * Metodo abstrato que retorna o texto do log.
     * @return Texto do log
     */
    public abstract String imprime();
    
    /**
     * Retorna a data do log.
     * @return Data do log
     */
    public Date getData()
    {
        return data;
    }
    
    /**
     * Retorna o texto do log.
     * @return Texto do log
     */
    public String getText()
    {
        return texto;
    }
}
