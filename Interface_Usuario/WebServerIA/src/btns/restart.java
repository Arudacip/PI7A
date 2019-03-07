package btns;

public class restart extends ArudacipButton
{
	public ButtonTypes type = ButtonTypes.Restart;
	/**
	 * Esse botão será o botão de reinicio.
	 */
	private static final long serialVersionUID = 2L;
	public restart()
	{
		this.setName("Reiniciar");
		this.setText("Reiniciar");
	}
	@Override
	public ButtonTypes type() {
		return this.type;
	}

}