package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import controllers.sockets.SocketAdmin;
import models.AbstractFactoryLog;
import models.AbstractLog;
import models.FactoryLogCrit;
import models.FactoryLogInfo;
import models.FactoryLogWarn;
import models.LogCrit;
import models.LogInfo;
import models.LogWarn;
import views.ViewServiceAdmin;
import views.buttons.ButtonTypes;

public class ControllerMain implements ActionListener
{

    /**
     * Classe do Controller principal do design pattern MVC.
     *
     * @param infolog: logs de informacao
     * @param warnlog: logs de warning
     * @param critlog: logs de critical
     * @param mainlog: logs completos
     * @param viewSAUI: View do Service Admin
     * @method main: inicia o app
     */
	
	// Variaveis de Ambiente
	private static final int INFO = 1;
	private static final int WARN = 2;
	private static final int CRIT = 3;
	private AbstractLog currentLog;
    private int porta = 80;
    private SocketAdmin servidor;
	
    // Models
    private ArrayList<AbstractLog> infolog;
    private ArrayList<AbstractLog> warnlog;
    private ArrayList<AbstractLog> critlog;
    private ArrayList<AbstractLog> mainlog;

    // Views
    private static ViewServiceAdmin viewSAUI;

    public void createService()
    {
        // Cria os Models
    	infolog = new ArrayList<AbstractLog>();
    	warnlog = new ArrayList<AbstractLog>();
    	critlog = new ArrayList<AbstractLog>();
    	mainlog = new ArrayList<AbstractLog>();
    	
        AbstractFactoryLog[] factories = new AbstractFactoryLog[3];
        factories[0] = new FactoryLogInfo();
        factories[1] = new FactoryLogWarn();
        factories[2] = new FactoryLogCrit();
        AbstractLog log = null;
        for (AbstractFactoryLog fabrica : factories)
        {
            // TODO: fabricas montarem os logs recuperados no database
            // AbstractLogDAO.getLogs(conn);
            log = fabrica.retornaLogs(new Date(System.currentTimeMillis()), "Aberto.");
            mainlog.add(log);
        }
        servidor = new SocketAdmin();
    }
    
    public void createView()
    {
    	// Cria Views
        viewSAUI = new ViewServiceAdmin(this);
        viewSAUI.setVisible(true);
    }
    
    public void startService()
    {
    	generateLog("Tentando iniciar o servidor...", WARN);
        boolean sucesso = servidor.start(porta);
        if (sucesso)
        {
        	generateLog("Servidor iniciado!", INFO);
        } else
        {
        	generateLog("Falha ao iniciar do servidor!", CRIT);
        }
    }
    
    public void stopService()
    {
        generateLog("Tentando parar o servidor...", WARN);
        boolean sucesso = servidor.stop();
        if (sucesso)
        {
        	generateLog("Servidor parado!", INFO);
        } else
        {
        	generateLog("Falha na parada do servidor!", CRIT);
        }
    }
    
    public void restartService()
    {
    	generateLog("Reiniciando o servidor.", WARN);
        stopService();
        startService();
    }
    
	@Override
	public void actionPerformed(ActionEvent e)
	{
        if (e.getSource() == viewSAUI.getIteractions().getButton(ButtonTypes.Start))/*Iniciar*/
        {
            startService();
        }
        else if (e.getSource() == viewSAUI.getIteractions().getButton(ButtonTypes.Restart))/*Reiniciar*/
        {
        	restartService();
        }
        else if (e.getSource() == viewSAUI.getIteractions().getButton(ButtonTypes.Stop))/*Parar*/
        {
            stopService();
        }
	}
	
	public void generateLog(String texto, int tipo)
	{
		switch(tipo)
		{
		case INFO:
			currentLog = new LogInfo(new Date(System.currentTimeMillis()), texto);
			infolog.add(currentLog);
			mainlog.add(currentLog);
			viewSAUI.addLog(currentLog);
			System.out.println("Tamanho info: "+infolog.size());
			System.out.println("Tamanho main: "+mainlog.size());
			break;
		case WARN:
			currentLog = new LogWarn(new Date(System.currentTimeMillis()), texto);
			warnlog.add(currentLog);
			mainlog.add(currentLog);
			viewSAUI.addLog(currentLog);
			System.out.println("Tamanho warn: "+warnlog.size());
			System.out.println("Tamanho main: "+mainlog.size());
			break;
		case CRIT:
			currentLog = new LogCrit(new Date(System.currentTimeMillis()), texto);
			critlog.add(currentLog);
			mainlog.add(currentLog);
			viewSAUI.addLog(currentLog);
			System.out.println("Tamanho crit: "+critlog.size());
			System.out.println("Tamanho main: "+mainlog.size());
			break;
		default:
			currentLog = new LogCrit(new Date(System.currentTimeMillis()), "Erro desconhecido.");
			critlog.add(currentLog);
			mainlog.add(currentLog);
			viewSAUI.addLog(currentLog);
			System.out.println("Tamanho crit: "+critlog.size());
			System.out.println("Tamanho main: "+mainlog.size());
			break;
		}
	}
}
