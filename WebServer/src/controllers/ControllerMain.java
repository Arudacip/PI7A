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

/**
 * Classe em design pattern Singleton, que gera o Controller principal do servidor, em design pattern MVC.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param INSTANCE : [CONSTANT] instancia Singleton do Controller
 * @param PORTA_CLIENT : [CONSTANT] porta de acesso default para clientes
 * @param PORTA_SERVER : [CONSTANT] porta de acesso default para administradores
 * @param STOPPED : [CONSTANT] status do service web parado
 * @param UNKOWN : [CONSTANT] status do service web desconhecido
 * @param INFO : [CONSTANT] LogType para logs informativos
 * @param WARN : [CONSTANT] LogType para logs de warning
 * @param CRIT : [CONSTANT] LogType para logs de critical
 * @param VERBOSE : [CONSTANT] define se os logs de sistema e servico devem ser <b>verbose</b>
 * @param PORTA : porta em uso no servidor
 * @param servidor : ponteiro para o servidor
 * 
 * @param infolog : lista de logs INFO
 * @param warnlog : lista de logs WARN
 * @param critlog : lista de logs CRIT
 * @param mainlog : lista de logs completa
 * 
 * @param viewSAUI : ponteiro para o View de ServiceAdmin
 */

public final class ControllerMain implements ActionListener
{

    // VARIAVEIS DE AMBIENTE
	private static final ControllerMain INSTANCE = new ControllerMain();
	private static final int PORTA_CLIENT = 80;
	@SuppressWarnings("unused")
	private static final int PORTA_SERVER = 8080;
    public static final int STARTED = 1;
    public static final int STOPPED = 2;
    public static final int UNKOWN = 3;
	public static final int INFO = 1;
	public static final int WARN = 2;
	public static final int CRIT = 3;
    public static final boolean VERBOSE = true;
    public static int PORTA;
	private AbstractLog currentLog;
    private SocketAdmin servidor;
	
    // MODELS
    private ArrayList<AbstractLog> infolog;
    private ArrayList<AbstractLog> warnlog;
    private ArrayList<AbstractLog> critlog;
    private ArrayList<AbstractLog> mainlog;

    // VIEWS
    private static ViewServiceAdmin viewSAUI;
    
    private ControllerMain()
    {
    }
    
    /**
     * Retorna a instancia unica do Controller principal do servidor.
     * @return Instancia unica do Controller
     */
    public static ControllerMain getInstance()
    {
    	return INSTANCE;
    }
    
    /**
     * Prepara as variaveis de ambiente necessarias para funcionamento e cria o service web HTTP no servidor.
     */
    public void createService()
    {
        // Cria os Models
    	infolog = new ArrayList<AbstractLog>();
    	warnlog = new ArrayList<AbstractLog>();
    	critlog = new ArrayList<AbstractLog>();
    	mainlog = new ArrayList<AbstractLog>();
    	PORTA = PORTA_CLIENT;
    	
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
    
    /**
     * Constroi as Views, em design pattern MVC.
     */
    public void createView()
    {
        viewSAUI = new ViewServiceAdmin();
        viewSAUI.setVisible(true);
    }
    
    /**
     * Inicializar o service web em uma Thread, separadamente da View.
     */
    private void startService()
    {
    	// Usa uma Thread, para nao travar a view
    	new Thread()
    	{
            @Override
            public void run()
            {
            	generateLog(WARN, "Tentando iniciar o servidor...");
                try
                {
                	servidor.start(PORTA);
                	generateLog(INFO, "Servidor iniciado!");
                	// tem que ser checado aqui caso contrario da null pointer exception
                    checkStatus();
                } catch (IOException ex) {
                    ex.printStackTrace();
                	generateLog(CRIT, "Falha ao iniciar do servidor!");
                	// tem que ser checado aqui caso contrario da null pointer exception
                    checkStatus();
                }
                servidor.esperarCliente();
            }
        }.start();
    }
    
    /**
     * Parar o service web.
     */
    private void stopService()
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
    
    /**
     * Reiniciar o service web.
     */
    private void restartService()
    {
    	generateLog(WARN, "Reiniciando o servidor.");
        stopService();
        startService();
    }
    
    /**
     * Verificar o status do service web.
     */
    private void checkStatus()
    {
    	int valor = servidor.getStatus();
    	switch (valor)
    	{
    	case ControllerMain.STARTED:
    		viewSAUI.setStatus(ControllerMain.STARTED);
    		break;
    	case ControllerMain.STOPPED:
            viewSAUI.setStatus(ControllerMain.STOPPED);
    		break;
    	case ControllerMain.UNKOWN:
            viewSAUI.setStatus(ControllerMain.UNKOWN);
    		break;
    	default:
            viewSAUI.setStatus(ControllerMain.UNKOWN);
    		break;
    	}
    }
    
    /**
     * Recebe os eventos da View e executa as acoes necessarias de acordo com o caso.
     */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Iniciar
        if (e.getSource() == viewSAUI.getIteractions().getButton(ButtonTypes.Start))
        {
            startService();
        }
        // Reiniciar
        else if (e.getSource() == viewSAUI.getIteractions().getButton(ButtonTypes.Restart))
        {
        	restartService();
        }
        // Parar
        else if (e.getSource() == viewSAUI.getIteractions().getButton(ButtonTypes.Stop))
        {
            stopService();
        }
	}
	
	/**
	 * Gera os objetos AbstractLog do service web, de acordo com os tipos e parametros necessarios.
	 * 
	 * @param tipo : tipo de AbstractLog
	 * @param texto : mensagem do log
	 */
	public void generateLog(int tipo, String texto)
	{
		switch(tipo)
		{
		case INFO:
			currentLog = new LogInfo(new Date(System.currentTimeMillis()), texto);
			infolog.add(currentLog);
			mainlog.add(currentLog);
			viewSAUI.addLog(currentLog);
			if (ControllerMain.VERBOSE)
			{
				System.out.println("INFO: "+currentLog.imprime());
			}
			break;
			
		case WARN:
			currentLog = new LogWarn(new Date(System.currentTimeMillis()), texto);
			warnlog.add(currentLog);
			mainlog.add(currentLog);
			viewSAUI.addLog(currentLog);
			if (ControllerMain.VERBOSE)
			{
				System.out.println("INFO: "+currentLog.imprime());
			}
			break;
			
		case CRIT:
			currentLog = new LogCrit(new Date(System.currentTimeMillis()), texto);
			critlog.add(currentLog);
			mainlog.add(currentLog);
			viewSAUI.addLog(currentLog);
			if (ControllerMain.VERBOSE)
			{
				System.out.println("INFO: "+currentLog.imprime());
			}
			break;
			
		default:
			currentLog = new LogCrit(new Date(System.currentTimeMillis()), "Erro desconhecido.");
			critlog.add(currentLog);
			mainlog.add(currentLog);
			viewSAUI.addLog(currentLog);
			if (ControllerMain.VERBOSE)
			{
				System.out.println("INFO: "+currentLog.imprime());
			}
			break;
		}
	}
}
