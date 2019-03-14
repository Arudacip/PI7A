package btns;

public enum ButtonTypes 
{
	Start(0), Restart(1), Stop(2);
	private final int valor;
	ButtonTypes(int valorOp)
	{
		valor = valorOp;
	}
	public int getValor()
	{
		return valor;
	}
}
