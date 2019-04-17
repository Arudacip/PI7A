package views.buttons;

/**
 * Classe de StopButton do design pattern MVC + Compose.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param tipo: tipo de button
 */

public class StopButton extends AbstractButton
{
    
    private static final long serialVersionUID = 3L;
    ButtonTypes tipo = ButtonTypes.Stop;
    
    /**
     * Construtor do button de stop.
     */
    public StopButton()
    {
        this.setName("btStop");
        this.setText("Parar");
    }

    @Override
    public ButtonTypes tipo()
    {
        return this.tipo;
    }

}
