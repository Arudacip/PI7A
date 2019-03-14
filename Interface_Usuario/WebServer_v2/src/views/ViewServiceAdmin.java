package views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.utils.ComponentMapper;
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
    private ArrayList<AbstractLog> infolog, warnlog, critlog, mainlog;

    // Constroi a IA de Administracao do servico
    public ViewServiceAdmin(ArrayList<AbstractLog> infolog, ArrayList<AbstractLog> warnlog,
    		ArrayList<AbstractLog> critlog, ArrayList<AbstractLog> mainlog)
    {
        // Define valores principais do View
        super("Servidor Web v2.0 - ECP7AN-MCA1-09");
        setSize(400, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.infolog = infolog;
        this.warnlog = warnlog;
        this.critlog = critlog;
        this.mainlog = mainlog;
        
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
        
        ComponentMapper.createComponentMap(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        AbstractLog log;
        if (e.getSource() == composeControl.getButton(ButtonTypes.Start))/*Iniciar*/
        {
            log = new LogInfo(new Date(System.currentTimeMillis()), "Tentativa de iniciar...");
            infolog.add(log);
            mainlog.add(log);
            composeLog.AddMessage(log);
            System.out.println("Tamanho info: "+infolog.size());
            System.out.println("Tamanho main: "+mainlog.size());
        } else if (e.getSource() == composeControl.getButton(ButtonTypes.Restart))/*Reiniciar*/
        {
            log = new LogWarn(new Date(System.currentTimeMillis()), "Tentativa de reiniciar...");
            warnlog.add(log);
            mainlog.add(log);
            composeLog.AddMessage(log);
            System.out.println("Tamanho info: "+warnlog.size());
            System.out.println("Tamanho main: "+mainlog.size());
        } else if (e.getSource() == composeControl.getButton(ButtonTypes.Stop))/*Parar*/
        {
            log = new LogCrit(new Date(System.currentTimeMillis()), "Tentativa de parar...");
            critlog.add(log);
            mainlog.add(log);
            composeLog.AddMessage(log);
            System.out.println("Tamanho info: "+critlog.size());
            System.out.println("Tamanho main: "+mainlog.size());
        }
    }
}
