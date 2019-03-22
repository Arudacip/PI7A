package views.buttons;
import javax.swing.JButton;

public abstract class AbstractButton extends JButton
{
    
    /**
     * Classe do ViewItem de AbstractButton do design pattern MVC + Compose. 
     *
     * @param serialVersionUID: serializable
     * @method type: abstrato que retorna os tipos de botao
     */
    
    private static final long serialVersionUID = 1L;
    
    public abstract ButtonTypes tipo();
}
