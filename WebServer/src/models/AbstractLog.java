package models;

import java.util.Date;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Classe do Model raiz de FactoryItem do design pattern MVC + Abstract.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param id : id do log
 * @param data : data do log
 * @param texto : texto do log
 */

public abstract class AbstractLog implements Serializable, Comparable<AbstractLog>
{
	
	private static final long serialVersionUID = 1L;
	private int id;
    private Timestamp data;
    private String texto;
	
	/**
	 * Construtor principal do AbstractLog que recebe os valores do log.
	 * @param data : data do log
	 * @param texto : texto do log
	 */
    public AbstractLog(Date data, String texto)
    {
    	this.id = 0;
        this.data = new Timestamp(data.getTime());
        this.texto = texto;
    }
    
    /**
     * Metodo abstrato que retorna o texto do log.
     * @return Texto do log
     */
    public abstract String imprime();
    
    /**
     * Retorna o ID do log.
     * @return ID do log
     */
    public int getID()
    {
        return id;
    }
    
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
    
    @Override
    public int compareTo(AbstractLog o)
    {
    	if (getData() == null || o.getData() == null)
    	{
    		return 0;
    	}
    	return getData().compareTo(o.getData());
    }
    
    @Override
    public String toString()
    {
    	return imprime();
    }
}
