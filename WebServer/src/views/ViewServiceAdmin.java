package views;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.ControllerMain;
import controllers.utils.ComponentMapper;
import models.AbstractLog;
import views.panels.ComposeControl;
import views.panels.ComposeLog;
import views.panels.ComposeStatus;

public class ViewServiceAdmin extends JFrame
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
    private ControllerMain controller = null;
    
    // Constantes
    private static final String ISSTARTED = "Ativo";
    private static final String ISSTOPPED = "Inativo";
    private static final String ISUNKOWN = "---";
    public static final int STARTED = 1;
    public static final int STOPPED = 2;
    public static final int UNKOWN = 3;

    // Constroi a IA de Administracao do servico
    public ViewServiceAdmin(ControllerMain controller)
    {
        // Define valores principais do View
        super("Servidor Web v2.0 - ECP7AN-MCA1-09");
        setSize(400, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.controller = controller;
        
        // Compoe paineis e monta a interface
        JPanel panelStatus = composeStatus.criaPanelStatus();
        JPanel panelLog = composeLog.criaPanelLog();
        JPanel panelControl = composeControl.criaPanelControl(controller);
        panelStatus.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelLog.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelControl.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        getContentPane().add(panelStatus, BorderLayout.NORTH);
        getContentPane().add(panelLog, BorderLayout.CENTER);
        getContentPane().add(panelControl, BorderLayout.SOUTH);
        
        ComponentMapper.createComponentMap(this);
    }
    
    public void setStatus(int status)
    {
    	switch(status)
    	{
    	case STARTED:
    		composeStatus.setStatus(ISSTARTED);
    		break;
    	case STOPPED:
    		composeStatus.setStatus(ISSTOPPED);
    		break;
    	case UNKOWN:
    		composeStatus.setStatus(ISUNKOWN);
    		break;
    	default:
    		composeStatus.setStatus(ISUNKOWN);
    		break;
    	}
    }
    
    public void addLog(AbstractLog log)
    {
    	composeLog.AddMessage(log);
    }
    
    public ComposeControl getIteractions()
    {
    	return composeControl;
    }
}
