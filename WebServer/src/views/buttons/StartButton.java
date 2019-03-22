package views.buttons;

public class StartButton extends AbstractButton
{
    
    /**
     * Classe do ViewItem de StartButton do design pattern MVC + Compose. 
     *
     * @param tipo: tipo de button
     * @param serialVersionUID: serializable
     * @method StartButton: construtor do button de start
     * @method tipo: retorna o tipo de botao
     */

    private static final long serialVersionUID = 1L;
    public ButtonTypes tipo = ButtonTypes.Start;
    
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
