package btns;



public class start extends ArudacipButton
{
	private static final long serialVersionUID = 1L;
	public ButtonTypes type = ButtonTypes.Start;
	/**
	 * Esse botão será o botão de inicio.
	 */
	public start()
	{
		this.setName("Iniciar");
		this.setText("Iniciar");
	}
	@Override
	public ButtonTypes type() {
		return this.type;
	}

}
