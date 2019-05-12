package models;

import java.util.Date;

/**
 * Classe do Model de LogAcc do design pattern MVC + Abstract.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param prefix : prefixo do log
 */

public class LogAcc extends AbstractLog
{
	
	private static final long serialVersionUID = 1L;
    private String prefix = "> ACCESS: ";
    
    /**
     * Construtor principal do LogAcc que recebe os valores do log.
	 * @param data : data do log
	 * @param texto : texto do log
     */
    public LogAcc(Date data, String texto)
    {
		super(data, texto);
	}
    
    @Override
    public String imprime()
    {
        String message = getData().toString() + prefix + getText();
        return message;
    }
    
    /**
     * Separa e retorna o arquivo procurado.
     * @return arquivo procurado
     */
    public String getArquivo()
    {
    	String texto[] = getText().split("#");
    	String arquivo = texto[0];
    	return arquivo;
    }
    
    /**
     * Separa e retorna o metodo HTTP usado.
     * @return metodo usado
     */
    public String getMethod()
    {
    	String texto[] = getText().split("#");
    	String method = texto[1];
    	return method;
    }
    
    /**
     * Separa e retorna o ip do cliente.
     * @return ip do cliente
     */
    public String getIP()
    {
    	String texto[] = getText().split("#");
    	String ip = texto[2];
    	return ip;
    }
    
    /**
     * Separa e retorna o codigo de retorno da requisicao.
     * @return codigo de retorno
     */
    public String getCode()
    {
    	String texto[] = getText().split("#");
    	String code = texto[3];
    	return code;
    }
    
}
