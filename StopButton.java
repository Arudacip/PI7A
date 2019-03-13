package views.buttons;

public class StopButton extends AbstractButton
{
    
    /**
     * Classe do ViewItem de StopButton do design pattern MVC + Compose. 
     *
     * @param tipo: tipo de button
     * @param serialVersionUID: serializable
     * @method StopButton: construtor do button de start
     * @method tipo: retorna o tipo de botao
     */

    private static final long serialVersionUID = 3L;
    ButtonTypes tipo = ButtonTypes.Stop;

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
