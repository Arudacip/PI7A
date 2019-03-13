package views.buttons;

public class RestartButton extends AbstractButton
{
    
    /**
     * Classe do ViewItem de RestartButton do design pattern MVC + Compose. 
     *
     * @param tipo: tipo de button
     * @param serialVersionUID: serializable
     * @method RestartButton: construtor do button de restart
     * @method tipo: retorna o tipo de botao
     */
    
    private static final long serialVersionUID = 2L;
    public ButtonTypes tipo = ButtonTypes.Restart;

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
