package views.buttons;

/**
 * Classe de ItemButton do design pattern MVC + Compose.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param valor : valor do tipo de button
 * @param Start : Construtor start button
 * @param Stop : Construtor stop button
 * @param Restart : Construtor restart button
 */

public enum ButtonTypes
{
    
    Start(0), Restart(1), Stop(2);
    private final int valor;

    ButtonTypes(int valorOp)
    {
        valor = valorOp;
    }
    
    /**
     * Retorna valor do button.
     * @return valor do button
     */
    public int getValor()
    {
        return valor;
    }
}
