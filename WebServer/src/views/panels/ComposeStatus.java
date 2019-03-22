package views.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ComposeStatus
{

    /**
     * Classe do ViewItem de painel do design pattern MVC + Compose.
     *
     * @param lbStatus: area de texto para logs exibidos
     * @method criaPanelStatus: cria o painel de status para o View
     * @method setStatus: altera o status exibido do servidor
     */
    private JLabel lbStatus;

    public JPanel criaPanelStatus()
    {
        JPanel panelStatus = new JPanel();

        // instanciar os objetos de tela
        JLabel lbTitle = new JLabel("Servidor Web - ECP7AN-MCA1-09");
        lbTitle.setFont(new Font("Serif", Font.BOLD, 20));
        lbTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        lbStatus = new JLabel("Status: ---");
        lbStatus.setName("lbStatus");
        
        // acrescenta no frame os objetos
        panelStatus.setLayout(new BorderLayout());
        panelStatus.add(lbTitle, BorderLayout.CENTER);
        panelStatus.add(lbStatus, BorderLayout.SOUTH);

        return panelStatus;
    }

    public void setStatus(String status)
    {
        lbStatus.setText("Status: " + status);
    }
}
