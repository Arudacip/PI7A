package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    private static int porta = 80;
	private AbstractLog currentLog;
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
    	porta = 80;
    	
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
    	// Usa uma Thread, para nao travar a view
    	new Thread()
    	{
            @Override
            public void run() {
            	generateLog(WARN, "Tentando iniciar o servidor...");
                try {
                	servidor.start(porta);
                	generateLog(INFO, "Servidor iniciado!");
                	// tem que ser checado aqui caso contrario da null pointer exception
                    checkStatus();
                } catch (IOException ex) {
                    ex.printStackTrace();
                	generateLog(CRIT, "Falha ao iniciar do servidor!");
                	// tem que ser checado aqui caso contrario da null pointer exception
                    checkStatus();
                }
            }
        }.start();
    }
    
    public void stopService()
    {
        generateLog(WARN, "Tentando parar o servidor...");
        boolean sucesso = servidor.stop();
        if (sucesso)
        {
        	generateLog(INFO, "Servidor parado!");
        	// tem que ser checado aqui caso contrario da null pointer exception
            checkStatus();
        } else
        {
        	generateLog(CRIT, "Falha na parada do servidor!");
        	// tem que ser checado aqui caso contrario da null pointer exception
            checkStatus();
        }
    }
    
    public void restartService()
    {
    	generateLog(WARN, "Reiniciando o servidor.");
        stopService();
        startService();
    }
    
    public void checkStatus()
    {
    	int valor = servidor.getStatus();
    	switch (valor)
    	{
    	case ViewServiceAdmin.STARTED:
    		viewSAUI.setStatus(ViewServiceAdmin.STARTED);
    		break;
    	case ViewServiceAdmin.STOPPED:
            viewSAUI.setStatus(ViewServiceAdmin.STOPPED);
    		break;
    	case ViewServiceAdmin.UNKOWN:
            viewSAUI.setStatus(ViewServiceAdmin.UNKOWN);
    		break;
    	default:
            viewSAUI.setStatus(ViewServiceAdmin.UNKOWN);
    		break;
    	}
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
	
	public void generateLog(int tipo, String texto)
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
