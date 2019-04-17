package views.buttons;

/**
 * Classe de RestartButton do design pattern MVC + Compose.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param tipo: tipo de button
 */

public class RestartButton extends AbstractButton
{
    
    private static final long serialVersionUID = 2L;
    public ButtonTypes tipo = ButtonTypes.Restart;
    
    /**
     * Construtor do button de restart.
     */
    public RestartButton()
    {
        this.setName("btRestart");
        this.setText("Reiniciar");
    }
    
    @Override
    public ButtonTypes tipo()
    {
        return this.tipo;
    }

}
