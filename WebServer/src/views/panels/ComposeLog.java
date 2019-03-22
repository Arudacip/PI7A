package views.panels;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import models.AbstractLog;

public class ComposeLog
{
    
    /**
     * Classe do ViewItem de painel do design pattern MVC + Compose.
     *
     * @param txtLog: area de texto para logs exibidos
     * @method criaPanelLog: cria o painel de logs para o View
     * @method AddMessage: altera os logs exibidos
     */
    
    private JTextArea txtLog;

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
        JScrollPane scrollLog = new JScrollPane(txtLog);
        panelLog.setLayout(new BorderLayout());
        panelLog.add(lbTitle, BorderLayout.NORTH);
        panelLog.add(scrollLog, BorderLayout.CENTER);

        return panelLog;
    }

    public void AddMessage(AbstractLog message)
    {
        txtLog.setText(txtLog.getText() + "\n" + message.imprime());
    }
}
