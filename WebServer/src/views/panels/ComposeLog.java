package views.panels;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import models.AbstractLog;

/**
 * Classe de painel do design pattern MVC + Compose.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param txtLog: area de texto para logs exibidos
 * @param scrollLog: scroll para a area de texto
 */

public class ComposeLog
{
    
    private JTextArea txtLog;
    private JScrollPane scrollLog;
    
    /**
     * Cria o painel de logs para o View.
     * @return O painel de Logs
     */
    public JPanel criaPanelLog()
    {
        JPanel panelLog = new JPanel();
        panelLog.setName("panelLog");

        // instanciar os objetos de tela
        JLabel lbTitle = new JLabel("Ultimos eventos:");
        txtLog = new JTextArea(6, 30);
        txtLog.setName("txtLog");
        txtLog.setEditable(false);
        txtLog.setFont(new Font("Logger", Font.PLAIN, 10));;
        txtLog.setBorder(BorderFactory.createEtchedBorder());
        scrollLog = new JScrollPane(txtLog);
        panelLog.setLayout(new BorderLayout());
        panelLog.add(lbTitle, BorderLayout.NORTH);
        panelLog.add(scrollLog, BorderLayout.CENTER);

        return panelLog;
    }
    
    /**
     * Altera os logs exibidos na View.
     * 
     * @param message : AbstractLog a ser acrescentado
     */
    public void AddMessage(AbstractLog message)
    {
        txtLog.setText(txtLog.getText() + "\n" + message.imprime());
        JScrollBar sb = scrollLog.getVerticalScrollBar();
        sb.setValue(sb.getMaximum());
    }
}
