package views.panels;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import controllers.ControllerMain;
import views.buttons.AbstractButton;
import views.buttons.ButtonTypes;
import views.buttons.RestartButton;
import views.buttons.StartButton;
import views.buttons.StopButton;

/**
 * Classe de painel do design pattern MVC + Compose.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param buttonsList : lista de buttons da interface
 */

public class ComposeControl
{
	
    private ArrayList<AbstractButton> buttonsList = new ArrayList<>();
    
    /**
     * Construtor principal do painel.
     */
    public ComposeControl()
    {
        buttonsList.add(new StartButton());
        buttonsList.add(new StopButton());
        buttonsList.add(new RestartButton());
    }
    
    /**
     * Cria o painel de controle e os botoes, monta a interface e retorna o painel completo.
     * @return Painel de Controle 
     */
    public JPanel criaPanelControl()
    {
        JPanel panelControl = new JPanel();
        panelControl.setName("panelControl");
        panelControl.setLayout(new FlowLayout());
        
        for (AbstractButton btn : buttonsList)
        {
            JButton myBT = (JButton) btn;
            myBT.addActionListener(ControllerMain.getInstance());
            panelControl.add(myBT);
        }
        return panelControl;
    }
    
    /**
     * Retorna o botao de acordo com seu tipo.
     * @param button : tipo de botao
     * @return Ponteiro para o botao de acordo com seu tipo.
     */
    public JButton getButton(ButtonTypes button)
    {
        for (AbstractButton obj : buttonsList)
        {
            if (obj.tipo() == button)
                return (JButton) obj;
        }
        return null;
    }
}
