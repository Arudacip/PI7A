package views.buttons;
import javax.swing.JButton;

/**
 * Classe de AbstractButton do design pattern MVC + Compose. 
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 */

public abstract class AbstractButton extends JButton
{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Metodo abstrato que retorna os tipos de botao.
     * @return Tipos de botao
     */
    public abstract ButtonTypes tipo();
}
