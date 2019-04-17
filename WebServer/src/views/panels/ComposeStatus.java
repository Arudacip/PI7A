package views.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe de painel do design pattern MVC + Compose.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param lbStatus: area de texto para logs exibidos
 */

public class ComposeStatus
{

    private JLabel lbStatus;
    
    /**
     * Cria o painel de status para o View.
     * @return Painel de Status
     */
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
    
    /**
     * Altera o status exibido do servidor.
     * @param status : status a exibir
     */
    public void setStatus(String status)
    {
        lbStatus.setText("Status: " + status);
    }
}
