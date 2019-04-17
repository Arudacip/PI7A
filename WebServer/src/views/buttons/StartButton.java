package views.buttons;

/**
 * Classe de StartButton do design pattern MVC + Compose.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param tipo: tipo de button
 */

public class StartButton extends AbstractButton
{
    
    private static final long serialVersionUID = 1L;
    public ButtonTypes tipo = ButtonTypes.Start;
    
    /**
     * Construtor do button de start.
     */
    public StartButton()
    {
        this.setName("btStart");
        this.setText("Iniciar");
    }

    @Override
    public ButtonTypes tipo()
    {
        return this.tipo;
    }

}
