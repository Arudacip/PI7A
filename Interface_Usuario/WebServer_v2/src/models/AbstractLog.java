package models;

import java.util.Date;

public abstract class AbstractLog
{
    
    /**
     * Classe do Model raiz de FactoryItem do design pattern MVC + Abstract Factory.
     *
     * @param data: data do log
     * @param texto: texto do log
     * @method imprime: abstrato que retorna o texto do log
     * @method getData: retorna a data
     * @method getText: retorna o texto
     * @method setParameters: seta os valores do log
     */

    private Date data;
    private String texto;

    public abstract String imprime();

    public Date getData()
    {
        return data;
    }

    public String getText()
    {
        return texto;
    }

    public void setParameters(Date data, String texto)
    {
        this.data = data;
        this.texto = texto;
    }
}
