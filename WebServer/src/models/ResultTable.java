package models;

/**
 * Classe java bean utilizada para parsing de dados recuperados do banco de dados.
 * 
 * @param tipo : tipo do dado.
 * @param valor : valor do dado.
 */

public class ResultTable
{
	
	private String tipo;
	private int valor = -1;
	
	/**
	 * Construtor padrao
	 */
	public ResultTable()
	{
	}
	
	/**
	 * Construtor de valores fornecidos
	 * @param tipo : tipo do dado.
	 * @param valor : valor do dado.
	 */
	public ResultTable(String tipo, int valor)
	{
		this.tipo = tipo;
		this.valor = valor;
	}
	
	/**
	 * Retorna o tipo de dado armazenado
	 * @return tipo de dado.
	 */
	public String getTipo()
	{
		return tipo;
	}
	
	/**
	 * Define o tipo de dado armazenado
	 * @param tipo de dado
	 */
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}
	
	/**
	 * Retorna o valor do dado armazenado
	 * @return valor do dado.
	 */
	public int getValor()
	{
		return valor;
	}
	
	/**
	 * Define o valor do dado armazenado
	 * @param valor do dado
	 */
	public void setValor(int valor)
	{
		this.valor = valor;
	}
	
	@Override
	public String toString()
	{
		return "["+this.getTipo()+"/"+this.getValor()+"]";
	}
}
