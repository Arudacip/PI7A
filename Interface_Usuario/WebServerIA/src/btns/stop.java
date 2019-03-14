package btns;

public class stop extends ArudacipButton
{
	ButtonTypes type = ButtonTypes.Stop;
	/**
	 * Esse botão será o botão de parada.
	 */
	private static final long serialVersionUID = 3L;
	public stop()
	{
		this.setName("Parar");
		this.setText("Parar");
	}
	@Override
	public ButtonTypes type() {
		return this.type;
	}

}