package views;

import controllers.Main;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import models.AbstractLog;
import models.LogCrit;
import models.LogInfo;
import models.LogWarn;
import views.buttons.ButtonTypes;
import views.panels.ComposeControl;
import views.panels.ComposeLog;
import views.panels.ComposeStatus;

public class ViewServiceAdmin extends JFrame implements ActionListener
{

    /**
     * Classe da View de ServiceAdmin do design pattern MVC.
     *
     * @param composeStatus: label de status do servico
     * @param composeLog: area de texto para viazualizacao dos logs
     * @param composeControl: botao de Start
     * @param componentMap: mapa de componentes da view
     */
    
    private static final long serialVersionUID = 1L;
    private ComposeStatus composeStatus = new ComposeStatus();
    private ComposeLog composeLog = new ComposeLog();
    private ComposeControl composeControl = new ComposeControl();
    private HashMap componentMap;
    //private ControllerMain controller;
    //private ArrayList<AbstractLog> mainlog;

    // Constroi a IA de Administracao do servico
    public ViewServiceAdmin()
    {
        // Define valores principais do View
        super("Servidor Web v2.0 - ECP7AN-MCA1-09");
        //this.controller = controller;
        //this.mainlog = mainlog;
        setSize(400, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Compoe paineis e monta a interface
        JPanel panelStatus = composeStatus.criaPanelStatus();
        JPanel panelLog = composeLog.criaPanelLog();
        JPanel panelControl = composeControl.criaPanelControl(this);
        panelStatus.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelLog.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelControl.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        getContentPane().add(panelStatus, BorderLayout.NORTH);
        getContentPane().add(panelLog, BorderLayout.CENTER);
        getContentPane().add(panelControl, BorderLayout.SOUTH);
        
        createComponentMap();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        AbstractLog log;
        if (e.getSource() == composeControl.getButton(ButtonTypes.Start))/*Iniciar*/
        {
            log = new LogInfo(new Date(System.currentTimeMillis()), "Tentativa de iniciar...");
            composeLog.AddMessage(log);
        } else if (e.getSource() == composeControl.getButton(ButtonTypes.Restart))/*Reiniciar*/
        {
            log = new LogWarn(new Date(System.currentTimeMillis()), "Tentativa de reiniciar...");
            composeLog.AddMessage(log);
        } else if (e.getSource() == composeControl.getButton(ButtonTypes.Stop))/*Parar*/
        {
            log = new LogCrit(new Date(System.currentTimeMillis()), "Tentativa de parar...");
            composeLog.AddMessage(log);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void createComponentMap()
    {
        componentMap = new HashMap<String,Component>();
        Component[] components = getContentPane().getComponents();
        for (int i=0; i < components.length; i++)
        {
            componentMap.put(components[i].getName(), components[i]);
        }
    }
    
    public Component getComponentByName(String name)
    {
        if (componentMap.containsKey(name))
        {
            return (Component) componentMap.get(name);
        }
        else return null;
    }
}
