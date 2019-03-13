package views.panels;

import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import views.ViewServiceAdmin;
import views.buttons.AbstractButton;
import views.buttons.ButtonTypes;
import views.buttons.RestartButton;
import views.buttons.StartButton;
import views.buttons.StopButton;

public class ComposeControl
{

    /**
     * Classe do ViewItem de painel do design pattern MVC + Compose.
     *
     * @param buttonsList: lista de buttons da solucao
     * @method ComposeControl: construtor dos buttons
     * @method criaPanelControl: cria o painel de controle para o View
     * @method getButton: retorna o button por tipo
     */
    
    private ArrayList<AbstractButton> buttonsList = new ArrayList<>();

    public ComposeControl()
    {
        buttonsList.add(new StartButton());
        buttonsList.add(new StopButton());
        buttonsList.add(new RestartButton());
    }

    public JPanel criaPanelControl(ViewServiceAdmin ui)
    {
        JPanel panelControl = new JPanel();
        panelControl.setName("panelControl");
        panelControl.setLayout(new FlowLayout());
        
        for (AbstractButton btn : buttonsList)
        {
            JButton myBT = (JButton) btn;
            myBT.addActionListener(ui);
            panelControl.add(myBT);
        }
        return panelControl;
    }

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
