package views;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.ControllerMain;
import models.AbstractLog;
import models.utils.ComponentMapper;
import views.panels.ComposeControl;
import views.panels.ComposeLog;
import views.panels.ComposeStatus;

/**
 * Classe da View de ServiceAdmin, no design pattern MVC.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param ISSTARTED : [CONSTANT] status ativo
 * @param ISSTOPPED : [CONSTANT] status parado
 * @param ISUNKOWN : [CONSTANT] status desconhecido
 * @param composeStatus : painel de status do servico
 * @param composeLog : painel de visualizacao dos logs
 * @param composeControl : painel de controle da interface
 */

public class ViewServiceAdmin extends JFrame
{

    private static final long serialVersionUID = 1L;
    private static final String ISSTARTED = "Ativo";
    private static final String ISSTOPPED = "Inativo";
    private static final String ISUNKOWN = "---";
    private ComposeStatus composeStatus = new ComposeStatus();
    private ComposeLog composeLog = new ComposeLog();
    private ComposeControl composeControl = new ComposeControl();

    /**
     * Constroi a View de Administracao do servico.
     * @param VERSION : versao do ServerWeb 
     */
    public ViewServiceAdmin(String VERSION)
    {
        // Define valores principais do View
        super("Servidor Web v"+VERSION+" - ECP7AN-MCA1-09");
        setSize(400, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Compoe paineis e monta a interface
        JPanel panelStatus = composeStatus.criaPanelStatus();
        JPanel panelLog = composeLog.criaPanelLog();
        JPanel panelControl = composeControl.criaPanelControl();
        panelStatus.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelLog.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelControl.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        getContentPane().add(panelStatus, BorderLayout.NORTH);
        getContentPane().add(panelLog, BorderLayout.CENTER);
        getContentPane().add(panelControl, BorderLayout.SOUTH);
        
        ComponentMapper.createComponentMap(this);
    }
    
    /**
     * Define a exibicao de status do servidor na interface administrativa.
     * 
     * @param status : constante referente ao status atual do servidor.
     */
    public void setStatus(int status)
    {
    	switch(status)
    	{
    	case ControllerMain.STARTED:
    		composeStatus.setStatus(ISSTARTED);
    		break;
    	case ControllerMain.STOPPED:
    		composeStatus.setStatus(ISSTOPPED);
    		break;
    	case ControllerMain.UNKOWN:
    		composeStatus.setStatus(ISUNKOWN);
    		break;
    	default:
    		composeStatus.setStatus(ISUNKOWN);
    		break;
    	}
    }
    
    /**
     * Acrescenta o AbstractLog fornecido na saida de TextArea da View.
     * 
     * @param log : objeto AbstractLog a ser acrescentado
     */
    public void addLog(AbstractLog log)
    {
    	composeLog.AddMessage(log);
    }
    
    /**
     * Retorna as interacoes do administrador com a View, para tratar os eventos de interface.
     * @return o painel de controle da View
     */
    public ComposeControl getIteractions()
    {
    	return composeControl;
    }
}
