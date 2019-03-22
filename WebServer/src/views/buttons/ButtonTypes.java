package views.buttons;

public enum ButtonTypes
{
    
    /**
     * Classe da ViewItem de ItemButton do design pattern MVC + Compose. 
     *
     * @param valor: valor do tipo de button
     * @method Start: Construtor start button
     * @method Stop: Construtor stop button
     * @method Restart: Construtor restart button
     * @method getValor: retorna valor do button
     */
    
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
