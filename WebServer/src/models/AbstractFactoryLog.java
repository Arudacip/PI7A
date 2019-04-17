package models;

import java.util.Date;

/**
 * Classe do Model raiz da Factory do design pattern MVC + Abstract Factory.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 */

public abstract class AbstractFactoryLog
{
    
    /**
     * Metodo abstrato que retorna os objetos dos logs armazenados.
     * @param data : dia do log
     * @param text : conteudo do log
     * @return objeto do AbstractLog
     */
    public abstract AbstractLog retornaLogs(Date data, String text);
}
