package models;

public class ResultTable
{
	private String tipo;
	private int valor = -1;
	
	public ResultTable()
	{
	}
	
	public ResultTable(String tipo, int valor)
	{
		this.tipo = tipo;
		this.valor = valor;
	}
	
	public String getTipo()
	{
		return tipo;
	}
	
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}
	
	public int getValor()
	{
		return valor;
	}
	
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
